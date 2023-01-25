package AleatorioAXml;

import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        Menu t1 = new Menu();
        CrearFicheroAleatorio c1 = new CrearFicheroAleatorio();
        MostrarempRandom_BN l1 = new MostrarempRandom_BN();
        CrearEmpleadoXml testXml = new CrearEmpleadoXml();
        LecturaXmlEmpleado testLecturaXml = new LecturaXmlEmpleado();

        //c1.crearFA();
        //l1.leerFA();
        //t1.miMenu();
        //testXml.crearXml();
        testLecturaXml.leerXml();
    }

}
