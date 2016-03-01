package com.tang.moneylogger.bean;

import java.io.Serializable;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public class Activities implements Serializable{

    private int id;
    private int type_id;
    private String name;
    private int icon;
    private String description;
    private String time;
    private double amount;

    public Activities() {
    }

    public Activities(int type_id, String name, int icon, String description, String time, double amount) {
        this.type_id = type_id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.time = time;
        this.amount = amount;
    }

    public Activities(int id, int type_id, String name, int icon, String description, String time, double amount) {
        this.id = id;
        this.type_id = type_id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.time = time;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
