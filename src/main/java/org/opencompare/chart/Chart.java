package org.opencompare.chart;
import org.opencompare.api.java.*;

/**
 * Created by Elina on 18/11/2016.
 */
public class Chart {

    private String nameChart;
    private String nameIcon;
    private Class<Value> featureType;

    public Chart(String name, String icon, Class<Value> type){
        this.nameChart = name;
        this.nameIcon = icon;
        this.featureType = type;
    }
    public String getNameChart(){
        return this.nameChart;
    }

    public String getNameIcon(){
        return this.nameIcon;
    }

    public Class<Value> getFeatureType(){
        return this.featureType;
    }

}
