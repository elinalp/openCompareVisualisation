package org.opencompare;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.opencompare.api.java.*;
import org.opencompare.api.java.extractor.CellContentInterpreter;
import org.opencompare.api.java.impl.FeatureImpl;
import org.opencompare.api.java.impl.PCMFactoryImpl;
import org.opencompare.api.java.impl.ValueImpl;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.util.Pair;
import org.opencompare.*;
import org.opencompare.api.java.io.*;
import org.opencompare.chart.Chart;

import javax.management.Query;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by gbecan on 13/10/14.
 */
public class HTMLGenerate {

    private static ExportMatrixExporter exportMatrixExporter = new ExportMatrixExporter();
    private static Document doc = Document.createShell("");
    private static Element head = doc.head();
    private static Element body = doc.body();
    private static FeatureVizFactory featureVizFactory = new FeatureVizFactory();

    //Données à envoyé au js

    private static ArrayList<String> labels = new ArrayList<>();
    private static Hashtable<Integer, String> data = new Hashtable<Integer, String>();

    public static final boolean USER_CHOICE = false;

    /**
     * Crée l'entête du document HTML
     */
    private static void createHead(){

        // Meta info
        head.appendElement("meta").attr("charset", "utf-8");

        //Ajout JQuery
        Element scriptJquery = head.appendElement("script");
        scriptJquery.attr("src" ,"https://code.jquery.com/jquery-3.1.1.min.js");
        scriptJquery.attr("integrity", "sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=");
        scriptJquery.attr("crossorigin", "anonymous");

        //Ajout de Bootstrap
        //TODO: Rajout lib Bootstrap pour execution en local
        Element link = head.appendElement("link");
        link.attr("rel","stylesheet");
        link.attr("href", "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css");
        link.attr("integrity","sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u");
        link.attr("crossorigin","anonymous");
        Element script = head.appendElement("script");
        script.attr("src" ,"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js");
        script.attr("integrity", "sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa");
        script.attr("crossorigin", "anonymous");

        //Ajout Chartist
        Element linkChartist = head.appendElement("link");
        linkChartist.attr("rel","stylesheet");
        linkChartist.attr("href", "/getting-started/chartist/chartist.min.css");

        Element scriptChartist =  head.appendElement("script");
        scriptChartist.attr("src" ,"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js");

        Element scriptChartist2 =  head.appendElement("script");
        scriptChartist.attr("src" ,"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.bundle.min.js");

        //Ajout Font Awesome
        Element linkFont = head.appendElement("link");
        linkFont.attr("rel", "stylesheet");
        linkFont.attr("href", "/getting-started/font-awesome/css/font-awesome.css");

        //Ajout du main script javascript
        Element scriptMain =  head.appendElement("script");
        scriptMain.attr("src" ,"/getting-started/prototype/javascript/main.js");

        //Ajout du css global
        Element cssGlobal =  head.appendElement("link");
        cssGlobal.attr("rel", "stylesheet");
        cssGlobal.attr("href", "/getting-started/prototype/css/main.css");

    }

