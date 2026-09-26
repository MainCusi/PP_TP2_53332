package actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String titulo;
    protected int cupoMaximo;
    public static final int CUPO_MINIMO = 1;

    protected List<Inscripcion> inscripciones;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
        this.inscripciones = new ArrayList<>();
    }

    public final void mostrarIdentificacion() {
        System.out.println("Actividad: " + titulo + " (ID: " + id + ") | Tipo: " + getTipo());
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException("No hay cupo disponible en la actividad: " + titulo + " (Cupo máximo: " + cupoMaximo + ")");
        }
        Inscripcion inscripcion = new Inscripcion(estudiante);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("   - Sin inscripciones registradas.");
            return;
        }
        for (Inscripcion ins : inscripciones) {
            System.out.println("   - Estudiante: " + ins.getEstudiante().getNombre() + " | Estado: " + ins.getEstado());
        }
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getCupoMaximo() { return cupoMaximo; }
    public List<Inscripcion> getInscripciones() { return inscripciones; }
}