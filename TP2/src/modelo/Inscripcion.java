package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;
    private TicketDeAcceso ticket;

    public Inscripcion(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.fecha = LocalDate.now();
        this.estado = "Pendiente";
    }

    public void confirmarInscripcion() {
        this.estado = "Confirmada";
        this.ticket = new TicketDeAcceso();
    }

    public Estudiante getEstudiante() { return estudiante; }
    public String getEstado() { return estado; }
    public LocalDate getFecha() { return fecha; }
    public TicketDeAcceso getTicket() { return ticket; }

    // Clase Miembro Anidada
    public class TicketDeAcceso implements Serializable {
        private static final long serialVersionUID = 1L;

        private String idTicket;
        private LocalDate fechaEmision;

        public TicketDeAcceso() {
            this.idTicket = "TCK-" + estudiante.getLegajo() + "-" + (int)(Math.random() * 9000 + 1000);
            this.fechaEmision = LocalDate.now();
        }

        public void enviarTicket() {
            System.out.println("[ENVÍO TICKET] Ticket " + idTicket + " enviado exitosamente a " + estudiante.getNombre() + " (" + estudiante.getLegajo() + ")");
        }

        public String getIdTicket() { return idTicket; }
        public LocalDate getFechaEmision() { return fechaEmision; }
    }
}