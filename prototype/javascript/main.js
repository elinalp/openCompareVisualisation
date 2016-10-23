//TODO : Généraliser le code et éviter le doublon de code utiles pour chacun des graphes

$( document ).ready(function() {
    $(".GenereGraph").click(function(event) {
      event.preventDefault();
      if($(this).data("type") == "barchart"){
         var data = {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                  series: [
                  [5, 4, 3, 7, 5, 10, 3, 4, 8, 10, 6, 8],
                  [3, 2, 9, 5, 4, 6, 4, 6, 7, 8, 7, 4]
                ]
              };

              var options = {
                seriesBarDistance: 15
        };
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