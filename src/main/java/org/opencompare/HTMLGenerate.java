package org.opencompare;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.util.Pair;
import org.opencompare.*;
import org.opencompare.api.java.io.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gbecan on 13/10/14.
 */
public class HTMLGenerate {

    private static ExportMatrixExporter exportMatrixExporter = new ExportMatrixExporter();
    private static Document doc = Document.createShell("");
    private static Element head = doc.head();
    private static Element body = doc.body();

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
        scriptChartist.attr("src" ,"/getting-started/chartist/chartist.min.js");

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
        Element textPL= divPL.appendElement("span").attr("class", "span_nom").text("Pierre-Louis Ollivier");
        Element divElina = divRow.appendElement("div").attr("class", "col-md-2");
        Element imgElina = divElina.appendElement("div").attr("class", "circular_elina");
        Element textElina = divElina.appendElement("span").attr("class", "span_nom").text("Elina LEPETIT");
        Element divKilian = divRow.appendElement("div").attr("class", "col-md-2");
        Element imgKilian = divKilian.appendElement("div").attr("class", "circular_kilian");
        Element textKilain = divKilian.appendElement("span").attr("class", "span_nom").text("Kilian Guégan");
        divRow.appendElement("div").attr("class", "col-md-1");

        // Create table
        Element table = body.appendElement("table").attr("border", "1");
        table.addClass("table table-striped table-hover");

        ExportMatrix exportMatrix = exportMatrixExporter.export(pcmContainer);

        for (int row = 0; row < exportMatrix.getNumberOfRows(); row++) {
            Element htmlRow = table.appendElement("tr");

            for (int column = 0; column < exportMatrix.getNumberOfColumns(); column++) {
                ExportCell exportCell = exportMatrix.getCell(row, column);

                if (exportCell != null) {
                    Element htmlCell;
                    if (exportCell.isFeature() || exportCell.isInProductsKeyColumn()) {
                        htmlCell = htmlRow.appendElement("th");
                    } else {
                        htmlCell = htmlRow.appendElement("td");
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
        dernierLigne.appendElement("td");
        for (int column = 1; column < exportMatrix.getNumberOfColumns(); column++) {
            Element colonneGraph = dernierLigne.appendElement("td");
            colonneGraph.addClass("graph_cell");

            Element lienBarChart = colonneGraph.appendElement("a");
            lienBarChart.attr("data-type", "barchart");
            lienBarChart.addClass("GenereGraph");
            Element iconeBarChart = lienBarChart.appendElement("i");
            iconeBarChart.addClass("fa fa-bar-chart");

            Element lienPieChart = colonneGraph.appendElement("a");
            lienPieChart.attr("data-type", "piechart");
            lienPieChart.addClass("GenereGraph");
            Element iconePieChar = lienPieChart.appendElement("i");
            iconePieChar.addClass("fa fa-pie-chart");

        }


        //Div pour la création de graphe
        Element divGraph = body.appendElement("div");
        divGraph.addClass("ct-chart ct-perfect-fourth");
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
    public static String export(String nameFile) {
        File pcmFile = new File(nameFile);
        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();
        //Create head of the HTML document
        createHead();

        try
        {
            // Load the file
            // A loader may return multiple PCM containers depending on the input format
            // A PCM container encapsulates a PCM and its associated metadata
            List<PCMContainer> pcmContainers = loader.load(pcmFile);
            for (PCMContainer pcmContainer : pcmContainers) {
                PCM pcm = pcmContainer.getPcm();
                setTitleHtml(pcm.getName());
                final PCMMetadata metadata;
                if (pcmContainer.getMetadata() == null) {
                    metadata = new PCMMetadata(pcm);
                } else {
                    metadata = pcmContainer.getMetadata();
                }
                //Create body of the HTML document
                createBody(pcmContainer);
            }
        }catch (Exception e){

        }
        //HTML code of the document
        Document.OutputSettings settings = new Document.OutputSettings().prettyPrint(false);
        return doc.outputSettings(settings).outerHtml();
    }

    private static void setTitleHtml(String pcmName){
        head.appendElement("title").text(pcmName);
    }

    public static void main(String[] args){
        try
        {
            // Ecriture du fichier HTML (écrase si le fichier existe déjà)
            writeFile(export("pcms/example.pcm"));
        }catch (Exception e){

        }
    }


}
