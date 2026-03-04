package org.example.model;

public class UserModel {
    
    private int id_user;
    private String user_name;
    private String email;

    public UserModel() {}

    public UserModel(String user_name, String email) {
        this.user_name = user_name;
        this.email = email;
    }

    public UserModel(String user_name, String email, int id_user) {
        this.id_user = id_user;
        this.user_name = user_name;
        this.email = email;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
