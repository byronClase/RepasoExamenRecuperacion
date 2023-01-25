package AleatorioAXml;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Menu implements itemMenu{
    public boolean consulta(File fichero, int miEmpleado){
        RandomAccessFile file = null;
        DecimalFormat formato = new DecimalFormat("##,##0.00");
        boolean tuDevolucion=true;
        try {
            file = new RandomAccessFile(fichero, "r");
            int id, dep, posicion;
            double salario;
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
                posicion= posicion + 37; // me posiciono para el sig empleado
                //Cada empleado ocupa 37 bytes (4+20+4+8+1) por el boolean
                //Si he recorrido todos los bytes salgo del for
                if(miEmpleado==id){
                    tuDevolucion=false;
                    String miFormato = formato.format(salario);
                    System.out.println("ID: " + id + ", Apellido: "+ apellidoS +
                            ", Departamento: "+dep + ", Salario: " + miFormato+", Carnet: "+carnet);
                }
                if (file.getFilePointer()==file.length())break;

            }//fin bucle for

            file.close(); //cerrar fichero
        } catch (IOException e) {
            System.err.println("ERROR > "+ e);
        }catch (Exception e){
            System.err.println("No existe ese empleado");
        }
        return tuDevolucion;
    }
    public int miValor(Scanner sc, String mensaje){
        System.out.println(mensaje);
        int miValor = sc.nextInt();
        return miValor;
    }
    public boolean miBool(Scanner sc, String mensaje){
        System.out.println(mensaje);
        boolean miBool = sc.nextBoolean();
        return miBool;
    }
    public double miComa(Scanner sc, String mensaje){
        System.out.println(mensaje);
        double miComa = sc.nextDouble();
        return miComa;
    }
    public String miCadena(Scanner sc, String mensaje){
        System.out.println(mensaje);
        String miCadena = sc.next();
        return miCadena;
    }
    public void insertar(File fichero, Scanner sc, int id) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fichero, "rw");
            StringBuffer buffer = null; // bufer para almacenar apellido
            String apellido = miCadena(sc,"Nuevo apellido: "); // apellido a insertar
            double salario = miComa(sc,"Nuevo salario: "); // salario
            int dep = miValor(sc,"Nuevo departamento: "); // dep del empleado
            boolean coche = miBool(sc,"Coche: ");
            long posicion = (id - 1) * 37; // calculamos la posicion
            file.seek(posicion);
            file.writeInt(id); // se escribe id
            buffer = new StringBuffer(apellido);
            buffer.setLength(10); // 10 caracteres para el apellido
            file.writeChars(buffer.toString());// insertar apellido
            file.writeInt(dep); // insertar departamento
            file.writeDouble(salario);// insertar salario
            file.writeBoolean(coche);
            posicion = posicion + 37;


        } catch (Exception e) {
            System.out.println("ERROR > "+e);
        }

    }
    public void modificar(File fichero, int identificacion, double nuevoSalario){
        DecimalFormat formato = new DecimalFormat("##,##0.00");

        RandomAccessFile file;
        try {
            double salario;
            double aux;
            long posicionAux;
            long posicion = (identificacion - 1) * 37; // (4+20+4+8+1) modifico salario y dep y coche
            posicion = posicion + 4 + 20 + 4; // sumo el tamaño de ID+apellido+salrio
            posicionAux = posicion;
            file = new RandomAccessFile(fichero,"rw");
            file.seek(posicion); // nos posicionamos
            salario = file.readDouble(); // obtengo salario
            aux = (double) salario;
            salario += nuevoSalario;
            file.seek(posicionAux); // nos posicionamos
            file.writeDouble((double) salario);// modif salario
            System.out.println("ID:" + identificacion);
            String salarioNuevo = formato.format(salario);
            System.out.println("Nuevo salario: " + salarioNuevo);
            String salarioAntiguo = formato.format(aux);
            System.out.println("Antiguo salario: " +salarioAntiguo);
        } catch (IOException e) {
            System.err.println("ERROR > "+e);
        }

    }
    public void borrar(File f, File f2,int identificacion){
        RandomAccessFile file = null;
        crearBorrados(f2, identificacion);
        StringBuffer buffer = null; // bufer para almacenar apellido
        String apellido = "-1"; // apellido a insertar
        Double salario = 0.0; // salario
        int id = -1; // id del empleado
        int dep = 0; // dep del empleado
        boolean coche = false;
        long posicion = (identificacion - 1) * 37; // calculamos la posicion
        try {
            file = new RandomAccessFile(f,"rw");
            file.seek(posicion); // nos posicionamos
            file.writeInt(id); // se escribe id
            buffer = new StringBuffer(apellido);
            buffer.setLength(10); // 10 caracteres para el apellido
            file.writeChars(buffer.toString());// insertar apellido
            file.writeInt(dep); // insertar departamento
            file.writeDouble(salario);// insertar salario
            file.writeBoolean(coche);
        } catch (IOException e) {
            System.err.println("ERROR > "+e);
        }


    }
    public void listado_opciones(){
        System.out.println("1. Consultar (id necesario)");
        System.out.println("2. Insertar (id necesario)");
        System.out.println("3. Aumentar salario (modificar, id necesario) ");
        System.out.println("4. Borrar un registro (id necesario) ");
        System.out.println("0. Para salir");
    }
    public void crearBorrados(File file2, int id) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(file2,true);
            pw = new PrintWriter(fw);
            pw.println("ID: "+id);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public  void leerBorrados(File file2) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file2);
            br = new BufferedReader(fr);
            String line ="";
            while((line=br.readLine())!=null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public  void miMenu(){
        Scanner sc = new Scanner(System.in);
        String ruta = ".\\empRandom_BN.dat";
        String ruta2 =".\\borrados_BN.txt";
        File fichero = new File(ruta);
        File fichero2 = new File(ruta2);
        int opcion;
        do{
            listado_opciones();
            opcion = miValor(sc,"Introduce una opcion: ");
            if(opcion==1){
                int codigo = miValor(sc,"id del empleado:");
                if(consulta(fichero, codigo)) System.out.println("No existe el registro con codigo: "+codigo);
            } else if (opcion==2) {
                int codigo = miValor(sc,"id del empleado:");
                if(consulta(fichero, codigo) && codigo>0){
                    insertar(fichero,sc,codigo);
                }else{
                    System.out.println("El registro con codigo "+codigo+" existe.");
                }
            }
            else if(opcion==3){
                int codigo = miValor(sc,"id del empleado:");
                if(!consulta(fichero, codigo) && codigo>0){
                    double subida = miComa(sc,"cantidad:");
                    modificar(fichero, codigo, subida);
                }else{
                    System.out.println("El registro con codigo "+codigo+" no se puede modificar.");
                }
            } else if (opcion==4) {
                int codigo = miValor(sc,"id del empleado:");
                if(!consulta(fichero, codigo) && codigo>0){
                    borrar(fichero, fichero2,codigo);
                }else{
                    System.out.println("El registro con codigo "+codigo+" no se puede borrar.");
                }
            }
        }while(opcion!=0);
        if(fichero2.exists()){
            leerBorrados(fichero2);
        }
        System.out.println("Has salido.");
    }
}
