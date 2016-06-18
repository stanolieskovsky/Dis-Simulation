/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;

/**
 *
 * @author liesko3
 */
public class Graf extends ApplicationFrame {

    XYSeriesCollection mnozina = new XYSeriesCollection();
    ChartPanel p;
    private XYPlot plot;
    private NumberAxis axis;

    public Graf(String applicationTitle, String chartTitle, String chart,String chart2) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createXYLineChart(chartTitle, chart, chart2,
                mnozina, PlotOrientation.VERTICAL, true, true, false
        );
        plot = (XYPlot) lineChart.getPlot();
        axis = (NumberAxis) plot.getRangeAxis();
        lineChart.getLegend().setVisible(false);
        lineChart.getLegend().setPosition(RectangleEdge.RIGHT);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        p = chartPanel;
        setContentPane(chartPanel);
    }

    public ChartPanel getChartPanel() {
        return p;
    }

    public void pridajSeries(String nazov) {
        mnozina.addSeries(new XYSeries(nazov));

    }

    public void pridajBodDoSerie(int series, Double x, Double y) {
        mnozina.getSeries(series).add((x/(30*24*3600)), y);
        try {
            axis.setRange(mnozina.getSeries(0).getMinY(), mnozina.getSeries(0).getMaxY());
        } catch (Exception e) {
        }

    }
}
