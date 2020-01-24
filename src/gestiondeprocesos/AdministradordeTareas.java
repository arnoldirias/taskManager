package gestiondeprocesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Double.NaN;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.Global.Infinity;
import oshi.software.os.OSProcess;

/**
 *
 * @author Arnold_Irias
 */
public class AdministradordeTareas extends javax.swing.JFrame {

    InformacionHSSistema miSistema = new InformacionHSSistema();
    ContenedorProcesos miContenedor = new ContenedorProcesos();
    Comparador miComparador;
    Configuracion conf = new Configuracion();

    DefaultTableModel mt;
    DefaultTableModel t;
    String[] enc2 = {"PID", "Nombre", "Uso de CPU", "Uso de Memoria"};

    String[][][][][] datos = null;

    public AdministradordeTareas() {
        initComponents();
        this.setLocationRelativeTo(null);
        mt = new DefaultTableModel(datos, enc2);
        this.jTable2.setModel(mt);
        t = new DefaultTableModel(datos, enc2);
        this.jTable3.setModel(t);

        System.out.println("Obteniendo datos de Configuración");
        
        String[] datos = conf.leerArchivo();
        if (datos == null) {
            panel = new VentanaConfig(miComparador);
            panel.setVisible(true);
        } else {
            miComparador = new Comparador(Double.parseDouble(datos[0]), Double.parseDouble(datos[1]), Double.parseDouble(datos[3]), Integer.valueOf(datos[2]));
        }
        for (int i = 0; i < datos.length; i++) {
            System.out.println(datos[i]);
        }

        System.out.println("Obteniento información del Equipo");
        miSistema.generarInf();

        jNumNucleos.setText(String.valueOf(miSistema.getProcesador_nucleos()));
        jNumProcesadoresL.setText(String.valueOf(miSistema.getProcesadores_logicos()));

        System.out.println("Obteniento información de los Procesos");
        Runnable task;
        task = new Runnable() {
            @Override
            public void run() {
                
                miContenedor = new ContenedorProcesos();
                double memoriaPrincipalDis=0;
                double memoriaPrincipalTotal=0;
                
                miSistema.actualizar();
                long memoria=miSistema.getMemoria_tamtotal();
                
                List<OSProcess> procs;
                double w = 0;
                procs = miSistema.obtenerProcesos();
                for (OSProcess p : procs) {
                    w = 100d * p.getResidentSetSize() / miSistema.getMemoria_tamtotal();

                    if ((w == Infinity) || (w == NaN)) {
                        miContenedor.Agregar2(
                            p.getName(),
                            p.getProcessID(),
                            100d / miSistema.getProcesador_nucleos() * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                            0.0,
                            p.getUpTime()
                        );

                    } else {
                        miContenedor.Agregar2(
                            p.getName(),
                            p.getProcessID(),
                            100d / miSistema.getProcesador_nucleos() * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                            w,
                            p.getUpTime());
                        //System.out.print("SAVED: "+w);System.out.format("---FORMAT: %4.1f \n",100d * p.getResidentSetSize() / miSistema.getMemoria_tamtotal());
                    }

                }

                LimpiarTablaT();
                for (Proceso q : miContenedor.getProcesos()) {
                    q.generarResultados();
                    Object x = new Object[]{q.getPID(), q.getNombre(), String.format("%.1f %%", q.getUsoCPU()), String.format("%.1f %% (%4.1f MB)", q.getUsoMemoria(), memoria * q.getUsoMemoria() / (100 * 1024 * 1024))};
                    t.addRow((Object[]) x);
                }
                t.setColumnIdentifiers(enc2);
                jTable3.setModel(t);

                miSistema.actualizar();
                jNumProcesos.setText(String.valueOf(miSistema.getNumProcesos()));
                jNumHilos.setText(String.valueOf(miSistema.getNumHilos()));
                jMemD.setText(miSistema.getMemoria_disponible());
                jMemT.setText(miSistema.getMemoria_total());
                
                
                String partes[] = miSistema.getMemoria_disponible().split(" ");
                String partes2[] = miSistema.getMemoria_total().split(" ");
                if(partes[1].equals("MiB")){
                    memoriaPrincipalDis=Double.parseDouble(partes[0])/1024;
                }else{
                    memoriaPrincipalDis=Double.parseDouble(partes[0]);
                }
                
                if(partes2[0].equals("MiB")){
                    memoriaPrincipalTotal=Double.parseDouble(partes2[0])/1024;
                }else{
                    memoriaPrincipalTotal=Double.parseDouble(partes2[0]);
                }
                
    
                jPromMemoria.setText(String.format("%.0f%%", 100 * (1 - memoriaPrincipalDis/memoriaPrincipalTotal)));
                jPromCPU.setText(String.format("%.0f%%", miSistema.getUsoCPUxNucleo()));
                
                AnalisisProcesos resultados = new AnalisisProcesos(miContenedor, miComparador, memoria);
                resultados.generarResultados();
                LimpiarTabla();
                for (Proceso q : resultados.getProcesosMaxUso()) {
                    //q.generarResultados();
                    Object x = new Object[]{q.getPID(), q.getNombre(), String.format("%.1f %%", q.getUsoCPU()), String.format("%.1f %% (%4.1f MB)", q.getUsoMemoria(), memoria * q.getUsoMemoria() / (100 * 1024 * 1024))};
                    mt.addRow((Object[]) x);
                }
                mt.setColumnIdentifiers(enc2);
                jTable2.setModel(mt);
            }
        };
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        int initialDelay = 0;
        int periodicDelay = 1;

        scheduler.scheduleAtFixedRate(task, initialDelay, periodicDelay, TimeUnit.SECONDS);
    }

