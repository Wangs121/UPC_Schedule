package com.ws.upc_schedule.Login;

import androidx.annotation.Nullable;

public class LoginResult {
    @Nullable
    private String success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable String success) {
        this.success = success;
    }

    @Nullable
    public String getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
