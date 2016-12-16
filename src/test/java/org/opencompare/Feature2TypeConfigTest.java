package org.opencompare;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.extractor.CellContentInterpreter;
import org.opencompare.api.java.impl.PCMFactoryImpl;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.CSVLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pierre-Louis on 25/11/2016.
 */
public class Feature2TypeConfigTest {
    private List<Feature> listFeature;
    private Hashtable<Class<Value>, Integer> collection;

    /**
     * Liste de PCM
     */
    private static HashMap<String, PCM> listPcm = new HashMap<>();

    /**
     * Chargement de des matrices
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Create a loader that can handle the file format
        CSVLoader csvL = new CSVLoader(new PCMFactoryImpl(), new CellContentInterpreter(new PCMFactoryImpl()));

        // Load Get PCMS
        PCM pcm1 = csvL.load(new File("pcms/pcms_test_junit/matriceTestPredominant.csv")).get(0).getPcm();

        listPcm.put("pcm1", pcm1);
    }


    /**
     * Test de la méthode getPredominantType
     *
     * @throws IOException
     */
    @Test
    public void testGetPredominantType() throws IOException{

        FeatureViz viz;
        Hashtable<Class<Value>, Integer> featureTypes;
        Feature2TypeConfig f2tc;

        List<Feature> listFeature;
        listFeature = listPcm.get("pcm1").getConcreteFeatures();

        for(Feature f : listFeature) {
            viz = new FeatureViz(f);
            featureTypes = viz.getTypesFeature();
            f2tc = new Feature2TypeConfig(featureTypes);

            switch (f.getName()) {
                case "String":
                    assertEquals("org.opencompare.api.java.impl.value.StringValueImpl",f2tc.getPredominantType().getCanonicalName());
                    break;
                case "Integer":
                    assertEquals("org.opencompare.api.java.impl.value.IntegerValueImpl",f2tc.getPredominantType().getCanonicalName());
                    break;
                case "Real":
                    assertEquals("org.opencompare.api.java.impl.value.RealValueImpl",f2tc.getPredominantType().getCanonicalName());
                    break;
                case "Boolean":
                    assertEquals("org.opencompare.api.java.impl.value.BooleanValueImpl",f2tc.getPredominantType().getCanonicalName());
                    break;
                case "Multiple":
                    assertEquals("org.opencompare.api.java.impl.value.MultipleImpl",f2tc.getPredominantType().getCanonicalName());
                    break;
                case "Empty":
                    assertEquals("org.opencompare.api.java.impl.value.NotAvailableImpl",f2tc.getPredominantType().getCanonicalName());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Test de la méthode
     * @throws IOException
     */
    @Test
    public void testGetRandomType() throws IOException{
        FeatureViz viz;
        Hashtable<Class<Value>, Integer> featureTypes;
        Feature2TypeConfig f2tc;

        List<Feature> listFeature;
        listFeature = listPcm.get("pcm1").getConcreteFeatures();
            List<String> listValuesCanonicalName = new ArrayList<String>();

        for(Feature f : listFeature) {
            viz = new FeatureViz(f);
            featureTypes = viz.getTypesFeature();
            f2tc = new Feature2TypeConfig(featureTypes);

            switch (f.getName()) {
                case "String":
                    listValuesCanonicalName.clear();
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.IntegerValueImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.StringValueImpl");
                    assertTrue(listValuesCanonicalName.contains(f2tc.getRandomType().getCanonicalName()));
                    break;
                case "Integer":
                    listValuesCanonicalName.clear();
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.IntegerValueImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.StringValueImpl");
                    assertTrue(listValuesCanonicalName.contains(f2tc.getRandomType().getCanonicalName()));
                    break;
                case "Real":
                    listValuesCanonicalName.clear();
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.RealValueImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.NotAvailableImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.StringValueImpl");
                    assertTrue(listValuesCanonicalName.contains(f2tc.getRandomType().getCanonicalName()));
                    break;
                case "Boolean":
                    listValuesCanonicalName.clear();
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.BooleanValueImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.IntegerValueImpl");
                    assertTrue(listValuesCanonicalName.contains(f2tc.getRandomType().getCanonicalName()));
                    break;
                case "Multiple":
                    listValuesCanonicalName.clear();
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.MultipleImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.StringValueImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.IntegerValueImpl");
                    assertTrue(listValuesCanonicalName.contains(f2tc.getRandomType().getCanonicalName()));
                    break;
                case "Empty":
                    listValuesCanonicalName.clear();
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.StringValueImpl");
                    listValuesCanonicalName.add("org.opencompare.api.java.impl.value.NotAvailableImpl");
                    assertTrue(listValuesCanonicalName.contains(f2tc.getRandomType().getCanonicalName()));
                    break;
                default:
                    break;
            }
        }
    }
}