    /**
     * Crée le body du document HTML
     *
     * @param pcmContainer
     *      Objet contenant une PCM
     */
    private static void createBody(PCMContainer pcmContainer){

        //Création de l'entête projet

        Element banner = body.appendElement("section").attr("id", "banner");
        Element divHeader = banner.appendElement("div").attr("class", "inner");
        divHeader.appendElement("h1").attr("class", "h1_custom").text("Projet PDL MIAGE");


        Element divProfil = banner.appendElement("div").attr("class", "container");
        divProfil.appendElement("h2").attr("class", "h2_custom").text("Notre équipe: ");
        Element divRow = divProfil.appendElement("div").attr("class", "row text-center");

        //Génération des cinq div membres
        divRow.appendElement("div").attr("class", "col-md-1");
        Element divAntoine = divRow.appendElement("div").attr("class", "col-md-2");
        Element imgAntoine = divAntoine.appendElement("div").attr("class", "circular_antoine");
        Element textAntoine = divAntoine.appendElement("span").attr("class", "span_nom").text("Antoine RAVET");
        Element divAlexis = divRow.appendElement("div").attr("class", "col-md-2");
        Element imgAlexis = divAlexis.appendElement("div").attr("class", "circular_alexis");
        Element textAlexis = divAlexis.appendElement("span").attr("class", "span_nom").text("Alexis RENAULT");
        Element divPL = divRow.appendElement("div").attr("class", "col-md-2");
        Element imgPL = divPL.appendElement("div").attr("class", "circular_PL");
        Element textPL= divPL.appendElement("span").attr("class", "span_nom").text("Pierre-Louis OLLIVIER");
        Element divElina = divRow.appendElement("div").attr("class", "col-md-2");
        Element imgElina = divElina.appendElement("div").attr("class", "circular_elina");
        Element textElina = divElina.appendElement("span").attr("class", "span_nom").text("Elina LEPETIT");
        Element divKilian = divRow.appendElement("div").attr("class", "col-md-2");
        Element imgKilian = divKilian.appendElement("div").attr("class", "circular_kilian");
        Element textKilain = divKilian.appendElement("span").attr("class", "span_nom").text("Kilian GUEGAN");
        divRow.appendElement("div").attr("class", "col-md-1");

        // Create table
        Element table = body.appendElement("table").attr("border", "1");
        table.addClass("table table-striped table-hover");
        List<Feature> features = pcmContainer.getPcm().getConcreteFeatures();
        //Création de la ligne des features
        Element rowFeature = table.appendElement("tr");
        //rowFeature.appendElement("td");
        /*
        for(Feature feature: features){
            rowFeature.appendElement("th").text(feature.getName());
            for(Cell cell: feature.getCells()){
                if(data.get(cell.getFeature().getName()) != null){
                    System.out.println(cell.getFeature().getName());
                    data.put(cell.getFeature().getName(), data.get(cell.getFeature().getName()) + ',' + '"' + cell.getContent() + '"');
                }else {
                    data.put(cell.getFeature().getName(), '"' + cell.getContent() + '"');
                }
            }
        }


        List<Product> products = pcmContainer.getPcm().getProducts();


        for (Product product : products) {
            Element productsRow = table.appendElement("tr");
            productsRow.appendElement("th").text(product.getKeyContent());
            labels.add( '"' + product.getKeyContent() + '"');
            for(Cell cell : product.getCells()){
                System.out.println("Product: " + product.getKeyContent());
                System.out.println("Données " + cell);
                if(!cell.getProduct().getKeyCell().equals(cell)){
                    productsRow.appendElement("td").text(cell.getContent());
                }

            }

        } */



        ExportMatrix exportMatrix = exportMatrixExporter.export(pcmContainer);
        for (int row = 0; row < exportMatrix.getNumberOfRows(); row++) {
            Element htmlRow = table.appendElement("tr");

            for (int column = 0; column < exportMatrix.getNumberOfColumns(); column++) {
                ExportCell exportCell = exportMatrix.getCell(row, column);
                if (exportCell != null) {
                    Element htmlCell;
                    if (exportCell.isFeature() || exportCell.isInProductsKeyColumn()) {
                        htmlCell = htmlRow.appendElement("th");
                        //On ignore la première ligne
                        if(row >= 1){
                            labels.add( '"' + exportCell.getContent() + '"');
                        }
                    } else {
                        htmlCell = htmlRow.appendElement("td");
                        if(data.get(column) != null){
                            data.put(column, data.get(column) + ',' + '"' + exportCell.getContent() + '"');
                        }else {
                            data.put(column, '"' + exportCell.getContent() + '"');
                        }
                    }

                    htmlCell.attr("style", "white-space: pre;");
                    htmlCell.text(exportCell.getContent());

                    if (exportCell.getRowspan() > 1) {
                        htmlCell.attr("rowspan", "" + exportCell.getRowspan());
                    }

                    if (exportCell.getColspan() > 1) {
                        htmlCell.attr("colspan", "" + exportCell.getColspan());
                    }
                }

            }
        }

        //Génératon de la dernière ligne : icone d'affichage des graphes
        Element dernierLigne = table.appendElement("tr");
        //dernierLigne.appendElement("td");
        int indexFeature = 0;
        for(Feature f : features){

            //Dans tous les cas on crée un td mais il ne sera pas remplit pour le product
            Element colonneGraph = dernierLigne.appendElement("td");

            //On exclut le product pour l'algo suivant
            if(indexFeature>0){

                System.out.println(f);

                colonneGraph.addClass("graph_cell");
                //Algo récupération liste des charts
                FeatureViz viz = featureVizFactory.makeFeatureViz(f);
                Hashtable<Class<Value>, Integer> typesCollection = viz.getTypesFeature();
                Feature2TypeConfig f2tc = new Feature2TypeConfig(typesCollection);
                Class<Value> valuePredominant;
                if(!USER_CHOICE){
                    valuePredominant = f2tc.getPredominantType();
                }else{
                    valuePredominant = f2tc.getRandomType();
                }

                viz.setTypeSelected(valuePredominant);
                List<Chart> chartsList = viz.getListCharts();

                //Parcours de la liste des charts
                for(Chart c : chartsList) {
                    //System.out.println(data.toString());
                    //System.out.println(labels.toString());

                    // Antoine pour chaque Chart c
                    // Le type des feature a choisir pour le graphique ? ( Surement afficher pour chaque cellules le type
                    // Si il y a plusieurs type de données dans le feature on prend uniqement les données associé a c.getFeatureType();
                    // Quelque chose comme ca : data-cell-type = cell.getInterpretation()
                    c.getFeatureType();
                    c.getNameChart();
                    c.getNameIcon();
                    Element lienChart = colonneGraph.appendElement("a");
                    lienChart.attr("data-type", c.getNameChart());

                    String tableauData = "[" + data.get(indexFeature) + "]";

                    lienChart.attr("data-data", tableauData);
                    lienChart.attr("data-labels", labels.toString());
                    lienChart.addClass("GenereGraph");
                    Element iconeBarChart = lienChart.appendElement("i");
                    iconeBarChart.addClass(c.getNameIcon());
                }
            }
            //On incrémente l'index
            indexFeature++;
        }


        //Div pour la création de graphe
        Element divGraph = body.appendElement("canvas");
        divGraph.attr("id", "myChart");
        divGraph.attr("width", "400");
        divGraph.attr("height", "400");
    }



