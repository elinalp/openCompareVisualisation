package org.opencompare;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.extractor.CellContentInterpreter;
import org.opencompare.api.java.impl.PCMFactoryImpl;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.CSVLoader;
import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.value.BooleanValue;
import org.opencompare.api.java.value.StringValue;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.impl.value.*;
import scala.Int;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Classe de test da la classe FeatureViz
 */
public class FeatureVizTest {

    /**
     * Liste de PCM
     */
    private static HashMap<String, PCM> listPcm = new HashMap<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Create a loader that can handle the file format
        CSVLoader csvL = new CSVLoader(new PCMFactoryImpl(), new CellContentInterpreter(new PCMFactoryImpl()));

        // Load Get PCMS
        PCM pcm1 = csvL.load(new File("pcms/pcms_test_junit/matrice_all_type.csv")).get(0).getPcm();
        PCM pcm2 = csvL.load(new File("pcms/pcms_test_junit/matrice_typeFeatureDifferent.csv")).get(0).getPcm();

        listPcm.put("pcm_all_types", pcm1);
        listPcm.put("pcm_feature_different", pcm2);
    }


    @Test
    public void testGetTypesFeature() throws IOException {

        List<Feature> listFeature;
        FeatureViz featureViz;
        Hashtable<Class<Value>, Integer> listeType;

        // TEST PCM  ALL_TYPES
        listFeature = listPcm.get("pcm_all_types").getConcreteFeatures();

        for (Feature feature : listFeature) {

            switch (feature.getName()){
                case "reel" :
                    featureViz = new FeatureViz(feature);
                    listeType = featureViz.getTypesFeature();

                    assertEquals("Type feature - matrice correcte - Feature reel - Taille", listeType.size(), 1);
                    assertTrue("Type feature - matrice correcte - Feature reel - Keys",listeType.containsKey(MultipleImpl.class));
                    assertEquals("Type feature - matrice correcte - Feature reel - Count", (int) listeType.get(MultipleImpl.class), 31);
                    break;

                case "boolean" :
                    featureViz = new FeatureViz(feature);
                    listeType = featureViz.getTypesFeature();

                    assertEquals("Type feature - matrice correcte - Feature boolean - Taille", listeType.size(), 1);
                    assertTrue("Type feature - matrice correcte - Feature boolean - Keys",listeType.containsKey(BooleanValueImpl.class));
                    assertEquals("Type feature - matrice correcte - Feature boolean - Count", (int) listeType.get(BooleanValueImpl.class), 31);
                    break;

                case "integer" :
                    featureViz = new FeatureViz(feature);
                    listeType = featureViz.getTypesFeature();

                    assertEquals("Type feature - matrice correcte - Feature integer - Taille", listeType.size(), 1);
                    assertTrue("Type feature - matrice correcte - Feature integer - Keys",listeType.containsKey(IntegerValueImpl.class));
                    assertEquals("Type feature - matrice correcte - Feature integer - Count", (int) listeType.get(IntegerValueImpl.class), 31);
                    break;

                case "moda_inf" :
                    featureViz = new FeatureViz(feature);
                    listeType = featureViz.getTypesFeature();

                    assertEquals("Type feature - matrice correcte - Feature moda_inf - Taille", listeType.size(), 1);
                    assertTrue("Type feature - matrice correcte - Feature moda_inf - Keys",listeType.containsKey(StringValueImpl.class));
                    assertEquals("Type feature - matrice correcte - Feature moda_inf - Count", (int) listeType.get(StringValueImpl.class), 31);
                    break;
            }
        }

        // TEST PCM TYPE FEATURE DIFFERENT
        listFeature = listPcm.get("pcm_feature_different").getConcreteFeatures();

        for (Feature feature : listFeature) {

            switch (feature.getName()){

                case "boolean" :
                    featureViz = new FeatureViz(feature);
                    listeType = featureViz.getTypesFeature();

                    assertEquals("Type feature - matrice type feature different - Feature boolean - Taille", listeType.size(), 2);
                    assertTrue("Type feature - matrice type feature different - Feature boolean - Keys",listeType.containsKey(BooleanValueImpl.class));
                    assertTrue("Type feature - matrice type feature different - Feature boolean - Keys",listeType.containsKey(IntegerValueImpl.class));
                    assertEquals("Type feature - matrice type feature different - Feature boolean - Count", (int) listeType.get(BooleanValueImpl.class), 29);
                    assertEquals("Type feature - matrice type feature different - Feature boolean - Count", (int) listeType.get(IntegerValueImpl.class), 2);
                    break;

                case "moda_sup" :
                    featureViz = new FeatureViz(feature);
                    listeType = featureViz.getTypesFeature();

                    assertEquals("Type feature - matrice type feature different - Feature moda_inf - Taille", listeType.size(), 3);
                    assertTrue("Type feature - matrice correcte - Feature moda_inf - Keys",listeType.containsKey(StringValueImpl.class));
                    assertTrue("Type feature - matrice correcte - Feature moda_inf - Keys",listeType.containsKey(IntegerValueImpl.class));
                    assertTrue("Type feature - matrice correcte - Feature moda_inf - Keys",listeType.containsKey(NotAvailableImpl.class));
                    assertEquals("Type feature - matrice correcte - Feature moda_inf - Count", (int) listeType.get(StringValueImpl.class), 28);
                    assertEquals("Type feature - matrice correcte - Feature moda_inf - Count", (int) listeType.get(IntegerValueImpl.class), 1);
                    assertEquals("Type feature - matrice correcte - Feature moda_inf - Count", (int) listeType.get(NotAvailableImpl.class), 2);
                    break;
            }
        }
    }

    @Test
    public void testGetCountModalite() throws IOException {

        List<Feature> listFeature;
        FeatureViz featureViz;
        int nbModalite;

        // TEST PCM  ALL_TYPES
        listFeature = listPcm.get("pcm_all_types").getConcreteFeatures();

        for (Feature feature : listFeature) {

            switch (feature.getName()) {
                case "moda_sup":
                    featureViz = new FeatureViz(feature);
                    nbModalite = featureViz.getCountModalite();
                    assertEquals("Matrice correcte - Nombre de modalité de la feature " + feature.getName(), 7, nbModalite);
                    break;
                case "moda_inf":
                    featureViz = new FeatureViz(feature);
                    nbModalite = featureViz.getCountModalite();
                    assertEquals("Matrice correcte - Nombre de modalité de la feature " + feature.getName(), 4, nbModalite);
                    break;
            }
        }
    }

    @Test
    public void testGetListCharts() throws IOException {

    }

}