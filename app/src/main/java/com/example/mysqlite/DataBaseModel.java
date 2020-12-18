package com.example.mysqlite;

import java.io.Serializable;

public class DataBaseModel implements Serializable {
    //ModelClass For Get and Set input
    int id;
    String name;
    String age;
    String profession;

    public DataBaseModel(int id, String name, String age, String profession) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profession = profession;
    }

    public DataBaseModel(String name, String age, String profession) {
        this.name = name;
        this.age = age;
        this.profession = profession;
    }

    public DataBaseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
