package actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Curso extends actividades.Actividad implements Certificable {
    private int nivel;

    public Curso(int id, String titulo, int cupoMaximo, int nivel) {
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return nivel * 3500.0;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE APROBACIÓN (Curso Nivel " + nivel + ") - " + ENTIDAD_EMISORA + "\n" +
                "Otorgado a: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                "Por completar satisfactoriamente el Curso: " + getTitulo();
    }

    public int getNivel() { return nivel; }
}