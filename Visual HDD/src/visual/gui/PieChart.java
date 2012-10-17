package visual.gui;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class PieChart extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private PieDataset dataset;
	private String chartTitle;

	public PieChart(String title, PieDataset data) {
        dataset = data;
        chartTitle = title;
    }
	    
    public ChartPanel createChart(){
        JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, dataset, false, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(180);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setLabelGap(0.02);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        return new ChartPanel(chart);
    }
}