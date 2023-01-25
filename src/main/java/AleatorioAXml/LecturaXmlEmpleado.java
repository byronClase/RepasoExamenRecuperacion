package AleatorioAXml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LecturaXmlEmpleado {
    public static void leerXml() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("Empleados.xml"));

            document.getDocumentElement().normalize();
            System.out.println("Elemento raíz: " + document.getDocumentElement().getNodeName());
            // crea una lista con todos los nodos persona
            NodeList empleados = document.getElementsByTagName("empleado");
            // recorrer la lista
            for (int i = 0; i < empleados.getLength(); i++) {

                Node empe = empleados.item(i); // obtener un nodo

                if (empe.getNodeType() == Node.ELEMENT_NODE) {// tipo de nodo
                    Element elemento = (Element) empe; // obtener los elementos el nodo
                    System.out.println("ID: " + getNodo("id", elemento));
                    System.out.println("Apellido: " + getNodo("apellido", elemento));
                    System.out.println("Departamento: " + getNodo("departamento", elemento));
                    System.out.println("Salario: " + getNodo("salario", elemento));
                    System.out.println("Carnet: " + getNodo("coche", elemento));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// fin de main
//obtener la informaci�n de un nodo

    public static String getNodo(String etiqueta, Element elem) {
        NodeList nodo = elem.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node valornodo = (Node) nodo.item(0);
        return valornodo.getNodeValue();// devuelve el valor del nodo
    }
}
