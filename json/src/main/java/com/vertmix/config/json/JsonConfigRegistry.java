package com.vertmix.config.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vertmix.config.AbstractConfigRegistry;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.Optional;

public class JsonConfigRegistry extends AbstractConfigRegistry {

    private static final String EXT = ".json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
            .create();


    @Override
    public void save(Object obj, File file) {
        trySave(obj, file, f -> f.getName().endsWith(EXT), (o, f) -> {
            try {
                GSON.toJson(obj, new FileWriter(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <Type> Optional<Type> load(Class<Type> clazz, File file) {
        return Optional.ofNullable(tryLoad(clazz, file, f -> f.getName().endsWith(EXT), (f, instance) -> {
            try {
                return GSON.fromJson(new FileReader(file), clazz);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }));
    }

}
