import actividades.*;
import certificacion.Certificable;
import excepciones.CupoExcedidoException;
import hilos.EnvioTicketsThread;
import modelo.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("====================================================");
        System.out.println("    TRABAJO PRÁCTICO INTEGRADOR - SISTEMA POO");
        System.out.println("====================================================\n");

        // --------------------------------------------------
        // EJERCICIO 1: Manejo de Excepciones y Persistencia
        // --------------------------------------------------
        System.out.println("--- EJERCICIO 1: Excepciones y Persistencia ---");
        EventoUniversitario e1 = new EventoUniversitario("E101", "Congreso Tecnológico", 2000, false);
        e1.crearActividad(1, "Charla IA", 1, "Charla"); // Cupo máximo = 1

        Estudiante est1 = new Estudiante("4001", "Santiago Cusimano");
        Estudiante est2 = new Estudiante("4002", "Ana Martínez");

        try {
            System.out.println("[Intento 1] Inscribiendo a Santiago...");
            e1.getActividades().get(0).inscribir(est1); // Exitoso
            System.out.println("Inscripción exitosa.");

            System.out.println("[Persistencia] Guardando evento en disco...");
            e1.persistirEvento();

            System.out.println("[Persistencia] Recuperando evento de disco...");
            EventoUniversitario eRecuperado = EventoUniversitario.recuperarEvento("E101");
            System.out.println("Evento recuperado desde archivo: " + eRecuperado.getTitulo());

            System.out.println("[Intento 2] Inscribiendo a Ana (Excediendo cupo)...");
            e1.getActividades().get(0).inscribir(est2); // Lanza CupoExcedidoException

        } catch (CupoExcedidoException e) {
            System.err.println("CATCH [CupoExcedidoException]: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("CATCH [FileNotFoundException]: No se encontró el archivo.");
        } catch (IOException e) {
            System.err.println("CATCH [IOException]: Error de entrada/salida.");
        } catch (ClassNotFoundException e) {
            System.err.println("CATCH [ClassNotFoundException]: Clase no encontrada al deserializar.");
        } finally {
            System.out.println("FINALLY: Finalizaron las operaciones del Ejercicio 1.\n");
        }

        // --------------------------------------------------
        // EJERCICIO 2: Interfaces y Certificados
        // --------------------------------------------------
        System.out.println("--- EJERCICIO 2: Interfaces y Certificación ---");
        EventoUniversitario e2 = new EventoUniversitario("E102", "Simposio de Desarrollo", 1000, false);
        e2.asignarSala(new Sala(10, "Auditorio Central"));

        e2.crearActividad(10, "Charla Keynote", 50, "Charla");
        e2.crearActividad(11, "Taller de Git & Github", 20, "Taller", true, 0);
        e2.crearActividad(12, "Curso Profesional Java", 15, "Curso", false, 2);

        try {
            e2.getActividades().get(0).inscribir(est1);
            e2.getActividades().get(1).inscribir(est1);
            e2.getActividades().get(2).inscribir(est1);
        } catch (CupoExcedidoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n[Certificados Emitidos para el Estudiante]");
        for (Actividad act : e2.getActividades()) {
            if (act instanceof Certificable) {
                Certificable certificable = (Certificable) act;
                System.out.println("----------------------------------------");
                System.out.println(certificable.generarCertificado(est1));
            }
        }
        System.out.println("----------------------------------------\n");

        // --------------------------------------------------
        // EJERCICIO 3: Genéricos y Wildcards
        // --------------------------------------------------
        System.out.println("--- EJERCICIO 3: Genéricos y Wildcards ---");
        List<Charla> charlas = e2.filtrarActividadesPorTipo(Charla.class);
        List<Taller> talleres = e2.filtrarActividadesPorTipo(Taller.class);
        List<Curso> cursos = e2.filtrarActividadesPorTipo(Curso.class);

        System.out.println("Actividades tipo Charla creadas: " + charlas.size());
        System.out.println("Actividades tipo Taller creadas: " + talleres.size());
        System.out.println("Actividades tipo Curso creadas: " + cursos.size());

        System.out.println("Costo materiales (Charlas): $" + e2.calcularCostoMateriales(charlas));
        System.out.println("Costo materiales (Talleres): $" + e2.calcularCostoMateriales(talleres));
        System.out.println("Costo materiales (Cursos): $" + e2.calcularCostoMateriales(cursos));
        System.out.println("Costo materiales total del evento: $" + e2.calcularCostoMateriales(e2.getActividades()) + "\n");

        // --------------------------------------------------
        // EJERCICIO 4: Clase Anidada e Hilos (Concurrencia)
        // --------------------------------------------------
        System.out.println("--- EJERCICIO 4: Ticket de Acceso e Hilos ---");

        // Confirmar inscripciones para generar las instancias de TicketDeAcceso
        for (Actividad act : e2.getActividades()) {
            for (Inscripcion ins : act.getInscripciones()) {
                ins.confirmarInscripcion();
            }
        }

        // Iniciar hilo concurrente para el envío de tickets
        EnvioTicketsThread hiloEnvio = new EnvioTicketsThread(e2);
        hiloEnvio.start();

        // El Hilo Principal continúa mostrando información sin bloquarse
        System.out.println("[HILO PRINCIPAL] Mostrando datos del evento mientras los tickets se envían en segundo plano...");
        e2.mostrarDatos();

        try {
            // Esperamos que el hilo concurrente finalice antes de cerrar el programa
            hiloEnvio.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Ejecución finalizada con éxito.");
    }
}