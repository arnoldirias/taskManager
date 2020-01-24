/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondeprocesos;

import com.sun.management.OperatingSystemMXBean;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.lang.management.ManagementFactory;
import javax.swing.JPanel;

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
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

/**
 *
 * @edit by Arnold_Irias
 */
/**
 * A simple demonstration application showing how to create a ring chart using
 * data from a {@link DefaultPieDataset}.
 */
public class RingChartDemo2 extends ApplicationFrame {

    InformacionDelSistema xy = new InformacionDelSistema();
    private static final long serialVersionUID = 1L;
    public JPanel miPanel;

    public class CustomRingPlot extends RingPlot {

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

    /**
     * Default constructor.
     *
     * @param title the frame title.
     */
    public RingChartDemo2(String title) throws InterruptedException {
        super(title);
        setContentPane(createDemoPanel());
    }

    /**
     * Creates a sample dataset.
     *
     * @return A sample dataset.
     */
    public PieDataset createDataset() throws InterruptedException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        InformacionDelSistema INFO = new InformacionDelSistema();
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        double arrUso[] = new double[2];
        double uso = 0;
        for (int i = 0; i < arrUso.length; i++) {
            double usoCPU = INFO.UsoDelCPU();
            arrUso[i] = (usoCPU * 100);
        }
        for (int i = 0; i < arrUso.length; i++) {

            uso = uso + arrUso[i];
            uso = uso / arrUso.length;
        }

        double usoCPU = 0;
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long t = System.currentTimeMillis();
        while (System.currentTimeMillis() < t + 1000) {

            usoCPU = operatingSystemMXBean.getSystemCpuLoad();
        }

        usoCPU = usoCPU * 100;
        usoCPU = Redondear(usoCPU);
        dataset.setValue("USO CPU", usoCPU);
        dataset.setValue("CPU LIBRE", (100 - uso));
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset the dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(PieDataset dataset) {
        CustomRingPlot plot = new CustomRingPlot(dataset);
        JFreeChart chart = new JFreeChart("Uso del procesador en %",
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0),
                new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));

        // customise the title position and font
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

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public double Redondear(double numero) {
        return Math.rint(numero * 100) / 100;
    }

    public JPanel createDemoPanel() throws InterruptedException {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600, 300));
        miPanel = panel;
        return panel;
    }
}
