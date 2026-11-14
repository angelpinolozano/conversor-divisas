# Conversor de Divisas (Java)

Aplicación Java compuesta por dos procesos independientes: una aplicación hija que convierte una cantidad de una divisa a otra, y una aplicación padre que la lanza con `ProcessBuilder`, recoge su resultado y lo muestra por consola.

## Aplicación hija: ConversorDivisas

Recibe por línea de comandos tres parámetros: la cantidad, la divisa de origen y la divisa de destino (por ejemplo `100 EUR USD`).

- Trabaja con tasas fijas que indican cuánto vale 1 euro en cada divisa: `EUR` (1,00), `USD` (1,10) y `GBP` (0,85).
- Convierte pasando la cantidad a euros y después a la divisa de destino: `cantidad / tasaOrigen * tasaDestino`.
- Escribe por la salida estándar únicamente el resultado con dos decimales.
- Los errores se escriben por la salida de error y se termina con `System.exit`:
  - **Código 1**: no se reciben exactamente tres parámetros, o la cantidad no es un número.
  - **Código 2**: alguna de las divisas no está en la tabla.

## Aplicación padre: LanzadorConversor

Lanza `ConversorDivisas` como proceso hijo y gestiona su resultado:

- **ProcessBuilder**: construye el comando `java -cp <classpath> conversor.ConversorDivisas <parámetros>`.
- Lee la salida estándar y la salida de error del hijo.
- Espera a que termine con `waitFor()` y obtiene su código de salida.
- Si el código es 0, muestra el resultado (por ejemplo `100 EUR -> USD: 110.00`).
- Si el código no es 0, muestra un mensaje de error con el código y el texto que ha escrito el hijo.

## Pruebas realizadas

| Cantidad, origen y destino | Resultado |
|----------------------------|-----------|
| 100 EUR USD | 110.00 |
| 55 USD EUR | 50.00 |
| 50 GBP USD | 64.71 |
| 10 EUR JPY | Error, código 2 |
| abc EUR USD | Error, código 1 |

## Conceptos utilizados

- `ProcessBuilder` y `Process`
- `getInputStream` y `getErrorStream`
- `waitFor()` y códigos de salida
- `System.exit`
- `System.getProperty("java.home")` y `System.getProperty("java.class.path")`
- `BufferedReader` e `InputStreamReader`
- `Map` y `String.format(Locale.US, "%.2f", valor)`
- Control de versiones (`Git / GitHub`)

## Autor

Ángel Pino Lozano
