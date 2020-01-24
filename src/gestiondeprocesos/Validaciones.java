/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprocesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Arnold_Irias
 */
public class Validaciones {

    public boolean ValidacionGeneral(String valores[]) {
        if (ValidarUsoCPU(valores[0]) == false) {
            return false;
        }
        if (ValidarMemoria(valores[1]) == false) {
            return false;
        }
        if (ValidarSP(valores[2]) == false) {
            return false;
        }
        if (ValidarTiempo(valores[3]) == false) {
            return false;
        }
        return true;
    }

    public boolean ValidarUsoCPU(String cadena) {
        try {
            double usoCPU = Double.parseDouble(cadena);
            if (usoCPU <= 0 || usoCPU > 99) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public boolean ValidarTiempo(String cadena) {
        try {
            int tiempo = Integer.parseInt(cadena);
            if (tiempo <= 0) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public boolean ValidarSP(String cadena) {
        try {
            int SP = Integer.parseInt(cadena);
            if (SP <= 0) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public boolean ValidarMemoria(String cadena) {
        try {
            double memoria = Double.parseDouble(cadena);
            if ((memoria <= 0) || (memoria > Double.parseDouble(ObtenerRAMenMB()))) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    public Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }

    public String ObtenerRAMenMB() {
        Process p;
        String capacidad = "";
        try {
            p = Runtime.getRuntime().exec("wmic MemoryChip get Capacity");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            int indice = 0;
            while ((line = reader.readLine()) != null) {
                if (indice == 2) {
                    capacidad = line;
                }
                indice++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double capacidadEnMb = Double.parseDouble(capacidad) / (1024 * 1024);
        return String.valueOf(capacidadEnMb);
    }

    public void MatarProceso(String PID) {

        String cmd = "taskkill /F /T /PID " + PID;
        try {
            Runtime.getRuntime().exec(cmd);
            JFrame Matando = null;
            Matando = new Matando();
            Matando.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(AdministradordeTareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean ProcesoEnEjecucion(String PID) throws IOException {
        Process proceso = Runtime.getRuntime().exec("tasklist");
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                proceso.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(PID)) {
                return true;
            }
        }

        return false;

    }

    public void MatarConjuntoDeProcesos(String PIDS[]) {
        JFrame Matando = new Matando();
        Matando.setVisible(true);

        for (int i = 0; i < PIDS.length; i++) {
            try {
                if (ProcesoEnEjecucion(PIDS[i]) == true) {
                    String cmd = "taskkill /F /T /PID " + PIDS[i];
                    Runtime.getRuntime().exec(cmd);
                } else {
                    JOptionPane.showMessageDialog(null, "No se está ejecutando el proceso con PID: " + PIDS[i]);
                }
            } catch (IOException ex) {
                Logger.getLogger(Validaciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Matando.setVisible(false);
    }
}
