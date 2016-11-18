package org.opencompare;
import org.opencompare.api.java.*;
import org.opencompare.api.java.value.*;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;

import java.util.Collection;

/**
 * Created by Elina on 18/11/2016.
 */
public class FeatureViz {

    private Feature feature;
    private Value typeSelected;

    public FeatureViz(Feature f){
        this.feature = f;
    }

    public Value getTypeSelected(){
        return this.typeSelected;
    }

    public void setTypeSelected(Value v){
        this.typeSelected = v;
    }

    public Hashtable<Value, Integer> getTypesFeature(){

        Hashtable<Value, Integer> collectionTypes = new Hashtable<Value, Integer>();


        return collectionTypes;
    }

    public List<Chart> getListCharts(){
        List<Chart> listChart = new ArrayList<Chart>();

        if(this.typeSelected instanceof IntegerValue){
            // Create chart
        } else if (this.typeSelected instanceof StringValue){
            // Create chart
        } else if (this.typeSelected instanceof BooleanValue){
            // Create chart
        }
        return listChart;
    }

    public int getCountModalite(){
        int nb = 0;
        return nb;
    }
}
