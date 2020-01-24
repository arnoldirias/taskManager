package gestiondeprocesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

/**
 *
 * @author Arnold_Irias
 */
public class InformacionDelSistema {

    public BufferedReader extraerProcesos() throws IOException {
        String comando = System.getenv("windir") + "\\System32\\" + "tasklist.exe";
        Process comandoEjecutado = Runtime.getRuntime().exec(comando);
        BufferedReader br = new BufferedReader(new InputStreamReader(comandoEjecutado.getInputStream()));
        String tmp;
        return br;
    }

    public double UsoDelCPU() { //Si lo que ocupas es el dato 
        //Hace que la funci�n devuelva un double y el return que vas a hacer
        //En este caso bean.getSystemCpuLoad te devuelve la carga del procesador
        //Pero hay que usarla varias veces seguidas para que se note el cambio.
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        /*final Runnable task = new Runnable() {
         @Override
         public void run() {
         System.out.println(bean.getSystemCpuLoad());
				
         }
         };*/
        //ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        //int initialDelay = 2;
        //int periodicDelay = 2;	         
        //scheduler.scheduleAtFixedRate(task, initialDelay, periodicDelay, TimeUnit.SECONDS);

        return bean.getSystemCpuLoad();
    }
}
