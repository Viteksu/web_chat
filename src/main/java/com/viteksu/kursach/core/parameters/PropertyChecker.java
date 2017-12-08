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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PropertyChecker implements Runnable {

    private final ServletContext servletContext;
    private List<String> resourses = new CopyOnWriteArrayList<>();
    private Property property;

    public PropertyChecker(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Property getProperty() {
        return property;
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


            Type listType = new TypeToken<CopyOnWriteArrayList>() {
            }.getType();


            resourses = new Gson().fromJson(jsonElement, listType);
            int sleepTime = object.get("checking_time_out").getAsInt() * 1000;
            int sizeMessagePool = object.get("size_message_pool").getAsInt();

            property = new Property(resourses, sleepTime, sizeMessagePool);

            synchronized (this) {
                this.notify();
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
