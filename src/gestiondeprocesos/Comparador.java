package gestiondeprocesos;

/**
 *
 * @author Arnold_Irias
 */
public class Comparador {

    private double maxUsoCPU;
    private double maxUsoMemoria;
    private double maxTiempo;
    private int maxSP;

    public Comparador(double maxUsoCPU, double maxUsoMemoria, double maxTiempo, int maxSP) {
        this.maxUsoCPU = maxUsoCPU;
        this.maxUsoMemoria = maxUsoMemoria;
        this.maxTiempo = maxTiempo;
        this.maxSP = maxSP;
    }

    public double getMaxUsoCPU() {
        return maxUsoCPU;
    }

    public void setMaxUsoCPU(double maxUsoCPU) {
        this.maxUsoCPU = maxUsoCPU;
    }

    public double getMaxUsoMemoria() {
        return maxUsoMemoria;
    }

    public void setMaxUsoMemoria(double maxUsoMemoria) {
        this.maxUsoMemoria = maxUsoMemoria;
    }

    public double getMaxTiempo() {
        return maxTiempo;
    }

    public void setMaxTiempo(double maxTiempo) {
        this.maxTiempo = maxTiempo;
    }

    public int getMaxSP() {
        return maxSP;
    }

    public void setMaxSP(int maxSP) {
        this.maxSP = maxSP;
    }

}
