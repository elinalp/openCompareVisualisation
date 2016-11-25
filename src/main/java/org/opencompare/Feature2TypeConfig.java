package org.opencompare;

import org.opencompare.api.java.Value;

import java.util.*;

/**
 * Created by Pierre-Louis on 18/11/2016.
 */
public class Feature2TypeConfig {
    Hashtable<Class<Value>, Integer> collectionTypesFeature;

    /**
     * Constructeur de Feature2TypeConfig
     */
    public Feature2TypeConfig(Hashtable<Class<Value>, Integer> collectionTypesFeature){
        this.collectionTypesFeature = collectionTypesFeature;
    }

    /**
     * Renvoie un type de données aléatoire parmi ceux figurant dans la feature
     *
     * @return une instance Class<Value>,qui correspond à une classe Value figurant de la feature
     */
    public Class<Value> getRandomType(){
        List<Class<Value>> randomList = new ArrayList<Class<Value>>();

        Random rand = new Random();
        int randomIndexTab = rand.nextInt(collectionTypesFeature.size());

        for(Class<Value> c : collectionTypesFeature.keySet()){
            randomList.add(c);
        }
        return randomList.get(randomIndexTab);
    }

    /**
     * Renvoie le type de données prédominant dans la feature
     *
     * @return une instance Class<Value>,qui correspond la classe Value prédominante de la feature
     */
    public Class<Value> getPredominantType(){
        Iterator<Class<Value>> it = collectionTypesFeature.keySet().iterator();
        int nbOccurenceMax = 0;
        int nbOccurenceCurrent = 0;
        Class<Value> predominantValue = null;

        while(it.hasNext()){
            Class<Value> cle = it.next();
            nbOccurenceCurrent = collectionTypesFeature.get(cle);
            if(nbOccurenceMax < nbOccurenceCurrent){
                nbOccurenceMax = nbOccurenceCurrent;
                predominantValue = cle;
            }
        }
        return predominantValue;
    }


    public void setCollectionTypesFeature(Hashtable<Class<Value>, Integer> collect){
        collectionTypesFeature = collect;
    }
}
