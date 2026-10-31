import java.util.HashMap;
import java.util.Map;

public class Paquete implements Comparable<Paquete> {
    private String id;
    private double peso;
    private String direccion;
    private Prioridad prioridad;
    private EstadoPaquete estado;
    private int intentosFallidos;

    public Paquete(String id, double peso, String direccion, int nivelPrioridad) {
        this.id = id;
        this.peso = peso;
        this.direccion = direccion;
        this.prioridad = mapearPrioridad(nivelPrioridad);
        this.estado = EstadoPaquete.PENDIENTE;
        this.intentosFallidos = 0;
    }

    private Prioridad mapearPrioridad(int nivel) {
        for (Prioridad p : Prioridad.values()) {
            if (p.getNivel() == nivel) {
                return p;
            }
        }
        return Prioridad.NORMAL;
    }

    public boolean esPesoValido() {
        return this.peso >= 0.1 && this.peso <= 5.0;
    }

    public boolean puedeSerAsignado() {
        return this.estado == EstadoPaquete.PENDIENTE;
    }

    public void registrarIntentoFallido() {
        this.intentosFallidos++;
        if (this.intentosFallidos >= 3) {
            this.estado = EstadoPaquete.FALLIDO;
        }
    }

    public boolean marcarEntregado() {
        if (this.estado == EstadoPaquete.FALLIDO) {
            return false;
        }
        this.estado = EstadoPaquete.ENTREGADO;
        return true;
    }

    public Map<String, Object> obtenerInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("id", this.id);
        info.put("peso", this.peso);
        info.put("direccion", this.direccion);
        info.put("prioridad", this.prioridad);
        info.put("estado", this.estado);
        info.put("intentosFallidos", this.intentosFallidos);
        return info;
    }

    @Override
    public int compareTo(Paquete otro) {
        return Integer.compare(otro.prioridad.getNivel(), this.prioridad.getNivel());
    }

    public String getId() { return id; }
    public double getPeso() { return peso; }
    public EstadoPaquete getEstado() { return estado; }
    public void setEstado(EstadoPaquete estado) { this.estado = estado; }
}
