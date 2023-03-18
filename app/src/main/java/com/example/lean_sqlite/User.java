package com.example.lean_sqlite;

public class User {
    private  int id;
    private String name;
    private String phone;
    private  String nambh;

    private  String loaimay;

    public String getnambh() {
        return nambh;
    }

    public void setnambh(String mamay) {
        this.nambh = mamay;
    }

    public String getLoaimay() {
        return loaimay;
    }

    public void setLoaimay(String loaimay) {
        this.loaimay = loaimay;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
