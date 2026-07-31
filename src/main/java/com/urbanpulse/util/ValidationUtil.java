package com.urbanpulse.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private ValidationUtil() {}

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isNotBlank(String value){
        return value != null && !value.trim().isEmpty();
    }

    public static boolean hasMinLength(String value, int minLength){
        return value != null && value.length() >= minLength;
    }

}
