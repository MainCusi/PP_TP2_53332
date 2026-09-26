Paso a Paso del Programa (Ejecución de App.java)

Paso 1:

Se crea un Evento Universitario (E101 - Congreso Tecnológico) y se le agrega una Charla de IA con cupo máximo de 1 estudiante.

Se inscribe al primer estudiante exitosamente.

Se guarda (persiste) el evento en un archivo en el disco y se vuelve a leer para verificar que funciona la recuperación de datos.

Se intenta inscribir a un segundo estudiante: como la charla tenía cupo de 1, el sistema atrapa la excepción CupoExcedidoException y muestra un mensaje de advertencia en consola sin romper el programa.

Paso 2:

Se crea un segundo Evento Universitario (E102 - Simposio de Desarrollo) y se le asigna la sala Auditorio Central.

Se agregan tres actividades de distintos tipos:

Una Charla (Keynote).

Un Taller (Git & GitHub).

Un Curso (Java Profesional).

Se inscribe a un estudiante en las tres actividades.

El sistema evalúa qué actividades son certificables:

Genera e imprime los certificados para el Taller y el Curso.

Ignora la Charla (ya que las charlas no otorgan certificado).

Paso 3:

El sistema filtra automáticamente la lista de actividades del evento por su tipo exacto (Charla, Taller o Curso).

Muestra en consola cuántas actividades hay de cada tipo.

Calcula de forma independiente el costo de materiales para cada tipo de actividad y luego el costo total del evento con IVA incluido.

Paso 4:

Se confirman las inscripciones del estudiante, lo que genera automáticamente un Ticket de Acceso para cada una.

Se inicia un hilo secundario en segundo plano encargado de enviar los tickets.

Mientras los tickets se están enviando (con una pequeña simulación de tiempo de red), el hilo principal no se detiene y continúa mostrando la información del evento en pantalla.

Una vez que el hilo secundario termina de enviar todos los tickets, el programa finaliza su ejecución de forma limpia.
