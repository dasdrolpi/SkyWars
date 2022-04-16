package de.drolpi.skywars.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public abstract class AbstractConfigurationLoader<T> {

    private final Gson gson;
    private final Path directory;
    private final Class<? extends T> type;

    public AbstractConfigurationLoader(Path directory, Class<? extends T> type) {
        this(new GsonBuilder().setPrettyPrinting().create(), directory, type);
    }

    public AbstractConfigurationLoader(Gson gson, Path directory, Class<? extends T> type) {
        this.gson = gson;
        this.directory = directory;
        this.type = type;
    }

    public T loadFile() {
        return loadFromJson(directory, type);
    }

    public T createFile() {
        Path parent = directory.getParent();

        if (existsFile()) {
            if (parent != null && !Files.exists(parent)) {
                createDirectory(parent.toFile());
            }

            T config = createConfig();
            saveConfig(config);
            return config;
        }

        return null;
    }

    public T createConfig() {
        return createObject();
    }

    public abstract T createObject();

    public T loadOrCreateFile() {
        if (existsFile()) {
            return createFile();
        }

        return loadFile();
    }

    public T loadOrCreateConfig() {
        if (existsFile()) {
            return createConfig();
        }

        return loadFile();
    }

    public void loadOrCreateFile(Consumer<T> createConsumer, Consumer<T> loadConsumer) {
        if (existsFile()) {
            createConsumer.accept(createFile());
        }

        loadConsumer.accept(loadFile());
    }

    public void loadOrCreateConfig(Consumer<T> createConsumer, Consumer<T> loadConsumer) {
        if (existsFile()) {
            createConsumer.accept(createConfig());
        }

        loadConsumer.accept(loadFile());
    }

    public boolean existsFile() {
        return !Files.exists(directory);
    }

    public void saveConfig(T config) {
        saveToJson(config, directory);
    }

    private void createDirectory(File path) {
        if (path == null) {
            throw new IllegalArgumentException("The provided path can not be null.");
        }

        if (!path.exists()) {
            boolean bool = path.mkdirs();
        }
    }

    public void saveToJson(Object object, Path externalPath) {
        if (object == null) {
            throw new IllegalArgumentException("The provided object can not be null.");
        }

        if (externalPath == null) {
            throw new IllegalArgumentException("The provided path can not be null.");
        }

        try (FileWriter writer = new FileWriter(externalPath.toFile())) {
            writer.write(gson.toJson(object));
        } catch (IOException ignored) {
        }
    }

    public T loadFromJson(Path source, Class<? extends T> objectClass) {
        if (source == null) {
            throw new IllegalArgumentException("The provided path can not be null.");
        }

        if (objectClass == null) {
            throw new IllegalArgumentException("The provided objectclass can not be null.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(source.toFile()))) {
            return gson.fromJson(reader, objectClass);
        } catch (IOException e) {
            return null;
        }
    }

}