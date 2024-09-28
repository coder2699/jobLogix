// Function to generate the chart with dynamic data
function generateChart(dynamicData) {
  const chartConfig = {
    series: [
      {
        name: "Count",
        data: dynamicData.data,  // Dynamic data array
      },
    ],
    chart: {
      type: "bar",
      height: 240,
      toolbar: {
        show: false,
      },
    },
    title: {
      show: "",
    },
    dataLabels: {
      enabled: true,
      style: {
        colors: ['#757575'] 
      }
    },
    colors: ["#505fc0"],
    plotOptions: {
      bar: {
        columnWidth: "40%",
        borderRadius: 2,
      },
    },
    xaxis: {
      axisTicks: {
        show: false,
      },
      axisBorder: {
        show: false,
      },
      labels: {
        style: {
          colors: "#616161",
          fontSize: "12px",
          fontFamily: "inherit",
          fontWeight: 400,
        },
      },
      categories: dynamicData.categories,  // Dynamic categories
    },
    yaxis: {
      show: false,
      labels: {
        style: {
          colors: "#616161",
          fontSize: "12px",
          fontFamily: "inherit",
          fontWeight: 400,
        },
      },
    },
    grid: {
      show: false,
      borderColor: "#dddddd",
      strokeDashArray: 5,
      xaxis: {
        lines: {
          show: true,
        },
      },
      padding: {
        top: 5,
        right: 20,
      },
    },
    fill: {
      opacity: 0.8,
    }
  };

  const chart = new ApexCharts(document.querySelector("#bar-chart"), chartConfig);
  chart.render();
}

// Fetch dynamic data from API and generate chart
fetch('/api/chart-data')
  .then(response => response.json())
  .then(data => generateChart(data))
  .catch(error => console.error('Error fetching data:', error));