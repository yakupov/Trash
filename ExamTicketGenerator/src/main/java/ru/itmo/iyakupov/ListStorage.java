package ru.itmo.iyakupov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ListStorage<T> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final TypeToken<List<T>> typeToken;
    private List<T> elements = new ArrayList<>();

    public ListStorage(TypeToken<List<T>> typeToken) {
        this.typeToken = typeToken;
    }

    public void load(@Nonnull File file) throws IOException {
        if (!file.exists() || file.isDirectory())
            throw new IllegalArgumentException("The given file either is a directory or does not exist");

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
            elements = gson.fromJson(reader, typeToken.getType());
        }
    }

    public void store(@Nonnull File file) throws IOException {
        if (file.isDirectory())
            throw new IllegalArgumentException("The given file is a directory");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(gson.toJson(elements).getBytes(StandardCharsets.UTF_8));
        }
    }

    @Nonnull
    public List<T> getElements() {
        return elements;
    }

    public void setElements(@Nonnull List<T> elements) {
        this.elements = elements;
    }
}
