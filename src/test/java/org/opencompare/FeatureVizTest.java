package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.AbstractFeature;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by gbecan on 02/02/15.
 */
public class FeatureVizTest {

    private List<Feature> listFeature;

    public void setUp() throws Exception {
        // Load a PCM
        File pcmFile = new File("pcms/simple-example.pcm");
        PCMLoader loader = new KMFJSONLoader();
        PCMImpl pcm = (PCMImpl) loader.load(pcmFile).get(0).getPcm();
        assertNotNull(pcm);

        // Récupération de la liste des features de la matrice
        this.listFeature = pcm.getConcreteFeatures();
        System.out.print(listFeature.get(0));

    }

    @Test
    public void testGetTypesFeature() throws IOException {


    }

}
