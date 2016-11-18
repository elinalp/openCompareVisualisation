package org.opencompare.chart;

import org.opencompare.api.java.Value;


/**
 * Created by Elina on 18/11/2016.
 */
public class BarChart extends Chart {

    private boolean regroupement;

    public BarChart(String name, String icon, Value type, boolean r){
        super(name, icon, type);
        this.regroupement = r;
    }
}
