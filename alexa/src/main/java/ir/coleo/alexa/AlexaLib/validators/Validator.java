package ir.coleo.alexa.AlexaLib.validators;

import ir.coleo.alexa.AlexaLib.Mode;
import ir.coleo.alexa.AlexaLib.exceptions.InvalidInput;
import ir.coleo.alexa.AlexaLib.exceptions.NullMode;

public class Validator {

    public static void isValid(Object object) {
        if (object instanceof Class<?>) {
            throw new InvalidInput("invalid input object expected");
        }
        if (object == null) {
            throw new InvalidInput("null input");
        }
    }

    public static void isValid(Mode mode, Object object) {
        isValid(object);
        if (mode == null) {
            throw new NullMode("null mode as input");
        }
    }

}
