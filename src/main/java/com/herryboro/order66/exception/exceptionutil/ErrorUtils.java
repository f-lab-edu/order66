package com.herryboro.order66.exception.exceptionutil;

import com.google.gson.Gson;
import com.herryboro.order66.exception.InvalidInputException;
import org.springframework.validation.BindingResult;

public class ErrorUtils {
    public static void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            String jsonErrorMessages = new Gson().toJson(result.getFieldErrors());
            throw new InvalidInputException(jsonErrorMessages);
        }
    }
}
