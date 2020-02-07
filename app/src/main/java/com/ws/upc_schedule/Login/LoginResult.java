package com.ws.upc_schedule.Login;

import androidx.annotation.Nullable;

public class LoginResult {
    @Nullable
    private Integer success;
    @Nullable
    private String error;

    LoginResult(@Nullable String error) {
        this.error = error;
    }

    LoginResult(@Nullable Integer success) {
        this.success = success;
    }

    @Nullable
    public Integer getSuccess() {
        return success;
    }

    @Nullable
    public String getError() {
        return error;
    }
}