    public static List listRunningProcesses() {

        List<String> processes = new ArrayList<String>();
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {

                    processes.add(line.substring(0, line.indexOf(" ")));
                }

            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return processes;
    }

    void LimpiarTabla() {
        if (null != mt) {
            int a = mt.getRowCount() - 1;  //Índices van de 0 a n-1
            for (int i = a; i >= 0; i--) {
                mt.removeRow(i);
            }
        }
    }

    void LimpiarTablaT() {
        if (null != t) {
            int a = t.getRowCount() - 1;  //Índices van de 0 a n-1
            for (int i = a; i >= 0; i--) {
                t.removeRow(i);
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelNice1 = new org.edisoncor.gui.panel.PanelNice();
        panelRound1 = new org.edisoncor.gui.panel.PanelRound();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        buttonTask1 = new org.edisoncor.gui.button.ButtonTask();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buttonTask3 = new org.edisoncor.gui.button.ButtonTask();
        buttonTask4 = new org.edisoncor.gui.button.ButtonTask();
        buttonTask6 = new org.edisoncor.gui.button.ButtonTask();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jNumProcesos = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jNumHilos = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jNumNucleos = new javax.swing.JLabel();
        jNumProcesadoresL = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jMemD = new javax.swing.JLabel();
        jMemT = new javax.swing.JLabel();
        jPromMemoria = new javax.swing.JLabel();
        jPromCPU = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelNice1.setBackground(new java.awt.Color(0, 0, 51));

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setForeground(new java.awt.Color(255, 255, 255));
        panelRound1.setToolTipText("");
        panelRound1.setAnchoDeBorde(0.0F);
        panelRound1.setColorDeBorde(new java.awt.Color(255, 255, 255));
        panelRound1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panelRound1.setColorSecundario(new java.awt.Color(170, 176, 186));
        panelRound1.setOpaque(false);
        panelRound1.setPreferredSize(new java.awt.Dimension(880, 680));
        panelRound1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound1MouseClicked(evt);
            }
        });

        jTable2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jTable2.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre del Proceso", "PID", "usoCPU", "usoMemoria"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("PROCESOS CON ALTO NIVEL DE CONSUMO");

        buttonTask1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/eliminar (1).png"))); // NOI18N
        buttonTask1.setText(" ");
        buttonTask1.setCategorySmallFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        buttonTask1.setDescription("Matar Procesos");
        buttonTask1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Desarrollado por Arnold Irias");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("            PROCESOS EN EJECUCION");

