package gestiondeprocesos;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Arnold_Irias
 */
public class GestionDeProcesos {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                AdministradordeTareas mipanel = new AdministradordeTareas();
                mipanel.setVisible(true);
                mipanel.setTitle("Gestión de Procesos");
                mipanel.setLocationRelativeTo(null);
                mipanel.setResizable(false);
                //frame.add(mipanel);
                //frame.setSize(850, 620);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.setExtendedState(frame.MAXIMIZED_BOTH);
            }
        });
    }
}
