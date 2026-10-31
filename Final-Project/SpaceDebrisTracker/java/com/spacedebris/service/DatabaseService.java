package com.spacedebris.service;

import com.spacedebris.model.Satellite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    // Configuración de conexión limpia a MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/space_debris_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "spacedebris_user";
    private static final String PASSWORD = "05012007JRJ#2";

    public Connection connect() throws SQLException {
        try {
            // Cargamos explícitamente el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado en el classpath: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para guardar o actualizar un satélite en la base de datos
    public void saveOrUpdateSatellite(Satellite sat) {
        String sql = "INSERT INTO satellites (id, name, latitude, longitude, altitude) VALUES (?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name = VALUES(name), latitude = VALUES(latitude), longitude = VALUES(longitude), altitude = VALUES(altitude)";
        
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sat.getId());
            pstmt.setString(2, sat.getName());
            pstmt.setDouble(3, sat.getLatitude());
            pstmt.setDouble(4, sat.getLongitude());
            pstmt.setDouble(5, sat.getAltitude());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar satélite en MySQL: " + e.getMessage());
        }
    }

        // Método para obtener la lista de satélites desde MySQL
    public List<Satellite> getAllSatellites() {
        List<Satellite> list = new ArrayList<>();
        String query = "SELECT d.norad_cat_id, d.object_name, " +
                       "IFNULL(p.latitude, 0.0) as lat, " +
                       "IFNULL(p.longitude, 0.0) as lon, " +
                       "IFNULL(p.altitude, 400.0) as alt " +
                       "FROM space_debris d " +
                       "LEFT JOIN orbital_positions p ON d.norad_cat_id = p.norad_cat_id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("norad_cat_id");
                String name = rs.getObject("object_name").toString();
                double lat = rs.getDouble("lat");
                double lon = rs.getDouble("lon");
                double alt = rs.getDouble("alt");

                list.add(new Satellite(id, name, lat, lon, alt));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar satélites desde MySQL: " + e.getMessage());
        }
        return list;
    }
    
    public List<Satellite> getRawDebrisFromDatabase() {
        List<Satellite> list = new ArrayList<>();
        String query = "SELECT norad_cat_id, object_name, tle_line1, tle_line2 FROM space_debris";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("norad_cat_id");
                String name = rs.getString("object_name");
                String line1 = rs.getString("tle_line1");
                String line2 = rs.getString("tle_line2");

                Satellite sat = new Satellite();
                sat.setId(id);
                sat.setName(name);
                sat.setTleLine1(line1);
                sat.setTleLine2(line2);

                list.add(sat);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar TLEs crudos desde MySQL: " + e.getMessage());
        }
    return list;
    }

    public void saveOrbitalPosition(int noradCatId, double latitude, double longitude, double altitude) {
        String query = "INSERT INTO orbital_positions (norad_cat_id, latitude, longitude, altitude) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, noradCatId);
            pstmt.setDouble(2, latitude);
            pstmt.setDouble(3, longitude);
            pstmt.setDouble(4, altitude);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar la posición orbital en MySQL: " + e.getMessage());
        }
    }
}
