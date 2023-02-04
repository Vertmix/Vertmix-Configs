package com.vertmix.config.toml;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.vertmix.config.AbstractConfigRegistry;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class TomlConfigRegistry extends AbstractConfigRegistry {

    private static final String EXT = ".toml";
    private static final Toml TOML_READER = new Toml();
    private static final TomlWriter TOML_WRITER = new TomlWriter();

    @Override
    public void save(Object obj, File file) {
        trySave(obj, file, f -> file.getName().endsWith(EXT), (o, fi) -> {
            try {
                TOML_WRITER.write(obj, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <Type> Optional<Type> load(Class<Type> clazz, File file) {
        return Optional.ofNullable(tryLoad(clazz, file, f -> file.getName().endsWith(EXT), ((f, instance) -> (TOML_READER.read(file).to(clazz)))));
    }
}
