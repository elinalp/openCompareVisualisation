package org.opencompare;

import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.util.PCMVisitor;
import org.opencompare.api.java.value.*;
import org.opencompare.api.java.io.HTMLExporter;

import java.io.File;
import java.util.List;
import java.io.*;


/**
 * Created by Pierre-Louis on 21/10/2016.
 */
public class Execute {
    public static void main(String[] args){
        HTMLGenerate html = new HTMLGenerate();
        // Define a file representing a PCM to load
        File pcmFile = new File("pcms/simple-example.pcm");


        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        // Load the file
        // A loader may return multiple PCM containers depending on the input format
        // A PCM container encapsulates a PCM and its associated metadata
        try{
            List<PCMContainer> pcmContainers = loader.load(pcmFile);
            for (PCMContainer pcmContainer : pcmContainers) {
                System.out.println(html.export(pcmContainer));
                String htmlContent = html.export(pcmContainer);

                FileWriter fichier = new FileWriter("prototype/html/simple-example.html");
                fichier.write (htmlContent);
                fichier.close();
            }


        } catch (Exception e){

        }
    }
}
