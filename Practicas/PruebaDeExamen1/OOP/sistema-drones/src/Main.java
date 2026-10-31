import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CONTROL DE FLOTA DE DRONES ===\n");

        EstacionBase base = new EstacionBase("BASE-01");
        System.out.println("✓ Estación base creada\n");

        Drone drone1 = new Drone("DRN-001", 3.5);
        Drone drone2 = new Drone("DRN-002", 5.0);
        Drone drone3 = new Drone("DRN-003", 2.0);
        System.out.println("✓ Drones creados");

        if (base.registrarDrone(drone1)) System.out.println("✓ Drone 1 registrado");
        if (base.registrarDrone(drone2)) System.out.println("✓ Drone 2 registrado");
        if (base.registrarDrone(drone3)) System.out.println("✓ Drone 3 registrado");
        System.out.println();

        Paquete paquete1 = new Paquete("PKG-00001", 2.5, "Av. Banzer 123", 3);
        Paquete paquete2 = new Paquete("PKG-00002", 1.2, "Calle 24 de Septiembre", 4);
        Paquete paquete3 = new Paquete("PKG-00003", 4.8, "Radial 10 esq. 4to Anillo", 2);
        Paquete paquete4 = new Paquete("PKG-00004", 0.5, "Plaza 24 de Septiembre", 1);
        System.out.println("✓ Paquetes creados\n");

        base.agregarPaquete(paquete1);
        base.agregarPaquete(paquete2);
        base.agregarPaquete(paquete3);
        base.agregarPaquete(paquete4);
        System.out.println("✓ Paquetes agregados a la cola\n");

        drone1.cargarBateria(100);
        drone2.cargarBateria(100);
        drone3.cargarBateria(25);
        System.out.println("✓ Drones cargados\n");

        System.out.println("--- Asignación Automática de Paquetes ---");
        if (base.asignarPaqueteAutomatico()) {
            System.out.println("✓ Paquete asignado (debería ser PKG-00002 - Urgente)");
        }
        if (base.asignarPaqueteAutomatico()) {
            System.out.println("✓ Paquete asignado (debería ser PKG-00001 - Alta)");
        }
        if (base.asignarPaqueteAutomatico()) {
            System.out.println("✓ Paquete asignado (debería ser PKG-00003 - Normal)");
        }
        System.out.println();

        System.out.println("--- Iniciando Entregas ---");
        if (base.iniciarEntrega("DRN-001")) {
            System.out.println("✓ Entrega iniciada con DRN-001");
        } else {
            System.out.println("✗ No se pudo iniciar entrega con DRN-001");
        }

        if (base.iniciarEntrega("DRN-002")) {
            System.out.println("✓ Entrega iniciada con DRN-002");
        } else {
            System.out.println("✗ No se pudo iniciar entrega con DRN-002");
        }
        System.out.println();

        System.out.println("--- Prueba: Despegar con batería baja ---");
        if (base.iniciarEntrega("DRN-003")) {
            System.out.println("✗ ERROR: No debería permitir despegar con batería baja");
        } else {
            System.out.println("✓ Correctamente bloqueado: batería insuficiente\n");
        }

        System.out.println("--- Estado de Drones ---");
        Map<String, Object> info1 = drone1.obtenerInfo();
        System.out.println("Drone: " + info1.get("id") + ", Estado: " + info1.get("estado") + ", Batería: " + info1.get("bateria") + "%");
        Map<String, Object> info2 = drone2.obtenerInfo();
        System.out.println("Drone: " + info2.get("id") + ", Estado: " + info2.get("estado") + ", Batería: " + info2.get("bateria") + "%");
        Map<String, Object> info3 = drone3.obtenerInfo();
        System.out.println("Drone: " + info3.get("id") + ", Estado: " + info3.get("estado") + ", Batería: " + info3.get("bateria") + "%");
        System.out.println();

        System.out.println("--- Completando Entregas ---");
        if (base.completarEntrega("DRN-001")) {
            System.out.println("✓ Entrega completada por DRN-001");
        }
        if (base.completarEntrega("DRN-002")) {
            System.out.println("✓ Entrega completada por DRN-002");
        }
        System.out.println();

        System.out.println("--- Simulando Desgaste (51 horas de vuelo) ---");
        for (int i = 0; i < 102; i++) {
            drone1.aterrizar();
        }

        if (drone1.requiereMantenimiento()) {
            System.out.println("✓ DRN-001 requiere mantenimiento");
            drone1.enviarAMantenimiento();
            System.out.println("✓ DRN-001 enviado a mantenimiento");
        }
        System.out.println();

        System.out.println("--- Prueba: Asignar paquete a drone en mantenimiento ---");
        if (paquete4.puedeSerAsignado()) {
            if (drone1.asignarPaquete(paquete4)) {
                System.out.println("✗ ERROR: No debería permitir asignar paquete a drone en mantenimiento");
            } else {
                System.out.println("✓ Correctamente bloqueado: drone en mantenimiento\n");
            }
        }

        System.out.println("--- Prueba: Crear paquete con peso inválido ---");
        Paquete paquetePesado = new Paquete("PKG-00005", 6.0, "Dirección X", 2);
        if (base.agregarPaquete(paquetePesado)) {
            System.out.println("✗ ERROR: No debería permitir paquetes mayores a 5kg");
        } else {
            System.out.println("✓ Correctamente bloqueado: peso excede límite\n");
        }

        System.out.println("--- Prueba: Crear paquete con peso muy bajo ---");
        Paquete paqueteLiviano = new Paquete("PKG-00006", 0.05, "Dirección Y", 2);
        if (base.agregarPaquete(paqueteLiviano)) {
            System.out.println("✗ ERROR: No debería permitir paquetes menores a 0.1kg");
        } else {
            System.out.println("✓ Correctamente bloqueado: peso muy bajo\n");
        }

        System.out.println("--- Prueba: Intentos de entrega fallidos ---");
        Paquete paqueteDificil = new Paquete("PKG-00007", 1.0, "Zona Inaccesible", 2);
        base.agregarPaquete(paqueteDificil);

        paqueteDificil.registrarIntentoFallido();
        System.out.println("Intento 1 fallido");
        paqueteDificil.registrarIntentoFallido();
        System.out.println("Intento 2 fallido");
        paqueteDificil.registrarIntentoFallido();
        System.out.println("Intento 3 fallido");

        Map<String, Object> infoPaquete = paqueteDificil.obtenerInfo();
        System.out.println("✓ Estado del paquete: " + infoPaquete.get("estado") + "\n");

        System.out.println("--- Prueba: Entregar paquete con estado Fallido ---");
        if (paqueteDificil.marcarEntregado()) {
            System.out.println("✗ ERROR: No debería permitir marcar como entregado un paquete fallido");
        } else {
            System.out.println("✓ Correctamente bloqueado: paquete en estado Fallido\n");
        }

        System.out.println("\n=== FIN DE PRUEBAS ===");
    }
}
