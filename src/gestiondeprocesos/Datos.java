package gestiondeprocesos;

/**
 *
 * @author Arnold_Irias
 */
public class Datos {

    private double usoCPU;
    private double usoMemoria;
    private double tiempoConsulta;

    Datos(double usoCPU, double usoMemoria, double tiempoConsulta) {
        this.usoCPU = usoCPU;
        this.usoMemoria = usoMemoria;
        this.tiempoConsulta = tiempoConsulta;
    }

    public double getUsoCPU() {
        return usoCPU;
    }

    public void setUsoCPU(double usoCPU) {
        this.usoCPU = usoCPU;
    }

    public double getUsoMemoria() {
        return usoMemoria;
    }

    public void setUsoMemoria(double usoMemoria) {
        this.usoMemoria = usoMemoria;
    }

    public double getTiempoConsulta() {
        return tiempoConsulta;
    }

    public void setTiempoConsulta(double tiempoConsulta) {
        this.tiempoConsulta = tiempoConsulta;
    }

}
