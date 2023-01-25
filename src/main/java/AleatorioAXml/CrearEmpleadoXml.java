package AleatorioAXml;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CrearEmpleadoXml {
    public static void crearXml() throws IOException {
        File fichero = new File(".\\empRandom_BN.dat");
        RandomAccessFile file = new RandomAccessFile(fichero, "r");
        int id, posicion = 0; // para situarnos al principio del fichero

        char apellido[] = new char[10], auxa;// apellido
        int departamento;
        double salario;
        boolean coche;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Empleados", null);
            document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

            for (;;) {
                file.seek(posicion); // nos posicionamos
//              id
                id = file.readInt();
//				apellido
                for (int k = 0; k < apellido.length; k++) {
                    auxa = file.readChar();// recorro uno a uno los caracteres del apellido
                    apellido[k] = auxa; // los voy guardando en el array
                }
                String apellidoS = new String(apellido);// convierto a String el array
//              departamento
                departamento=file.readInt();
                // salario
                salario = file.readDouble();
                //carnet
                coche = file.readBoolean();
                if (id > 0) { // id validos a partir de 1
                    Element raiz = document.createElement("empleado"); // nodo empleado
                    document.getDocumentElement().appendChild(raiz);

//					CrearElemento("id",Integer.toString(id), raiz, document); //a�adir ID
                    CrearElemento("id", Integer.toString(id), raiz, document); // dni
                    CrearElemento("apellido", apellidoS.trim(), raiz, document); // Apellido
                    CrearElemento("departamento", Integer.toString(departamento), raiz, document); // nombre
                    CrearElemento("salario", Double.toString(salario), raiz, document); // edad
                    CrearElemento("coche", Boolean.toString(coche), raiz, document); // casado

                }
                posicion = posicion + 37; // me posiciono para el sig persona 18 + 12+ 20 + 4 + 4 + 18 + 100
                if (file.getFilePointer() == file.length())
                    break;
            } // fin del for que recorre el fichero
            Source source = new DOMSource(document);
            Result result = new StreamResult(new java.io.File(".\\Empleados.xml"));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            Result console= new StreamResult(System.out);
            transformer.transform(source, console);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        file.close(); // cerrar fichero
    }// fin de main
//Inserci�n de los datos del empleado

    public static void CrearElemento(String datoEmp, String valor, Element raiz, Document document) {
        Element elem = document.createElement(datoEmp); // creamos hijo
        Text text = document.createTextNode(valor); // damos valor
        raiz.appendChild(elem); // pegamos el elemento hijo a la raiz
        elem.appendChild(text); // pegamos el valor
    }
}
