package com.example.rechee.codestar.MainScreen;

import com.example.rechee.codestar.Enumerations;

/**
 * Created by reche on 1/1/2018.
 */

public class UserNameFormError {

    public UserNameFormError(Enumerations.Error error, ErrorTarget target, String errorMessage) {
        this.error = error;
        this.target = target;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorTarget getTarget() {
        return target;
    }

    public void setTarget(ErrorTarget target) {
        this.target = target;
    }

    public Enumerations.Error getError() {
        return error;
    }

    public void setError(Enumerations.Error error) {
        this.error = error;
    }

    enum ErrorTarget {
        USERNAME_ONE,
        USERNAME_TWO,
        GENERAL
    }

    private Enumerations.Error error;
    private ErrorTarget target;
    private String errorMessage;
}
