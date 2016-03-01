package com.tang.moneylogger.bean;

import java.io.Serializable;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public class Type implements Serializable {

    private int id;
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
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

}
