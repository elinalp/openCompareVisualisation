package org.opencompare;

import org.opencompare.api.java.Feature;

/**
 * Created by Pierre-Louis on 18/11/2016.
 */
public class FeatureVizFactory {

    public FeatureViz makeFeatureViz (Feature f){
        return new FeatureViz(f);
    }
}
