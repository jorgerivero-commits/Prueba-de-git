#include <iostream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <algorithm>

struct DebrisPosition {
    int id;
    std::string name;
    double latitude;
    double longitude;
    double altitude;
};

double generarCoordenadasAleatorias(double min, double max) {
    return min + (static_cast<double>(rand()) / RAND_MAX) * (max - min);
}

// Función auxiliar para limpiar retornos de carro (\r) de Linux/Windows
void limpiarLinea(std::string& s) {
    if (!s.empty() && s.back() == '\r') {
        s.pop_back();
    }
}

int main(int argc, char* argv[]) {
    srand(static_cast<unsigned int>(time(0)));

    std::vector<DebrisPosition> satellitesToProcess;
    std::string lineId, name, line1, line2;

    // Leer los bloques enviados por Java a través de Stdin
    while (std::getline(std::cin, lineId)) {
        limpiarLinea(lineId);
        if (lineId.empty()) continue;

        if (!std::getline(std::cin, name)) break;
        limpiarLinea(name);

        if (!std::getline(std::cin, line1)) break;
        limpiarLinea(line1);

        if (!std::getline(std::cin, line2)) break;
        limpiarLinea(line2);

        try {
            int id = std::stoi(lineId);

            DebrisPosition pos;
            pos.id = id;
            pos.name = name;
            // Simulamos el cálculo SGP4 generando coordenadas geográficas válidas
            pos.latitude = generarCoordenadasAleatorias(-70, 70);
            pos.longitude = generarCoordenadasAleatorias(-180, 180);
            pos.altitude = 400.0 + generarCoordenadasAleatorias(0, 350.0); // Órbita LEO

            satellitesToProcess.push_back(pos);
        } catch (...) {
            // Si hay algún error convirtiendo el ID, ignoramos esta línea y continuamos
            continue;
        }
    }

    // Si por alguna razón no leyó nada por Stdin (respaldo de seguridad para pruebas)
    if (satellitesToProcess.empty()) {
        satellitesToProcess.push_back({25544, "ISS (ZARYA)", 15.4, -45.2, 420.5});
        satellitesToProcess.push_back({36086, "POISK", -20.1, 110.5, 415.0});
    }

    // Enviar el resultado formateado a Java mediante Stdout
    for (const auto& debris : satellitesToProcess) {
        std::cout << "DEBRIS_DATA:" 
                  << debris.id << "|" 
                  << debris.name << "|" 
                  << debris.latitude << "|" 
                  << debris.longitude << "|" 
                  << debris.altitude << std::endl;
    }

    return 0;
}