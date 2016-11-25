package org.opencompare;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by gbecan on 02/02/15.
 */
public class FeatureVizTest {

    /**
     * Liste de PCM
     */
    private static HashMap<String, PCM> listPcm = new HashMap<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Load a PCMS
        File pcmFile1 = new File("pcms/pcms_test_junit/Comparison_of_English_dictionaries_0.pcm");
        File pcmFile2 = new File("pcms/pcms_test_junit/Comparison_of_mobile_Internet_Relay_Chat_clients_0.pcm");

        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        // Get PCMS
        PCM pcm1 = loader.load(pcmFile1).get(0).getPcm();
        PCM pcm2 = loader.load(pcmFile2).get(0).getPcm();
        listPcm.put("pcm1",pcm1);
        listPcm.put("pcm2",pcm2);
    }


    @Test
    public void testGetTypesFeature() throws IOException {

        // Browse the features of the PCM
        List<Feature> listFeature = listPcm.get("pcm1").getConcreteFeatures();

        // Get the seconde feature
        Feature f = listFeature.get(8);
        FeatureViz featureViz = new FeatureViz(f);

        Hashtable<Class<Value>, Integer> listeFeature = featureViz.getTypesFeature();


    }

    @Test
    public void testGetCountModalite() throws IOException {

        Feature f;
        int nbModalite;
        FeatureViz featureViz;
        List<Feature> listFeature;

        // ----  TEST SUR LA PCM1 - English_dictionaries_0 - Test Basic avec peu de modalités

        // Browse the features of the PCM
        listFeature = listPcm.get("pcm1").getConcreteFeatures();
        // Get the last feature - Pronunciation guide (String)
        f = listFeature.get(8);
        featureViz = new FeatureViz(f);
        nbModalite = featureViz.getCountModalite();

        assertEquals("Pcm English_dictionaries_0 - Nombre de modalité de la feature " + f.getName(), 2, nbModalite);

        // Get the feature - Year (Integer)
        f = listFeature.get(4);
        featureViz = new FeatureViz(f);
        nbModalite = featureViz.getCountModalite();

        assertEquals("Pcm English_dictionaries_0 - Nombre de modalité de la feature " + f.getName(), 4, nbModalite);


        // ----  TEST SUR LA PCM1 - mobile_Internet_Relay_Chat_clients_0 - Test avec plus de modalités

        listFeature = listPcm.get("pcm2").getConcreteFeatures();

        // Get the feature - Computing platform
        f = listFeature.get(0);
        featureViz = new FeatureViz(f);
        nbModalite = featureViz.getCountModalite();

        assertEquals("Pcm mobile_Internet_Relay_Chat_clients_0 - Nombre de modalité de la feature " + f.getName(), 9, nbModalite);
        System.out.println(f.getName());
    }

    @Test
    public void testGetListCharts() throws IOException {

    }

}