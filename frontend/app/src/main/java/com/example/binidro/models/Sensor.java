package com.example.binidro.models;

import java.util.ArrayList;

public class Sensor {
    private String id;
    private String name;
    private ArrayList<String> values;

    public Sensor(String id, String name, ArrayList<String> values) {
        this.id = id;
        this.name = name;
        this.values = values;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public Sensor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
