/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprocesos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Arnold_Irias
 */
public class Configuracion {

    Validaciones validacion = new Validaciones();

    public String[] leerArchivo() {
        String archivoConf = "config.txt";
        String cadena;
        String[] valores = new String[4];
        int indice = 0;
        try {
            FileReader lector = new FileReader(archivoConf);
            BufferedReader BFlector = new BufferedReader(lector);
            while ((cadena = BFlector.readLine()) != null) {
                valores[indice] = cadena;
                indice++;
            }
            for (int i = 0; i < valores.length; i++) {
                valores[i] = recuperarValor(valores[i]);
            }
            BFlector.close();
            if (validacion.ValidacionGeneral(valores) == false) {
                return null;
            } else {
                return valores;
            }
        } catch (FileNotFoundException ex) {
            crearArchivo("0.0", "12345", "1", "60");
            valores = leerArchivo();
            return valores;
        } catch (IOException ex) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void crearArchivo(String usoCPU, String Memoria, String SP, String Tiempo) {
        try {
            FileWriter archivo = new FileWriter("config.txt");
            PrintWriter escritor = new PrintWriter(archivo);
            escritor.println("UsoCPU=" + usoCPU);
            escritor.println("UsoMemoria=" + Memoria);
            escritor.println("SP=" + SP);
            escritor.println("Tiempo=" + Tiempo);
            archivo.close();

        } catch (IOException ex1) {

        }
    }

    public String recuperarValor(String cadena) {
        String partes[] = cadena.split("=");
        return partes[1];
    }
}
