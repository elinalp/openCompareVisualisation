package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Pierre-Louis on 25/11/2016.
 */
public class Feature2TypeConfigTest {
    private List<Feature> listFeature;
    private Hashtable<Class<Value>, Integer> collection;

    public void initialisation() throws IOException{
        // Load a PCM
        File pcmFile = new File("pcms/pcmPredominantType2.pcm");

        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        // Get the first PCM
        PCM pcm = loader.load(pcmFile).get(0).getPcm();

        // Browse the features of the PCM
        this.listFeature = pcm.getConcreteFeatures();
        // Get the seconde feature
        Feature f = listFeature.get(0);

        FeatureViz featureViz = new FeatureViz(f);

        this.collection = featureViz.getTypesFeature();

        System.out.println("Nombre de type de données dans la feature : "+collection.size());
    }

    @Test
    public void testGetPredominantType() throws IOException{
        initialisation();
        Feature2TypeConfig f2tc = new Feature2TypeConfig();
        f2tc.setCollectionTypesFeature(collection);
        Class<Value> predominantType = f2tc.getPredominantType();
        System.out.println(predominantType);
    }

    @Test
    public void testGetRandomType() throws IOException{
        initialisation();
        Feature2TypeConfig f2tc = new Feature2TypeConfig();
        f2tc.setCollectionTypesFeature(collection);
        Class<Value> randomType = f2tc.getRandomType();
        System.out.println(randomType);
    }
}
