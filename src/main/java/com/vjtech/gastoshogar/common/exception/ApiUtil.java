package com.vjtech.gastoshogar.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiUtil {
    public static Map<String, String> fieldError(String field, String message) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put(field, message);
        return error;
    }
}