        buttonTask3.setBackground(new java.awt.Color(0, 0, 0));
        buttonTask3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/coloeanalisis.png"))); // NOI18N
        buttonTask3.setText(" ");
        buttonTask3.setCategorySmallFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        buttonTask3.setDescription("Analisis");
        buttonTask3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask3ActionPerformed(evt);
            }
        });

        buttonTask4.setBackground(new java.awt.Color(0, 0, 0));
        buttonTask4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/colorreporte.png"))); // NOI18N
        buttonTask4.setText(" ");
        buttonTask4.setCategorySmallFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        buttonTask4.setColorDeSegundoBorde(new java.awt.Color(0, 0, 0));
        buttonTask4.setDescription("Información del Sistema");
        buttonTask4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask4ActionPerformed(evt);
            }
        });

        buttonTask6.setBackground(new java.awt.Color(0, 0, 0));
        buttonTask6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/colorconfig.png"))); // NOI18N
        buttonTask6.setText(" ");
        buttonTask6.setCategorySmallFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        buttonTask6.setDescription("Configuracion");
        buttonTask6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTask6ActionPerformed(evt);
            }
        });

        jTable3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jTable3.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre del Proceso", "PID", "usoCPU", "usoMemoria"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Número Total de Procesos:");

        jNumProcesos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jNumProcesos.setText("0");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Número Total de Hilos:");

        jNumHilos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jNumHilos.setText("0");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Número de Nucleos:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Número de Procesadores Lógicos:");

        jNumNucleos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jNumNucleos.setText("0");

        jNumProcesadoresL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jNumProcesadoresL.setText("0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Memoria Principal Disponible:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Memoria Principal Total:");

        jMemD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jMemD.setText("0");

        jMemT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jMemT.setText("0");

        jPromMemoria.setBackground(new java.awt.Color(255, 255, 255));
        jPromMemoria.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jPromMemoria.setText("0");

        jPromCPU.setBackground(new java.awt.Color(255, 255, 255));
        jPromCPU.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jPromCPU.setText("0");

        jLabel10.setText("Uso CPU:");

        jLabel11.setText("Uso Memoria:");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(94, 94, 94))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jNumProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jNumHilos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jNumProcesadoresL, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jNumNucleos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jMemT, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jMemD, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(46, 46, 46)
                                        .addComponent(jLabel10))
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jPromMemoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPromCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addComponent(buttonTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonTask6, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonTask3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(buttonTask4, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jNumProcesos))
                        .addGap(13, 13, 13)
                        .addComponent(jLabel6))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jMemD)
                            .addComponent(jLabel8)))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jNumNucleos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jNumProcesadoresL)
                            .addComponent(jNumHilos)
                            .addComponent(jLabel9)
                            .addComponent(jMemT)))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPromMemoria)
                            .addComponent(jPromCPU))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonTask6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTask3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTask4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout panelNice1Layout = new javax.swing.GroupLayout(panelNice1);
        panelNice1.setLayout(panelNice1Layout);
        panelNice1Layout.setHorizontalGroup(
            panelNice1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNice1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelNice1Layout.setVerticalGroup(
            panelNice1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(panelNice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelRound1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseClicked

    }//GEN-LAST:event_panelRound1MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void buttonTask6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask6ActionPerformed
        panel = new VentanaConfig(miComparador);
        panel.setVisible(true);
        panel.setLocationRelativeTo(null);
        panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_buttonTask6ActionPerformed

    private void buttonTask4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask4ActionPerformed
        frmGraficas grafica = new frmGraficas();
        grafica.setVisible(true);

    }//GEN-LAST:event_buttonTask4ActionPerformed

    private void buttonTask3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask3ActionPerformed
        // TODO add your handling code here:
        long memoria = miSistema.getMemoria_tamtotal();
        AnalisisProcesos resultados = new AnalisisProcesos(miContenedor, miComparador, memoria);
        frmProcesos analisis=new frmProcesos(resultados, memoria);
        analisis.setLocationRelativeTo(null);
        analisis.setVisible(true);
    }//GEN-LAST:event_buttonTask3ActionPerformed

    private void buttonTask1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTask1ActionPerformed
        try {
            Validaciones validacion = new Validaciones();
            String cm = JOptionPane.showInputDialog("PID del Proceso:");
            if(validacion.ProcesoEnEjecucion(cm)==true){
                validacion.MatarProceso(cm);
            } else {
                JOptionPane.showMessageDialog(null, "No se está ejecutando el proceso con PID: "+cm);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdministradordeTareas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonTask1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

    }//GEN-LAST:event_jTable2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonTask buttonTask1;
    private org.edisoncor.gui.button.ButtonTask buttonTask3;
    private org.edisoncor.gui.button.ButtonTask buttonTask4;
    private org.edisoncor.gui.button.ButtonTask buttonTask6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jMemD;
    private javax.swing.JLabel jMemT;
    private javax.swing.JLabel jNumHilos;
    private javax.swing.JLabel jNumNucleos;
    private javax.swing.JLabel jNumProcesadoresL;
    private javax.swing.JLabel jNumProcesos;
    private javax.swing.JLabel jPromCPU;
    private javax.swing.JLabel jPromMemoria;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private org.edisoncor.gui.panel.PanelNice panelNice1;
    private org.edisoncor.gui.panel.PanelRound panelRound1;
    // End of variables declaration//GEN-END:variables
    VentanaConfig panel;
}
