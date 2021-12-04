package com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "UserDetailsManagement")
public class Users {

    @Id
    private String id;
    private String fullName;
    private String email;;
    private String password;
    private String role;
    private String createdOn;
    @Nullable
    private String lastUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
