/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprocesos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arnold_Irias
 */
public class ContenedorProcesos {

    private int numProcesos = 0;
    private List<Proceso> Procesos = new ArrayList<Proceso>();

    public void Agregar2(String nombre, int PID, double usoCPU, double usoMemoria, double tiempoConsulta) {
        boolean encontrado = false;

        for (Proceso p : this.getProcesos()) {
            if ((p.getNombre().equals(nombre)) && (p.getPID() == PID)) {
                p.agregar(usoCPU, usoMemoria, tiempoConsulta);
                encontrado = true;
            }
        }

        if (!encontrado) {
            Proceso nuevo = new Proceso(nombre, PID);
            nuevo.agregar(usoCPU, usoMemoria, tiempoConsulta);
            getProcesos().add(nuevo);
            setNumProcesos(getNumProcesos() + 1);
        }
    }

    public boolean BuscarSiExiste2(String nombre, int PID) {
        for (Proceso p : this.getProcesos()) {
            if ((p.getNombre().equals(nombre)) && (p.getPID() == PID)) {
                return true;
            }
        }
        return false;
    }

    public Proceso Buscar2(String nombre, int PID) {
        for (Proceso p : this.getProcesos()) {
            if ((p.getNombre().equals(nombre)) && (p.getPID() == PID)) {
                return p;
            }
        }
        return null;
    }

    public boolean Eliminar2(String nombre, int PID) {
        for (Proceso p : this.getProcesos()) {
            if ((p.getNombre().equals(nombre)) && (p.getPID() == PID)) {
                getProcesos().remove(p);
                return true;
            }
        }
        return false;
    }

    public void Agregar(Proceso nuevoProceso) {
        getProcesos().add(nuevoProceso);
        setNumProcesos(getNumProcesos() + 1);
    }

    public boolean BuscarSiExiste(Proceso nuevoProceso) {
        if (getProcesos().indexOf(nuevoProceso) > -1) {
            return true;
        }
        return false;
    }

    public Proceso Buscar(Proceso nuevoProceso) {
        if (BuscarSiExiste(nuevoProceso) == true) {
            return getProcesos().get(getProcesos().indexOf(nuevoProceso));
        }
        return null;
    }

    public boolean estaVacia() {
        return getProcesos().isEmpty();
    }

    public void Eliminar(Proceso nuevoProceso) {
        getProcesos().remove(nuevoProceso);
    }

    public List<Proceso> getProcesos() {
        return Procesos;
    }

    public void setProcesos(List<Proceso> Procesos) {
        this.Procesos = Procesos;
    }

    public int getNumProcesos() {
        return numProcesos;
    }

    public void setNumProcesos(int numProcesos) {
        this.numProcesos = numProcesos;
    }

}
