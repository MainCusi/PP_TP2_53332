package modelo;

import actividades.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos = 0;

    private Sala sala;
    private List<Actividad> actividades;

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.actividades = new ArrayList<>(otro.actividades);
        this.sala = otro.sala;
        cantidadEventos++;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void crearActividad(int id, String titulo, int cupo, String tipo) {
        crearActividad(id, titulo, cupo, tipo, false, 1);
    }

    public void crearActividad(int id, String titulo, int cupo, String tipo, boolean requiereNotebook, int nivel) {
        Actividad act;
        if (tipo.equalsIgnoreCase("Charla")) {
            act = new Charla(id, titulo, cupo);
        } else if (tipo.equalsIgnoreCase("Taller")) {
            act = new Taller(id, titulo, cupo, requiereNotebook);
        } else if (tipo.equalsIgnoreCase("Curso")) {
            act = new Curso(id, titulo, cupo, nivel);
        } else {
            System.out.println("Tipo de actividad no válido: " + tipo);
            return;
        }
        actividades.add(act);
    }

    // Ejercicio 3.a: Método genérico acotado
    @SuppressWarnings("unchecked")
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Actividad act : actividades) {
            if (tipo.isInstance(act)) {
                resultado.add((T) act);
            }
        }
        return resultado;
    }

    // Ejercicio 3.b: Uso de Wildcards
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double total = 0;
        for (Actividad act : listaActividades) {
            total += act.calcularCostoMateriales();
        }
        return total;
    }

    public double calcularCostoEstimado() {
        if (gratuito) return 0;
        double totalActividades = calcularCostoMateriales(actividades);
        return (costoBase + totalActividades) * 1.21;
    }

    // Persistencia mediante Serialización
    public boolean persistirEvento() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.id + ".dat"))) {
            oos.writeObject(this);
            return true;
        }
    }

    public static EventoUniversitario recuperarEvento(String id) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(id + ".dat"))) {
            return (EventoUniversitario) ois.readObject();
        }
    }

    public void mostrarDatos() {
        System.out.println("==================================================");
        System.out.println("Evento: " + titulo + " (ID: " + id + ")");
        System.out.println("Costo Estimado Total: $" + String.format("%.2f", calcularCostoEstimado()));
        System.out.println("Sala: " + (sala != null ? sala.getNombre() : "Sin asignar"));
        System.out.println("Actividades:");
        for (Actividad act : actividades) {
            act.mostrarIdentificacion();
            act.mostrarInscripciones();
        }
        System.out.println("==================================================");
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public List<Actividad> getActividades() { return actividades; }
    public static int getCantidadEventos() { return cantidadEventos; }
}