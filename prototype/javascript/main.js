//TODO : Généraliser le code et éviter le doublon de code utiles pour chacun des graphes

$( document ).ready(function() {




    $(".GenereGraph").click(function(event) {

        $('#myChart').remove(); // this is my <canvas> element
        $('body').append('<canvas id="myChart"><canvas>');
        var canvas = $("#myChart");
        event.preventDefault();
        var type;

          //On définit le type de graphique
        switch($(this).data("type")) {
              case "barChart":
                  type = "bar";
                  break;
              case "pieChart":
                  type = "pie";
                  break;
              case "polarChart":
                  type = "radar";
                  break;
              default:
                  type = "bar";
        }

        var myChart = new Chart(canvas, {
            type: type,
            data: {
                labels: $(this).data("labels"),
                datasets: [{
                    data: $(this).data("data"),
                    borderWidth: 1,
                    backgroundColor:
                     [
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                     ],
                     borderColor: [
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                     ],
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });
    });
});
