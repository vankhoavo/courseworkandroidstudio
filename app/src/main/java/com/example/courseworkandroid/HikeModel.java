package com.example.courseworkandroid;

public class HikeModel {
    private int hike_id;
    private String hike_name;
    private String hike_location;
    private String hike_datetime;
    private String hike_parking_available;
    private String hike_length;
    private String hike_difficulty;
    private String hike_description;

    public HikeModel() {

    }

    public HikeModel(int hike_id, String hike_name, String hike_location, String hike_datetime, String hike_parking_available, String hike_length, String hike_difficulty, String hike_description) {
        this.hike_id = hike_id;
        this.hike_name = hike_name;
        this.hike_location = hike_location;
        this.hike_datetime = hike_datetime;
        this.hike_parking_available = hike_parking_available;
        this.hike_length = hike_length;
        this.hike_difficulty = hike_difficulty;
        this.hike_description = hike_description;
    }

    public int getHike_id() {
        return hike_id;
    }
    public void setHike_id(int hike_id) {
        this.hike_id = hike_id;
    }

    public String getHike_name() {
        return hike_name;
    }
    public void setHike_name(String hike_name) {
        this.hike_name = hike_name;
    }

    public String getHike_location() {
        return hike_location;
    }
    public void setHike_location(String hike_location){
        this.hike_location = hike_location;
    }

    public String getHike_datetime()
    {
        return hike_datetime;
    }
    public void setHike_datetime(String hike_datetime)
    {
        this.hike_datetime = hike_datetime;
    }

    public String getHike_parking_available()
    {
        return hike_parking_available;
    }
    public void setHike_parking_available(String hike_parking_available)
    {
        this.hike_parking_available = hike_parking_available;
    }

    public String getHike_length()
    {
        return hike_length;
    }
    public void setHike_length(String hike_length)
    {
        this.hike_length = hike_length;
    }

    public String getHike_difficulty()
    {
        return hike_difficulty;
    }
    public void setHike_difficulty(String hike_difficulty)
    {
        this.hike_difficulty = hike_difficulty;

    }

    public String getHike_description()
    {
        return hike_description;
    }
    public void setHike_description(String hike_description)
    {
        this.hike_description = hike_description;
    }
}
