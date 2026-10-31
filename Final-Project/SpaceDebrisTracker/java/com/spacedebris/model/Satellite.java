package com.spacedebris.model;

public class Satellite {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double altitude;
    private String tleLine1;
    private String tleLine2;

    // Constructor vacío
    public Satellite() {}

    // Constructor con coordenadas (el que ya usabas)
    public Satellite(int id, String name, double latitude, double longitude, double altitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    // Getters y Setters existentes...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }

    // NUEVOS Getters y Setters para las líneas TLE
    public String getTleLine1() { return tleLine1; }
    public void setTleLine1(String tleLine1) { this.tleLine1 = tleLine1; }

    public String getTleLine2() { return tleLine2; }
    public void setTleLine2(String tleLine2) { this.tleLine2 = tleLine2; }
}
