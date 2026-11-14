package conversor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class LanzadorConversor {

    public static void main(String[] args) {
        // Los cinco casos de prueba del enunciado
        String[][] pruebas = {
                {"100", "EUR", "USD"},
                {"55", "USD", "EUR"},
                {"50", "GBP", "USD"},
                {"10", "EUR", "JPY"},
                {"abc", "EUR", "USD"}
        };

        for (String[] p : pruebas) {
            convertir(p[0], p[1], p[2]);
        }
    }

    private static void convertir(String cantidad, String origen, String destino) {
        // Ruta de java y classpath actuales
        String java = System.getProperty("java.home") + "/bin/java";
        String classpath = System.getProperty("java.class.path");

        // java -cp <classpath> conversor.ConversorDivisas <cantidad> <origen> <destino>
        ProcessBuilder pb = new ProcessBuilder(
                java, "-cp", classpath, "conversor.ConversorDivisas",
                cantidad, origen, destino);

        try {
            Process proceso = pb.start();

            // Leer salida estándar del hijo
            String salida = leer(proceso.getInputStream());
            // Leer salida de error del hijo
            String error = leer(proceso.getErrorStream());

            // Esperar a que termine y obtener el código de salida
            int codigo = proceso.waitFor();

            if (codigo == 0) {
                System.out.println(cantidad + " " + origen + " -> " + destino + ": " + salida);
            } else {
                System.out.println("ERROR (codigo " + codigo + ") al convertir "
                        + cantidad + " " + origen + " " + destino + ": " + error);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("No se pudo lanzar el proceso hijo: " + e.getMessage());
        }
    }

    // Lee todo el contenido de un flujo y lo devuelve como texto
    private static String leer(java.io.InputStream flujo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(flujo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append(System.lineSeparator());
                }
                sb.append(linea);
            }
        }
        return sb.toString();
    }
}