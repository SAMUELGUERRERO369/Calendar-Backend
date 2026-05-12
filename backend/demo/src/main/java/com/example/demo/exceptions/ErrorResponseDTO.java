package com.example.demo.exceptions;

import java.time.Instant;

public class ErrorResponseDTO {
    private String error;
    private int code;
    private String timestamp;

    public ErrorResponseDTO(String error, int code) {
        this.error = error;
        this.code = code;
        this.timestamp = Instant.now().toString();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
