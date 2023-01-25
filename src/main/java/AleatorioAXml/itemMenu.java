package AleatorioAXml;

import java.io.File;
import java.util.Scanner;

public interface itemMenu {
    boolean consulta(File fichero, int miEmpleado);
    void insertar(File fichero, Scanner sc, int id);
    void modificar(File fichero, int identificacion, double nuevoSalario);
    void borrar(File f, File f2,int identificacion);

}
