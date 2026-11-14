package conversor;

import java.util.Locale;
import java.util.Map;

public class ConversorDivisas {

    // Cuánto vale 1 euro en cada divisa
    private static final Map<String, Double> TASAS = Map.of(
            "EUR", 1.00,
            "USD", 1.10,
            "GBP", 0.85
    );

    public static void main(String[] args) {
        // Error 1: no son exactamente tres parámetros
        if (args.length != 3) {
            System.err.println("Se esperan 3 parametros: cantidad origen destino");
            System.exit(1);
        }

        // Error 1: la cantidad no es un número
        double cantidad = 0;
        try {
            cantidad = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("La cantidad no es un numero: " + args[0]);
            System.exit(1);
        }

        String origen = args[1].toUpperCase();
        String destino = args[2].toUpperCase();

        // Error 2: alguna divisa no está en la tabla
        if (!TASAS.containsKey(origen) || !TASAS.containsKey(destino)) {
            System.err.println("Divisa no reconocida: " + origen + " o " + destino);
            System.exit(2);
        }

        double resultado = cantidad / TASAS.get(origen) * TASAS.get(destino);

        // Solo el resultado, con dos decimales, por la salida estándar
        System.out.println(String.format(Locale.US, "%.2f", resultado));
    }
}