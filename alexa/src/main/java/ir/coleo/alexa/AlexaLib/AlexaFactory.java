package ir.coleo.alexa.AlexaLib;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import ir.coleo.alexa.AlexaLib.exceptions.FailedGet;
import ir.coleo.alexa.AlexaLib.exceptions.InvalidInput;
import ir.coleo.alexa.AlexaLib.exceptions.NotListInput;
import ir.coleo.alexa.AlexaLib.exceptions.NullCreate;
import ir.coleo.alexa.AlexaLib.exceptions.NullField;
import ir.coleo.alexa.AlexaLib.exceptions.NullUniqueField;

import static ir.coleo.alexa.AlexaLib.validators.Validator.isValid;

public class AlexaFactory {

    private static String TAG = "ALEXA_FACTORY";

    private ArrayList<Class<?>> seen = new ArrayList<>();

    public JSONObject invoke(Object input, Mode mode) {
        isValid(mode, input);

        seen.clear();
        JSONObject mainOut = new JSONObject();
        try {
            String name = getName(input.getClass());
            switch (mode) {
                case Get: {
                    mainOut.put(name, getInner(input));
                    break;
                }
                case Create: {
                    mainOut.put(name, createInner(input, false));
                    break;
                }
                case Update: {
                    mainOut.put(name, updateInner(input, false));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainOut;
    }

    private JSONObject getInner(Object input) {
        isValid(input);

        boolean validOutput = false;
        JSONObject output = new JSONObject();
        fieldsLoop:
        for (Field field : input.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(input);
                if (value == null) {
                    throw new NullField(field.getName() + " is null");
                } else {
                    String name = this.getName(field);
                    Annotation[] annotations = field.getDeclaredAnnotations();
                    if (isWrapperType(value.getClass())) {
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof Unique) {
                                validOutput = true;
                                output.put(name, value);
                                continue fieldsLoop;
                            }
                        }
                    } else {
                        Log.i(TAG, "getInner: not wrapper in get ");
                    }
                }
            } catch (IllegalAccessException | JSONException e) {
                e.printStackTrace();
            } catch (NullField nullField) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                boolean unique = false, ignore = false;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Unique)
                        unique = true;
                    if (annotation instanceof Ignore)
                        ignore = true;
                }
                if (unique && !ignore) {
                    throw new NullUniqueField(field.getName() + " field is unique and null");
                } else {
                    nullField.printStackTrace();
                }
            }
        }
        if (!validOutput)
            throw new FailedGet("valid unique field not fount!");
        JSONObject mainOut = new JSONObject();
        try {
            mainOut.put(getName(input.getClass()), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainOut;
    }

    private JSONObject updateInner(Object input, boolean checkRecursive) {
        isValid(input);
        if (checkRecursive)
            if (seen.contains(input.getClass())) {
                return new JSONObject();
            } else {
                seen.add(input.getClass());
            }

        JSONObject output = new JSONObject();
        for (Field field : input.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(input);
                if (value == null) {
                    throw new NullField(field.getName() + " is null");
                } else {
                    if (isWrapperType(value.getClass()))
                        output.put(getName(field), value);
                    else {
                        if (isArrayType(value.getClass())) {
                            output.put(getName(field), toArray(value, Mode.Update));
                        } else {
                            output.put(getName(field), updateInner(value, true));
                        }
                    }
                }
            } catch (IllegalAccessException | JSONException e) {
                e.printStackTrace();
            } catch (NullField nullField) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                boolean unique = false, ignore = false;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Unique)
                        unique = true;
                    if (annotation instanceof Ignore)
                        ignore = true;
                }
                if (unique && !ignore) {
                    throw new NullUniqueField(field.getName() + " field is unique and null");
                } else {
                    nullField.printStackTrace();
                }
            }
        }
        seen.remove(input.getClass());
        return output;
    }

    private JSONObject createInner(Object input, boolean checkRecursive) {
        isValid(input);
        if (checkRecursive)
            if (seen.contains(input.getClass())) {
                return new JSONObject();
            } else {
                seen.add(input.getClass());
            }

        JSONObject output = new JSONObject();
        for (Field field : input.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(input);
                if (value == null) {
                    throw new NullField(field.getName() + " is null");
                } else {
                    String name = this.getName(field);
                    boolean unique = false, ignore = false;
                    Annotation[] annotations = field.getDeclaredAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Unique) {
                            unique = true;
                        } else if (annotation instanceof Ignore) {
                            ignore = true;
                        }
                    }
                    if (!unique && !ignore) {
                        if (isWrapperType(value.getClass()))
                            output.put(name, value);
                        else {
                            if (isArrayType(value.getClass())) {
                                output.put(getName(field), toArray(value, Mode.Create));
                            } else
                                output.put(getName(field), createInner(value, true));
                        }
                    }
                }
            } catch (IllegalAccessException | JSONException e) {
                e.printStackTrace();
            } catch (NullField nullField) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                boolean unique = false, ignore = false;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Unique)
                        unique = true;
                    if (annotation instanceof Ignore)
                        ignore = true;
                }
                if (unique || ignore) {
                    nullField.printStackTrace();
                } else {
                    throw new NullCreate(field.getName() + " field is null, can't create");
                }
            }
        }
        seen.remove(input.getClass());
        return output;
    }

    private String getName(Object object) {
        if (object instanceof Field) {
            Field field = ((Field) object);
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Name) {
                    Name temp = (Name) annotation;
                    return temp.nameTo();
                }
            }
            return ((Field) object).getName();
        } else if (object instanceof Class<?>) {
            Class aClass = ((Class) object);
            Annotation[] annotations = aClass.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Name) {
                    Name temp = (Name) annotation;
                    return temp.nameTo();
                }
            }
            return ((Class) object).getSimpleName();
        } else {
            throw new InvalidInput("not filed or class");
        }
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(String.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }

    private JSONArray toArray(Object input, Mode mode) {
        if (input == null) {
            throw new InvalidInput("null input");
        }
        if (!(input instanceof List)) {
            throw new NotListInput("invalid input List expected");
        }

        JSONArray output = new JSONArray();
        for (Object object : ((List) input)) {
            switch (mode) {
                case Get:
                case Create:
                    output.put(getInner(object));
                    break;
                case Update:
                    output.put(updateInner(object, true));
                    break;
            }
        }
        return output;
    }

    private static final Set<Class<?>> ARRAY_TYPES = getArrayTypes();

    private static boolean isArrayType(Class<?> clazz) {
        return ARRAY_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getArrayTypes() {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(List.class);
        ret.add(ArrayList.class);
        ret.add(Stack.class);
        ret.add(Queue.class);
        return ret;
    }
}