    /**
     *  Ecrit une chaîne de caractère dans un fichier
     * @param htmlContent
     *      La chaîne de caractères contenant le code de la page HTML
     */
    private static void writeFile(String htmlContent){
        try{
            FileWriter fichier = new FileWriter("prototype/html/simple-example.html");
            fichier.write (htmlContent);
            fichier.close();
        } catch (Exception e){

        }
    }

    /**
     * Converti un fichier contenant une PCM en code HTML
     *
     * @param nameFile
     *      Le nom du fichier contenant la PCM à exporter
     *
     * @return String : la chaîne de caractère contenant le code de la page HTMl
     */
    public static String export(String nameFile, String type) {

        if(type.equals("csv")){
            // Create a loader that can handle the file format
            CSVLoader loader = new CSVLoader(new PCMFactoryImpl(), new CellContentInterpreter(new PCMFactoryImpl()));

            // Load Get PCMS
            PCMContainer pcmContainer = null;
            PCM pcm = null;
            try {
                pcmContainer = loader.load(new File(nameFile)).get(0);
                pcm = loader.load(new File(nameFile)).get(0).getPcm();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create head of the HTML document
            createHead();
            setTitleHtml(pcm.getName());
            //Create body of the HTML document
            createBody(pcmContainer);
        }else{

            // Create a loader that can handle the file format
            PCMLoader loader = new KMFJSONLoader();

            PCMContainer pcmContainer = loader.load(nameFile).get(0);
            PCM pcm = pcmContainer.getPcm();
            //Create head of the HTML document
            createHead();
            setTitleHtml(pcm.getName());
            //Create body of the HTML document
            createBody(pcmContainer);
        }


        //HTML code of the document
        Document.OutputSettings settings = new Document.OutputSettings().prettyPrint(false);
        return doc.outputSettings(settings).outerHtml();
    }

    private static void setTitleHtml(String pcmName){
        head.appendElement("title").text(pcmName);
        body.appendElement("h1").text(pcmName);
    }

    public static void main(String[] args){
        try
        {

            writeFile(export("pcms/pcms_test_junit/matrice_all_type.csv", "csv"));
        }catch (Exception e){

        }
    }

    /**
     * Méthode renvoyant un objet PCM à partir d'un nom de fichier
     * @param nameFile
     *      Le nom du fichier
     * @return une instance de PCM, qui correspond à la PCM contenu dans le fichier
     * @throws IOException
     */
    private static PCM getPCM(String nameFile) throws IOException {
        File pcmFile = new File(nameFile);
        PCMLoader loader = new KMFJSONLoader();
        return loader.load(pcmFile).get(0).getPcm();
    }


}
