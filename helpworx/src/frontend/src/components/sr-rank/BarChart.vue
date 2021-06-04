<script>
  import {Bar, mixins} from 'vue-chartjs'
  const { reactiveProp } = mixins
  
  export default {
    extends: Bar,
    mixins: [reactiveProp],
    data () {
      return {
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              },
              gridLines: {
                display: true
              },
                scaleLabel: {
                    display: true,
                    labelString: "[SR 총 건수]",
                    fontColor: "black",
                }              
            }],
            xAxes: [ {
              gridLines: {
                display: false
              },
                scaleLabel: {
                    display: true,
                    labelString: "[시스템을 사용하는 고객사]",
                    fontColor: "black",
                }                
              
            }]
          },
          legend: {
            display: true
          },
          responsive: true,
          maintainAspectRatio: false
        }
      }
    },
    mounted () {
      this.renderChart(this.chartData, this.options)
    }
  }
</script>
