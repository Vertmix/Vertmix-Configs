package com.vertmix.config.exception;

public class ConfigNotRegisteredException extends RuntimeException {

    public ConfigNotRegisteredException() {
        super("Config was not registered. Register it before loading or saving.");
    }
}
