package com.viteksu.kursach.core.parameters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class PropertyChecker implements Runnable {

    private final ServletContext servletContext;
    private List<String> resourses = new LinkedList<>();

    public PropertyChecker(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public boolean isURI(String uri) {
        for (String s : resourses) {
            if (uri.equals(s) || uri.equals(s + "/")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void run() {
        while (true) {
            InputStream inputStream = servletContext.getResourceAsStream("/WEB-INF/properies/property.json");

            StringBuilder json = new StringBuilder();
            int b;
            try {
                while ((b = inputStream.read()) != -1) {
                    json.append((char) b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(json.toString()).getAsJsonObject();
            JsonElement jsonElement = object.get("resourses");


            Type listType = new TypeToken<List<String>>() {
            }.getType();


            resourses = new Gson().fromJson(jsonElement, listType);

            synchronized (this) {
                this.notify();
            }

            try {
                Thread.sleep(30_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
