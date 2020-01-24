/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprocesos;

import javax.swing.JOptionPane;

/**
 *
 * @author Arnold_Irias
 */
public class VentanaConfig extends javax.swing.JFrame {
Configuracion conf = new Configuracion();
Validaciones validacion = new Validaciones();
Comparador miComparador;
    /**
     * Creates new form VentanaConfig
     */
    public VentanaConfig(Comparador c) {
        initComponents();
        miComparador=c;
        
        String datos[] = conf.leerArchivo();
        
        if(datos==null){
        txtCPU.setText("0");
        txtMemoria.setText("0");
        txtSP.setText("0");
        txtTiempo.setText("0");
        jlblMemoria.setText(validacion.ObtenerRAMenMB());
        JOptionPane.showMessageDialog(null, "Archivo config.txt corrupto, ingrese nuevos valores");
        }else {
        txtCPU.setText(datos[0]);
        txtMemoria.setText(datos[1]);
        txtSP.setText(datos[2]);
        txtTiempo.setText(datos[3]);
        jlblMemoria.setText(validacion.ObtenerRAMenMB());
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

        btngMemoria = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCPU = new javax.swing.JTextField();
        txtMemoria = new javax.swing.JTextField();
        txtSP = new javax.swing.JTextField();
        txtTiempo = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        rbtnKB = new javax.swing.JRadioButton();
        rbtnMB = new javax.swing.JRadioButton();
        rbtnGB = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jlblMemoria = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Configuración");

        jLabel1.setText("% de uso del CPU:");

        jLabel2.setText("Uso de memoria:");

        jLabel3.setText("SP:");

        jLabel4.setText("Tiempo:");

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btngMemoria.add(rbtnKB);
        rbtnKB.setText("KB");

        btngMemoria.add(rbtnMB);
        rbtnMB.setSelected(true);
        rbtnMB.setText("MB");

        btngMemoria.add(rbtnGB);
        rbtnGB.setText("GB");

        jLabel5.setText("Uso de memoria en:");

        jLabel6.setText("Memoria máxima (MB):");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtSP, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(txtMemoria, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCPU, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTiempo)
                    .addComponent(jlblMemoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnKB)
                    .addComponent(rbtnMB)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnGB)
                    .addComponent(btnModificar))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnKB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnMB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnGB))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jlblMemoria)
                    .addComponent(btnModificar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        String memoria;
        if(rbtnKB.isSelected()){
            double dato = Double.parseDouble(txtMemoria.getText())/1024;
            double datoRedondeado = validacion.formatearDecimales(dato, 2);
            memoria = String.valueOf(datoRedondeado);
         
         txtMemoria.setText(memoria);
        
        }
        else if(rbtnGB.isSelected()){
            double dato = Double.parseDouble(txtMemoria.getText())*1024;
            double datoRedondeado = validacion.formatearDecimales(dato, 2);
            memoria = String.valueOf(datoRedondeado);
            txtMemoria.setText(memoria);
        } else {
            memoria = txtMemoria.getText();
            txtMemoria.setText(memoria);
        }
        String valores[] = {txtCPU.getText(), memoria, txtSP.getText(), txtTiempo.getText()};
     if(validacion.ValidacionGeneral(valores)==false){
         JOptionPane.showMessageDialog(null, "Datos incorrectos");
     } else {
     conf.crearArchivo(txtCPU.getText(), memoria, txtSP.getText(), txtTiempo.getText()); 
      rbtnMB.doClick();
     JOptionPane.showMessageDialog(null, "Datos modificados", "Modificacion terminada", HEIGHT);
     miComparador.setMaxUsoCPU(Double.parseDouble(txtCPU.getText()));
     miComparador.setMaxUsoMemoria(Double.parseDouble(memoria));
     miComparador.setMaxSP(Integer.valueOf(txtSP.getText()));
     miComparador.setMaxTiempo(Double.parseDouble(txtTiempo.getText()));
     //miComparador = new Comparador(Double.parseDouble(txtCPU.getText()), Double.parseDouble(memoria), Double.parseDouble(txtTiempo.getText()), Integer.valueOf(txtSP.getText())); 
     }
     
    }//GEN-LAST:event_btnModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificar;
    private javax.swing.ButtonGroup btngMemoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jlblMemoria;
    private javax.swing.JRadioButton rbtnGB;
    private javax.swing.JRadioButton rbtnKB;
    private javax.swing.JRadioButton rbtnMB;
    private javax.swing.JTextField txtCPU;
    private javax.swing.JTextField txtMemoria;
    private javax.swing.JTextField txtSP;
    private javax.swing.JTextField txtTiempo;
    // End of variables declaration//GEN-END:variables
}