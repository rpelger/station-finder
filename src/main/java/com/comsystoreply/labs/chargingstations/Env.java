package com.comsystoreply.labs.chargingstations;

public enum Env {
    DEV,
    PROD;


    public static Env from(String javaEnv) {
        return "prod".equalsIgnoreCase(javaEnv) ? PROD : DEV;
    }
}
