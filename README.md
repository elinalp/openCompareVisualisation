----------------------
Project main goal
----------------------

For this student project, we, as a group of five, have developed an application using the OpenCompare API. It allows the user to upload a product comparison matrice (PCM)
from a CSV File, a Wikipedia page etc. When the matrice is fully uploaded, you can editate it, export it, or even use graphs to visualize data.

For each feature, the application provides the possibility to choose between several charts, thanks to some buttons at the disposal of the user.


----------------------
Result
----------------------

OpenCompare's Graph visualization is not very relevant in , our application is filling that gap.
Indeed, OpenCompare is a powerful tool but it doesn't always offer the most relevant chart according to the main feature data type.
Our job here was to find the main data type for each feature, in order to generate the right buttons and charts whenever a user upload a PCM.
For example, for a specific feature, if there are more than five cells and if they contain integer values, a barchart button will appear at the bottom of the table.
If the user clicks on it, a barchart will be displayed below the table.
To go further, if this feature contains less than five cells, a piechart button will appear instead of a barchart one.



----------------------
Licence
----------------------

MIT licences and OpenCompare licences



----------------------
Development tools
----------------------

* [IntelliJ] (https://www.jetbrains.com/idea/)
* [Maven](https://maven.apache.org/)

----------------------
Technologies used
----------------------

To complete this project, we have used a large panel of technologies :

* [Junit4] (http://junit.org/junit4/)
* [Chart js](http://www.chartjs.org/)
* [Jquery] (https://jquery.com/)
* [Font awesome](http://fontawesome.io/)


----------------------
Project Architecture
----------------------

* pcms : contains pcm file
 * pcms_test_junit : contains csv file for test
* prototype : contains webpage and dependencies
 * prototype/html : Contains the prototype html pages
 * prototype/css : Contains the prototype css pages
 * prototype/javascript : Contains the prototype javascript pages

* src : implementation API
 * main.java.org.openCompare : source code
 * main.java.org.openCompare.test : implement some Junit tests to the visualization methods

 ----------------------
 Generate a html page
 ----------------------
 Implement the "Random" java class to simulate a type choice for a feature, as a user would probably do
 (Default userChoice : false, initialize in the class "HTMLGenerate")

 To launch the html page creation,
* Choose a pcm on the main HTMLGenerate.java (public static void main(String[] args){ } )
  * To CSV format
    writeFile(export("pcms/myFileExample.csv", "csv"));
  * Or to PCM format
    writeFile(export("pcms/myFileExample.pcm", "pcm"));
* Run the HTMLGenerate.java
* Open simple-example.html on browser

----------------------
Future work
----------------------

Some suggestions to improve the application, it might be interesting to add this functionnalities in the future :
* It would be relevant to implement Histogram chart to visualize quantitative data better
* Allow the user to overlay two barcharts
* Some very specific types are not treated by the Java code. We have identified them : "conditionalValue", "dimensionValue", "partialValue" and "unitValue".

