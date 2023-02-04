package com.vertmix.config.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vertmix.config.AbstractConfigRegistry;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class YamlConfigRegistry extends AbstractConfigRegistry {

    private static final String EXT = ".yml";
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @Override
    public void save(Object obj, File file) {
        trySave(obj, file, f -> file.getName().endsWith(EXT), (o, f) -> {
            try {
                MAPPER.writeValue(f, o);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <Type> Optional<Type> load(Class<Type> clazz, File file) {
        return Optional.ofNullable(tryLoad(clazz, file, f -> f.getName().equals(EXT), ((f, instance) -> {
            try {
                return MAPPER.readValue(f, clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })));
    }
}
