package me.coleo.mylib.AlexaLib;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import me.coleo.mylib.AlexaLib.exceptions.InvalidInput;
import me.coleo.mylib.AlexaLib.exceptions.NullField;
import me.coleo.mylib.AlexaLib.exceptions.NullUniqueField;

public class AlexaFactory {

    public JSONObject get(Object input) {
        if (input instanceof Class<?>) {
            throw new InvalidInput("invalid input object expected");
        }
        if (input == null) {
            throw new InvalidInput("null input");
        }

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
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Unique) {
                            output.put(name, value);
                            continue fieldsLoop;
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
        JSONObject mainOut = new JSONObject();
        try {
            mainOut.put(getName(input.getClass()), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainOut;
    }

    public JSONObject update(Object input) {
        if (input instanceof Class<?>) {
            throw new InvalidInput("invalid input object expected");
        }
        if (input == null) {
            throw new InvalidInput("null input");
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
                    else
                        output.put(getName(field), updateInner(value));
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
        JSONObject mainOut = new JSONObject();
        try {
            mainOut.put(getName(input.getClass()), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainOut;
    }

    private JSONObject updateInner(Object input) {
        if (input instanceof Class<?>) {
            throw new InvalidInput("invalid input object expected");
        }
        if (input == null) {
            throw new InvalidInput("null input");
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
                    else
                        output.put(getName(field), updateInner(value));
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

}
