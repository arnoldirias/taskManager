package gestiondeprocesos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arnold_Irias
 */
public class Proceso {

    private String nombre;
    private int PID;
    private double usoCPU;
    private double usoMemoria;
    private double promTiempo;
    private int numSP;
    private int maxSP;
    private List<Datos> Almacen = new ArrayList<Datos>();

    Proceso(String nombre, int PID) {
        this.nombre = nombre;
        this.PID = PID;
        this.usoCPU = 0;
        this.usoMemoria = 0;
        this.promTiempo = 0;
        this.numSP = 0;
        this.maxSP = 0;
    }

    public void agregar(double usoCPU, double usoMemoria, double tiempoConsulta) {
        Datos miDato = new Datos(usoCPU, usoMemoria, tiempoConsulta);
        Almacen.add(miDato);
        addSP();
    }

    public Datos generarResultados() {
        double usoCPU = 0.0;
        double usoMemoria = 0.0;
        double tiempoConsulta = 0.0;

        if (this.maxSP > 0) {
            for (Datos miDato : this.Almacen) {
                usoCPU += miDato.getUsoCPU();
                usoMemoria += miDato.getUsoMemoria();
                tiempoConsulta += miDato.getTiempoConsulta();

            }
        }

        this.setUsoCPU(usoCPU / this.maxSP);
        this.setUsoMemoria(usoMemoria / this.maxSP);
        this.setPromTiempo(tiempoConsulta);

        return new Datos(usoCPU, usoMemoria, tiempoConsulta);
    }

    public void addSP() {
        this.maxSP++;
        this.numSP++;
    }

    public void decSP() {
        this.numSP--;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public double getUsoCPU() {
        return usoCPU;
    }

    /**
     *
     * @param usoCPU
     */
    public void setUsoCPU(double usoCPU) {
        this.usoCPU = usoCPU;
    }

    public double getUsoMemoria() {
        return usoMemoria;
    }

    public void setUsoMemoria(double usoMemoria) {
        this.usoMemoria = usoMemoria;
    }

    public double getPromTiempo() {
        return promTiempo;
    }

    public void setPromTiempo(double promTiempo) {
        this.promTiempo = promTiempo;
    }

    public int getNumSP() {
        return numSP;
    }

    public void setNumSP(int numSP) {
        this.numSP = numSP;
    }

    public int getMaxSP() {
        return maxSP;
    }

    public void setMaxSP(int maxSP) {
        this.maxSP = maxSP;
    }

    public List<Datos> getAlmacen() {
        return Almacen;
    }

    public void setAlmacen(List<Datos> Almacen) {
        this.Almacen = Almacen;
    }

}
