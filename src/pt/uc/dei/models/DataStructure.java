package pt.uc.dei.models;

import java.io.Serializable;

public class DataStructure implements Serializable {

    private int id;
    private String title;
    private String description;
    private String objective;
    private String date;
    private String location;
    private String username;
    private String itemname;
    private String itemdescription;
    private String keydecision;
    private String status;
    private String usernameaction;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public String getKeydecision() {
        return keydecision;
    }

    public void setKeydecision(String keydecision) {
        this.keydecision = keydecision;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsernameaction() {
        return usernameaction;
    }

    public void setUsernameaction(String usernameaction) {
        this.usernameaction = usernameaction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
