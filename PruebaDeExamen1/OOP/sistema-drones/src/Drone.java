import java.util.HashMap;
import java.util.Map;

public class Drone {
    private String id;
    private double capacidadCarga;
    private double bateria;
    private EstadoDrone estado;
    private double horasVuelo;
    private Paquete paqueteAsignado;

    public Drone(String id, double capacidadCarga) {
        this.id = id;
        this.capacidadCarga = capacidadCarga;
        this.bateria = 100.0;
        this.estado = EstadoDrone.DISPONIBLE;
        this.horasVuelo = 0.0;
        this.paqueteAsignado = null;
    }

    public void cargarBateria(double porcentaje) {
        this.bateria = Math.min(100.0, Math.max(0.0, porcentaje));
    }

    public boolean puedeVolar() {
        return this.bateria >= 30.0 && this.estado == EstadoDrone.DISPONIBLE;
    }

    public boolean requiereMantenimiento() {
        return this.horasVuelo >= 50.0;
    }

    public void enviarAMantenimiento() {
        this.estado = EstadoDrone.EN_MANTENIMIENTO;
        this.paqueteAsignado = null;
    }

    public boolean asignarPaquete(Paquete paquete) {
        if (this.estado != EstadoDrone.DISPONIBLE) {
            return false;
        }
        if (paquete.getPeso() > this.capacidadCarga) {
            return false;
        }
        if (!paquete.puedeSerAsignado()) {
            return false;
        }

        this.paqueteAsignado = paquete;
        paquete.setEstado(EstadoPaquete.ASIGNADO);
        return true;
    }

    public boolean despegar() {
        if (!puedeVolar() || this.paqueteAsignado == null) {
            return false;
        }
        this.estado = EstadoDrone.EN_VUELO;
        return true;
    }

    public void aterrizar() {
        this.horasVuelo += 0.5;
        if (this.estado == EstadoDrone.EN_VUELO) {
            this.estado = EstadoDrone.DISPONIBLE;
        }
    }

    public boolean completarEntregaActual() {
        if (this.estado == EstadoDrone.EN_VUELO && this.paqueteAsignado != null) {
            boolean entregado = this.paqueteAsignado.marcarEntregado();
            if (entregado) {
                this.paqueteAsignado = null;
                aterrizar();
                return true;
            }
        }
        return false;
    }

    public Map<String, Object> obtenerInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("id", this.id);
        info.put("capacidadCarga", this.capacidadCarga);
        info.put("bateria", this.bateria);
        info.put("estado", this.estado);
        info.put("horasVuelo", this.horasVuelo);
        info.put("tienePaquete", this.paqueteAsignado != null);
        return info;
    }

    public String getId() { return id; }
    public EstadoDrone getEstado() { return estado; }
    public Paquete getPaqueteAsignado() { return paqueteAsignado; }
}
