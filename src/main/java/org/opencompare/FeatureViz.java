package org.opencompare;

import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.value.BooleanValue;
import org.opencompare.api.java.value.IntegerValue;
import org.opencompare.api.java.value.StringValue;
import org.opencompare.chart.Chart;
import org.opencompare.chart.PieChart;
import org.opencompare.chart.PolarChart;
import org.opencompare.chart.BarChart;

import java.awt.*;
import java.text.Normalizer;

import org.opencompare.api.java.impl.ValueImpl;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Elina on 18/11/2016.
 */
public class FeatureViz {

    /**
     * La feature associée a la classe FeatureViz
     */
    private Feature feature;
    /**
     * La valeur de la feature selectionnée parmi toutes celles présentes.
     */
    private Value typeSelected;

    /**
     * Constructeur
     * @param f Feature
     */
    public FeatureViz(Feature f){
        this.feature = f;
    }

    /**
     *
     * @return {@link #typeSelected}
     */
    public Value getTypeSelected(){
        return this.typeSelected;
    }

    /**
     * Affecte la value choisie à l'attribut typeSelected
     * @param v
     */
    public void setTypeSelected(Value v){
        this.typeSelected = v;
    }

    /**
     * Retourne une collection des types de values différentes dans la feature.
     * Les values en clé et leur nombre d'apparition en valeur.
     * @return Collection
     */
    public Hashtable<Class<Value>, Integer> getTypesFeature(){

        Hashtable<Class<Value>, Integer> collectionTypes = new Hashtable<Class<Value>, Integer>();

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
            Class classe = cellValue.getClass();

            if(collectionTypes.containsKey(classe)){
                // On incrémente le type de la feature
                collectionTypes.put(classe, collectionTypes.get(classe) + 1);

            } else {
                // On ajoute le type à la collection
                collectionTypes.put(classe, 1);
            }
        }
        return collectionTypes;
    }

    /**
     * Retourne la liste de char associée à la feature.
     * @return List
     */
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

    /**
     * Retourne le nombre de modalité pour la feature
     * @return int
     */
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
            // Modification de la chaine de caractere
            // mettre en miniscule
            cellContent = cellContent.toLowerCase();
            // Enleve les espaces en debut et fin de la chaine
            cellContent = cellContent.trim();
            // Remplace les espaces par des underscores
            cellContent = cellContent.replaceAll(" ", "_");
            // Suppression des accents
            cellContent = Normalizer.normalize(cellContent, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
            System.out.print(cellContent);

            // Si la modalité n'est pas présente dans la liste de modalité
            if (!listModalite.contains(cellContent)) {
                // On ajoute la modalité
                listModalite.add(cellContent);
            }
        }
        return listModalite.size();
    }
}
