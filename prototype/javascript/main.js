//TODO : Généraliser le code et éviter le doublon de code utiles pour chacun des graphes

$( document ).ready(function() {
    $(".GenereGraph").click(function(event) {
      event.preventDefault();
      if($(this).data("type") == "barchart"){
              var options = {
                seriesBarDistance: 15
        };
        console.log(data);
        new Chartist.Bar('.ct-chart', data, options);
      }else if($(this).data("type") == "piechart"){

        var data = {
          series: [5, 3, 4]
        };
        var sum = function(a, b) { return a + b };

        new Chartist.Pie('.ct-chart', data, options);
      }

    });
});