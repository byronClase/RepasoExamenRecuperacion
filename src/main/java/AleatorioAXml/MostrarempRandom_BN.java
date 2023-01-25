package AleatorioAXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MostrarempRandom_BN {
    public static void leerFA(){
        String ruta = ".\\empRandom_BN.dat";
        File fichero = new File(ruta);
        //declara el fichero de acceso aleatorio
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fichero, "r");
            //
            int id, dep, posicion;
            Double salario;
            char apellido[] = new char[10], aux;
            boolean carnet;
            posicion=0; //para situarnos al principio
            for(;;){ //recorro el fichero
                file.seek(posicion); //nos posicionamos en posicion
                id=file.readInt(); // obtengo id de empleado
                for (int i = 0; i < apellido.length; i++) {
                    aux = file.readChar();//recorro uno a uno los caracteres del apellido
                    apellido[i] = aux; //los voy guardando en el array
                }
                String apellidoS= new String(apellido);//convierto a String el array
                dep=file.readInt();//obtengo dep
                salario=file.readDouble(); //obtengo salario
                carnet=file.readBoolean();
                System.out.println("ID: " + id + ", Apellido: "+ apellidoS +
                        ", Departamento: "+dep + ", Salario: " + salario+", Carnet: "+carnet);
                posicion= posicion + 37; // me posiciono para el sig empleado
                //Cada empleado ocupa 37 bytes (4+20+4+8+1) por el boolean
                //Si he recorrido todos los bytes salgo del for
                if (file.getFilePointer()==file.length())break;

            }//fin bucle for
            file.close(); //cerrar fichero
        } catch (IOException e) {
            System.err.println("ERROR > "+ e);
        }

    }
}
