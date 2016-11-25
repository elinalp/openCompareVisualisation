package org.opencompare;

import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.impl.ValueImpl;
import org.opencompare.api.java.value.BooleanValue;
import org.opencompare.api.java.value.IntegerValue;
import org.opencompare.api.java.value.StringValue;
import org.opencompare.chart.Chart;
import org.opencompare.chart.PieChart;
import org.opencompare.chart.PolarChart;
import org.opencompare.chart.BarChart;



import org.opencompare.model.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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

        // Récupère la liste des cellules de la feature
        List<Cell> listCells = feature.getCells();

        // Création d'une liste des modalités
        List<String> listModalite =  new ArrayList<String>();

        // Parcours de la liste des cellules
        Iterator iterator = listCells.iterator();
        while (iterator.hasNext()) {
            Cell c = (Cell) iterator.next();

            // Récupération du type de la cellule
            Value cellValue = c.getInterpretation();

            if(collectionTypes.containsKey(cellValue.getkValue())){
                // On incrémente le type de la feature
                collectionTypes.put(cellValue, collectionTypes.get(cellValue) + 1);
            } else {
                // On ajoute le type à la collection
                collectionTypes.put(cellValue, 1);
            }
        }

        return collectionTypes;
    }

    public List<Chart> getListCharts(){
        List<Chart> listChart = new ArrayList<Chart>();

        if(this.typeSelected instanceof IntegerValue){
            // Create chart
            // Histogramme

            // Diagramme en baton - on étudie la feature et le product
            // Aucun regroupement
            listChart.add(new BarChart("barChart", "fa fa-barchart", this.typeSelected, false));

        } else if (this.typeSelected instanceof StringValue || this.typeSelected instanceof BooleanValue){
            // Create chart
            if(getCountModalite() <= 5 ){
                // Pie Chart
                listChart.add(new PieChart("pieChart", "fa fa-piechart", this.typeSelected));
                // Diagramme polaire
                listChart.add(new PolarChart("polarChart", "fa fa-polarchart", this.typeSelected));

            } else {
                // Diagramme en baton
                // regroupement
                listChart.add(new BarChart("barChart", "fa fa-barchart", this.typeSelected, true));
            }
        }

        return listChart;
    }

    public int getCountModalite(){
        // Récupère la liste des cellules de la feature
        List<Cell> listCells = feature.getCells();

        // Création d'une liste des modalités
        List<String> listModalite =  new ArrayList<String>();

        // Parcours de la liste des cellules
        Iterator iterator = listCells.iterator();
        while (iterator.hasNext()) {
            Cell c = (Cell) iterator.next();
            // Récupération du contenu de la cellule
            String cellContent = c.getContent();

            // Si la modalité n'est pas présente dans la liste de modalité
            if (!listModalite.contains(cellContent)) {
                // On ajoute la modalité
                listModalite.add(cellContent);
            }
        }
        return listModalite.size();
    }
}
