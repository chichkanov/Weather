package dvinc.yamblzhomeproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader {

    public <T> T loadTestData(String fileName, Class<T> cls) {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(fileName);
        InputStreamReader isr = new InputStreamReader(inputStream);

        Gson gson = new GsonBuilder().create();

        return gson.fromJson(isr, cls);
    }
}
