package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by gbecan on 02/02/15.
 */
public class FeatureVizTest {

    private List<Feature> listFeature;

    @Test
    public void testGetTypesFeature() throws IOException {

        // Load a PCM
        File pcmFile = new File("pcms/Comparison_of_English_dictionaries_0.pcm");

        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        // Get the first PCM
        PCM pcm = loader.load(pcmFile).get(0).getPcm();

        // Browse the features of the PCM
        this.listFeature = pcm.getConcreteFeatures();

        // Get the seconde feature
        Feature f = listFeature.get(7);

        FeatureViz featureViz = new FeatureViz(f);
        System.out.println(featureViz.getCountModalite());

        //Hashtable<Class<Value>, Integer> list = featureViz.getTypesFeature();

        //System.out.print(list.toString());
    }

    @Test
    public void testGetCountModalite() throws IOException {

    }

    @Test
    public void testGetListCharts() throws IOException {

    }

}