package gestiondeprocesos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arnold_Irias
 */
public class AnalisisProcesos {

    ContenedorProcesos miContenedor;
    Comparador miComparador;
    private List<Proceso> ProcesosMaxCPU = new ArrayList<Proceso>();
    private List<Proceso> ProcesosMaxMemoria = new ArrayList<Proceso>();
    private List<Proceso> ProcesosMaxTiempo = new ArrayList<Proceso>();
    private List<Proceso> ProcesosMaxSP = new ArrayList<Proceso>();
    private List<Proceso> OtrosProcesos = new ArrayList<Proceso>();
    private List<Proceso> ProcesosMaxUso = new ArrayList<Proceso>();
    private double memoria;

    public AnalisisProcesos(ContenedorProcesos miContenedor, Comparador miComparador, long memoria) {
        this.miContenedor = miContenedor;
        this.miComparador = miComparador;
        this.memoria = memoria / (1024 * 1024); //Comparamos en MBs
    }

    public void generarResultados() {
        boolean estado;
        //System.out.println("PID,usoCPU,UsoMemoria,PromTiempo,MaxSP,Nombre");
        for (Proceso p : miContenedor.getProcesos()) {
            estado = false;
            p.generarResultados();

            if (p.getUsoCPU() >= miComparador.getMaxUsoCPU()) {
                this.ProcesosMaxCPU.add(p);
                estado = true;
            }

            if (p.getUsoMemoria() / 100 >= miComparador.getMaxUsoMemoria() / memoria) {
                this.ProcesosMaxMemoria.add(p);
                estado = true;
                //System.out.println("MAXMEMORIA: "+p.getUsoMemoria()/100+"--VRS: "+miComparador.getMaxUsoMemoria()/memoria);
            }
            /*
             if(p.getPromTiempo()>=miComparador.getMaxTiempo()){
             this.ProcesosMaxTiempo.add(p);
             estado=true;
             }
             if(p.getMaxSP()>=miComparador.getMaxSP()){
             this.ProcesosMaxSP.add(p);
             estado=true;
             }
             */
            if (!estado) {
                this.OtrosProcesos.add(p);
            } else {
                this.ProcesosMaxUso.add(p);
            }

            //System.out.format(" %8d %5.1f %8.1f %10s %10s %s%n",
            //     p.getPID(), p.getUsoCPU(),p.getUsoMemoria(),p.getPromTiempo(),p.getMaxSP(),p.getNombre());
            //System.out.println(p.getNombre()+","+p.getPID()+","+p.getUsoCPU()+","+p.getUsoMemoria()+","+p.getPromTiempo()+","+p.getMaxSP());
        }
    }

    public List<String> generarResultadosSP() {
        List<Proceso> otroContenedor = miContenedor.getProcesos();
        List<Proceso> otroContenedor2 = miContenedor.getProcesos();
        List<String> pids = new ArrayList<String>();
        int[] estado = new int[otroContenedor.size()];
        int y = 0;
        for (Proceso p : otroContenedor) {
            int x = 0;
            String tmp = String.valueOf(p.getPID());
            boolean encontrado = false;
            p.generarResultados();
            p.setMaxSP(1);
            estado[y] = 1;

            for (Proceso q : otroContenedor2) {
                if ((q.getPID() != p.getPID()) && (q.getNombre().equals(p.getNombre())) && (estado[x] == 0)) {
                    q.generarResultados();
                    p.setMaxSP(p.getMaxSP() + 1);
                    tmp += "," + String.valueOf(q.getPID());
                    p.setUsoCPU(p.getUsoCPU() + q.getUsoCPU());
                    p.setUsoMemoria(p.getUsoMemoria() + q.getUsoMemoria());
                    p.setPromTiempo(p.getPromTiempo() + q.getPromTiempo());
                    encontrado = true;
                    estado[x] = 1;
                }
                x++;
            }

            if ((p.getMaxSP() >= miComparador.getMaxSP())) {
                pids.add(tmp + ":" + p.getNombre() + "," + p.getMaxSP() + "," + p.getUsoCPU() / p.getMaxSP() + "," + p.getUsoMemoria() / p.getMaxSP() + "," + p.getPromTiempo());
                System.out.println(tmp);
            }
            y++;
        }
        return pids;
    }

    public List<Proceso> getProcesosMaxCPU() {
        return ProcesosMaxCPU;
    }

    public void setProcesosMaxCPU(List<Proceso> ProcesosMaxCPU) {
        this.ProcesosMaxCPU = ProcesosMaxCPU;
    }

    public List<Proceso> getProcesosMaxMemoria() {
        return ProcesosMaxMemoria;
    }

    public void setProcesosMaxMemoria(List<Proceso> ProcesosMaxMemoria) {
        this.ProcesosMaxMemoria = ProcesosMaxMemoria;
    }

    public List<Proceso> getProcesosMaxTiempo() {
        return ProcesosMaxTiempo;
    }

    public void setProcesosMaxTiempo(List<Proceso> ProcesosMaxTiempo) {
        this.ProcesosMaxTiempo = ProcesosMaxTiempo;
    }

    public List<Proceso> getProcesosMaxSP() {
        return ProcesosMaxSP;
    }

    public void setProcesosMaxSP(List<Proceso> ProcesosMaxSP) {
        this.ProcesosMaxSP = ProcesosMaxSP;
    }

    public List<Proceso> getOtrosProcesos() {
        return OtrosProcesos;
    }

    public void setOtrosProcesos(List<Proceso> OtrosProcesos) {
        this.OtrosProcesos = OtrosProcesos;
    }

    public List<Proceso> getProcesosMaxUso() {
        return ProcesosMaxUso;
    }

    public void setProcesosMaxUso(List<Proceso> ProcesosMaxUso) {
        this.ProcesosMaxUso = ProcesosMaxUso;
    }

}
