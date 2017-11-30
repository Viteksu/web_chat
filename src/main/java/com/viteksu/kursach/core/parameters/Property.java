package com.viteksu.kursach.core.parameters;

import java.util.LinkedList;
import java.util.List;

public class Property {
    private List<String> resourses = new LinkedList<>();
    private int maxUsers = 10;

    public void addResourse(String res) {
        resourses.add(res);
    }

    public List<String> getResourses() {
        return resourses;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setResourses(List<String> strings) {
        this.resourses = strings;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

}