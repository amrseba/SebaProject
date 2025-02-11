package com.example.sebaproject;

public class Users {
    private int id;
    private String Fname;
    private String Lname;
    private String Email;
    private String Username;
    private String Password;

    public Users(int id, String fname, String lname, String email, String username, String password) {
        this.id = id;
        Fname = fname;
        Lname = lname;
        Email = email;
        Username = username;
        Password = password;
    }
    public Users(String fname, String lname, String email, String username, String password) {
        this.Fname = fname;
        this.Lname = lname;
        this.Email = email;
        this.Username = username;
        this.Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
