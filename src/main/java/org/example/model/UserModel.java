package org.example.model;

public class UserModel {
    
    private int idUser;
    private String userName;
    private String email;

    public UserModel() {}

    public UserModel(String user_name, String email) {
        this.userName = user_name;
        this.email = email;
    }

    public UserModel(String user_name, String email, int id_user) {
        this.idUser = id_user;
        this.userName = user_name;
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
