/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprocesos;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author David Hurtado
 * @edit by Arnold_Irias
 */
public class Matando extends javax.swing.JFrame {

    private Timer timer;
    int cont;
    private String newline;
    public final static int ONE_SECOND = 1200;

    public Matando() {
        initComponents();
        this.setLocationRelativeTo(null);
        cont = -1;
        initComponents();
        progressBarRound1.setValue(0);
        progressBarRound1.setStringPainted(true);
        timer = new Timer(ONE_SECOND, new TimerListener());

        timer.start();
    }

    class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            cont++;
           // jLabel1.setText("nume es :"+cont);

            progressBarRound1.setValue(cont);
            if (cont == 20) {
                /*           try {
                 FileInputStream fis;
                 Player player;
                 fis = new FileInputStream("rambo.mp3");
                 BufferedInputStream bis = new BufferedInputStream(fis);

                 player = new Player(bis);
                 player.play();         
                 } catch (JavaLayerException e) {
                 e.printStackTrace();
                 } catch (FileNotFoundException e) {
                 e.printStackTrace();
                 }*/
                timer.stop();
                esconder();

                try {

                } catch (Exception ex) {
                    Logger.getLogger(Matando.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void esconder() {
        this.setVisible(false);
    }

    public void activar() {
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        progressBarRound1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/RAMBO.gif"))); // NOI18N

        progressBarRound1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Matando Proceso...", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Comic Sans MS", 1, 14))); // NOI18N
        progressBarRound1.setName("Matando Proceso..."); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(progressBarRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBarRound1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Matando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Matando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Matando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Matando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Matando().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar progressBarRound1;
    // End of variables declaration//GEN-END:variables
}
