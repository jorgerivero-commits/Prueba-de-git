import java.util.*;

public class EstacionBase {
    private String id;
    private Map<String, Drone> drones;
    private PriorityQueue<Paquete> colaPaquetes;
    private int entregasCompletadas;

    public EstacionBase(String id) {
        this.id = id;
        this.drones = new HashMap<>();
        this.colaPaquetes = new PriorityQueue<>();
        this.entregasCompletadas = 0;
    }

    public boolean registrarDrone(Drone drone) {
        if (drones.containsKey(drone.getId())) {
            return false;
        }
        drones.put(drone.getId(), drone);
        return true;
    }

    public boolean agregarPaquete(Paquete paquete) {
        if (!paquete.esPesoValido()) {
            return false;
        }
        return colaPaquetes.add(paquete);
    }

    public boolean asignarPaqueteAutomatico() {
        if (colaPaquetes.isEmpty()) {
            return false;
        }

        Paquete paquete = colaPaquetes.peek();

        for (Drone drone : drones.values()) {
            if (drone.getEstado() == EstadoDrone.DISPONIBLE && drone.asignarPaquete(paquete)) {
                colaPaquetes.poll();
                return true;
            }
        }
        return false;
    }

    public boolean iniciarEntrega(String droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            return drone.despegar();
        }
        return false;
    }

    public boolean completarEntrega(String droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null && drone.completarEntregaActual()) {
            this.entregasCompletadas++;
            return true;
        }
        return false;
    }

    public Map<String, Object> obtenerEstadisticas() {
        long disponibles = drones.values().stream().filter(d -> d.getEstado() == EstadoDrone.DISPONIBLE).count();
        long enVuelo = drones.values().stream().filter(d -> d.getEstado() == EstadoDrone.EN_VUELO).count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("total_drones", drones.size());
        stats.put("drones_disponibles", disponibles);
        stats.put("drones_en_vuelo", enVuelo);
        stats.put("entregas_completadas", entregasCompletadas);
        stats.put("paquetes_pendientes", colaPaquetes.size());
        return stats;
    }
}
