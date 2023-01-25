package AleatorioAXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CrearFicheroAleatorio {
    public static void crearFA() {
        String ruta = ".\\empRandom_BN.dat";
        File fichero = new File(ruta);
        // declara el fichero de acceso aleatorio
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fichero, "rw");
            // arrays con los datos
            String apellido[] = {"FERNANDEZ", "GIL", "LOPEZ", "RAMOS", "SEVILLA", "CASILLA", "REY"}; // apellidos
            int dep[] = {10, 20, 10, 10, 30, 30, 20}; // departamentos
            double salario[] = {1000.45, 2400.60, 3000.0, 1500.56, 2200.0, 1435.87, 2000.0};// salarios
            boolean carnet[] = {false, true, false, true, true, true, true};

            StringBuffer buffer = null;// buffer para almacenar apellido
            int n = apellido.length;// numero de elementos del array

            for (int i = 0; i < n; i++) { // recorro los arrays
                file.writeInt(i + 1); // uso i+1 para identificar empleado
                buffer = new StringBuffer(apellido[i]);
                buffer.setLength(10); // 10 caracteres para el apellido
                file.writeChars(buffer.toString());// insertar apellido
                file.writeInt(dep[i]); // insertar departamento
                file.writeDouble(salario[i]);// insertar salario
                file.writeBoolean(carnet[i]);
            }
            file.close(); // cerrar fichero
            System.out.println("Se ha creado el fichero, compruebalo en " + ruta);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR > " + e);
        } catch (IOException e2) {
            System.err.println("ERROR > " + e2);
        } catch (IllegalArgumentException e3) {
            System.err.println("ERROR > " + e3);
        }

    }
}
