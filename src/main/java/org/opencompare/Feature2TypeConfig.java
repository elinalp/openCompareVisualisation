package org.opencompare;

import org.opencompare.api.java.Value;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Pierre-Louis on 18/11/2016.
 */
public class Feature2TypeConfig {
    Hashtable<Value, Integer> collectionTypesFeature;

    /**
     * Constructeur de Feature2TypeConfig
     */
    public Feature2TypeConfig(){
        collectionTypesFeature = new Hashtable<Value, Integer>();
    }

    /**
     *
     */
    public void getRandomType(){

    }

    /**
     * Renvoie le type de données prédominant dans la feature
     *
     * @return ValueImpl : la Value prédominante de la feature
     */
    public Value getPredominantType(){

        Iterator<Value> it = collectionTypesFeature.keySet().iterator();
        int nbOccurenceMax;
        Value predominantValue = null;

        while(it.hasNext()){
            Value cle = it.next();
            nbOccurenceMax = collectionTypesFeature.get(cle);
            //System.out.print("nb occurence "+nbOccurenceMax);
            if(nbOccurenceMax < collectionTypesFeature.get(cle)){
                predominantValue = it.next();
                nbOccurenceMax = collectionTypesFeature.get(cle);
            }
        }

        return predominantValue;
    }


    public void setCollectionTypesFeature(Hashtable<Value, Integer> collect){
        collectionTypesFeature = collect;
    }

    public String getFirstElement(){
        Iterator<Value> it = collectionTypesFeature.keySet().iterator();
        while(it.hasNext()){
            return it.next().toString();
        }
        return null;
    }
}
