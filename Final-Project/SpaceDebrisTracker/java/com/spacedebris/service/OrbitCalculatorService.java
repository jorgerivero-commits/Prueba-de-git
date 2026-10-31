package com.spacedebris.service;

import com.spacedebris.model.Satellite;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class OrbitCalculatorService {

    private DatabaseService dbService = new DatabaseService();

    public List<Satellite> fetchSimulatedOrbits() {
        List<Satellite> updatedSatellites = new ArrayList<>();
        File cppBinary = new File("./cpp/sgp4_engine");

        if (!cppBinary.exists()) {
            System.err.println("Advertencia: El binario de C++ no se encontró. Compílalo primero.");
            return updatedSatellites;
        }

        try {
            // 1. Obtener los TLEs que Java guardó previamente en MySQL
            List<Satellite> rawDebrisList = dbService.getRawDebrisFromDatabase(); 

            if (rawDebrisList.isEmpty()) {
                System.err.println("No hay TLEs en la base de datos para enviar a C++.");
                return updatedSatellites;
            }

            // 2. Configurar el proceso para C++
            ProcessBuilder pb = new ProcessBuilder(cppBinary.getAbsolutePath());
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 3. Enviar los TLEs de la base de datos a C++ a través de Stdin
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                for (Satellite sat : rawDebrisList) {
                    writer.write(sat.getId() + "\n");
                    writer.write(sat.getName() + "\n");
                    writer.write(sat.getTleLine1() + "\n");
                    writer.write(sat.getTleLine2() + "\n");
                }
                writer.flush();
            }

            // 4. Leer la respuesta de C++ desde Stdout
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("DEBRIS_DATA:")) {
                        String dataPart = line.substring(12);
                        String[] parts = dataPart.split("\\|");
                        if (parts.length >= 5) {
                            int id = Integer.parseInt(parts[0].trim());
                            String name = parts[1].trim();
                            double lat = Double.parseDouble(parts[2].trim());
                            double lon = Double.parseDouble(parts[3].trim());
                            double alt = Double.parseDouble(parts[4].trim());

                            updatedSatellites.add(new Satellite(id, name, lat, lon, alt));
                        }
                    } else {
                        System.out.println("[C++ Output]: " + line);
                    }
                }
            }

            process.waitFor();

        } catch (Exception e) {
            System.err.println("Error al ejecutar el motor C++ SGP4: " + e.getMessage());
        }

        return updatedSatellites;
    }
}
