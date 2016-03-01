package com.tang.moneylogger.bean;

import java.io.Serializable;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public class Total implements Serializable {

    private int id;
    private int type_id;
    private double amount;
    private String time;

    public Total() {
    }

    public Total(int type_id, double amount, String time) {
        this.type_id = type_id;
        this.amount = amount;
        this.time = time;
    }

    public Total(int id, int type_id, double amount, String time) {
        this.id = id;
        this.type_id = type_id;
        this.amount = amount;
        this.time = time;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
