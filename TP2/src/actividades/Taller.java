package actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Taller extends actividades.Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        return requiereNotebook ? 5000.0 : 2000.0;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE ASISTENCIA (Taller) - " + ENTIDAD_EMISORA + "\n" +
                "Otorgado a: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                "Por completar satisfactoriamente el Taller: " + getTitulo();
    }

    public boolean isRequiereNotebook() { return requiereNotebook; }
}