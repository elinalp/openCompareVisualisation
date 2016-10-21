package org.opencompare;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.opencompare.api.java.*;
import org.opencompare.api.java.util.Pair;
import org.opencompare.*;
import org.opencompare.api.java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gbecan on 13/10/14.
 */
public class HTMLGenerate {

    private ExportMatrixExporter exportMatrixExporter = new ExportMatrixExporter();

    public String export(PCMContainer pcmContainer) {
        PCM pcm = pcmContainer.getPcm();

        final PCMMetadata metadata;
        if (pcmContainer.getMetadata() == null) {
            metadata = new PCMMetadata(pcm);
        } else {
            metadata = pcmContainer.getMetadata();
        }

        // Create HTML document
        Document doc = Document.createShell("");
        Element head = doc.head();
        Element body = doc.body();

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


        // Create title
        head.appendElement("title").text(pcm.getName());
        body.appendElement("h1").text(pcm.getName());

        // Create table
        Element table = body.appendElement("table").attr("border", "1");
        table.addClass("table");

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
                    htmlCell.text(textToHTML(exportCell.getContent()));

                    if (exportCell.getRowspan() > 1) {
                        htmlCell.attr("rowspan", "" + exportCell.getRowspan());
                    }

                    if (exportCell.getColspan() > 1) {
                        htmlCell.attr("colspan", "" + exportCell.getColspan());
                    }
                }

            }
        }


        Element dernierLigne = table.appendElement("tr");
        dernierLigne.appendElement("td");
        for (int column = 1; column < exportMatrix.getNumberOfColumns(); column++) {
            Element colonneGraph = dernierLigne.appendElement("td");
            colonneGraph.addClass("graph_cell");
            Element iconeBarChart = colonneGraph.appendElement("i");
            iconeBarChart.addClass("fa fa-bar-chart");
            Element iconePieChar = colonneGraph.appendElement("i");
            iconePieChar.addClass("fa fa-pie-chart");

        }




        // Export to HTML code
        Document.OutputSettings settings = new Document.OutputSettings().prettyPrint(false);
        return doc.outputSettings(settings).outerHtml();

    }

    private String textToHTML(String text) {
//        String formattedText = text
//                .replaceAll("\n", "<br />");
//        return formattedText;
        return text;
    }

}
