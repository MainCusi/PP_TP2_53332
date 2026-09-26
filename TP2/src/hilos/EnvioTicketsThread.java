package hilos;

import actividades.Actividad;
import modelo.EventoUniversitario;
import modelo.Inscripcion;

public class EnvioTicketsThread extends Thread {
    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println("\n>>> [HILO DE ENVÍO] Iniciando envío de tickets para el evento: " + evento.getTitulo());
        for (Actividad act : evento.getActividades()) {
            for (Inscripcion ins : act.getInscripciones()) {
                if ("Confirmada".equals(ins.getEstado()) && ins.getTicket() != null) {
                    try {
                        Thread.sleep(700); // Simulación de tiempo de red
                    } catch (InterruptedException e) {
                        System.err.println("Hilo interrumpido.");
                    }
                    ins.getTicket().enviarTicket();
                }
            }
        }
        System.out.println(">>> [HILO DE ENVÍO] Proceso finalizado exitosamente.\n");
    }
}