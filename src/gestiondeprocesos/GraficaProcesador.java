package gestiondeprocesos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import static java.lang.Double.NaN;
import javax.swing.JPanel;
import jdk.nashorn.internal.objects.Global;
import static jdk.nashorn.internal.objects.Global.Infinity;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlotState;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
/**
 *
 * @author Arnold_Irias
 */
public class GraficaProcesador extends ApplicationFrame {

    private InformacionHSSistema miSistema;
    private JPanel panel;
    private static final long serialVersionUID = 1L;

    public GraficaProcesador(String title) {
        super(title);
    }

    static class CustomRingPlot extends RingPlot {

        private Font centerTextFont;

        private Color centerTextColor;

        public CustomRingPlot(PieDataset dataset) {
            super(dataset);
            this.centerTextFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
            this.centerTextColor = Color.LIGHT_GRAY;
        }

        @Override
        protected void drawItem(Graphics2D g2, int section,
                Rectangle2D dataArea, PiePlotState state, int currentPass) {
            super.drawItem(g2, section, dataArea, state, currentPass);
            if (currentPass == 1 && section == 0) {
                Number n = this.getDataset().getValue(section);
                g2.setFont(this.centerTextFont);
                g2.setPaint(this.centerTextColor);
                TextUtilities.drawAlignedString(n.toString(), g2,
                        (float) dataArea.getCenterX(),
                        (float) dataArea.getCenterY(),
                        TextAnchor.CENTER);
            }
        }

    }

    public void GraficaProcesadors(InformacionHSSistema miSistema) {
        this.miSistema = miSistema;
    }

    public void nuevaGrafica() {
        setContentPane(createDemoPanel());
        JPanel panel = createDemoPanel();
        panel.setPreferredSize(new java.awt.Dimension(500, 300));
        setContentPane(panel);
        this.panel = panel;
    }

    private PieDataset createDataset() {
        miSistema.actualizar();
        Validaciones validacion = new Validaciones();
        //double usoProcesador = validacion.formatearDecimales(info.getUsoCPUxNucleo(), 2);
        double usoProcesador = miSistema.getUsoCPUxNucleo();

        usoProcesador = miSistema.getUsoCPUxNucleo();

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("En uso", usoProcesador);
        dataset.setValue("Libre", (100 - usoProcesador));
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset) {
        CustomRingPlot plot = new CustomRingPlot(dataset);
        JFreeChart chart = new JFreeChart("% Actividad Procesador",
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0),
                new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.CENTER);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        plot.setLabelGenerator(null);
        plot.setSectionPaint("A", Color.ORANGE);
        plot.setSectionPaint("B", new Color(100, 100, 100));
        plot.setSectionDepth(0.05);
        plot.setSectionOutlinesVisible(false);
        plot.setShadowPaint(null);

        return chart;

    }

    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600, 300));
        return panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    /**
     * @param panel the panel to set
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
    /*   public static void main(String[] args) {
     GraficaProcesador demo = new GraficaProcesador("Procesador en uso");
     demo.pack();
     RefineryUtilities.centerFrameOnScreen(demo);
     demo.setVisible(true);
     }*/

}
