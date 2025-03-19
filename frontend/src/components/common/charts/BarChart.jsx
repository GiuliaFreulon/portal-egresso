import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BarChart = ({ data }) => {
    // Extrai labels (nomes dos cursos) e datasets (empregados/não empregados)
    const labels = Object.keys(data);
    const empregados = labels.map(curso => data[curso]["Empregados"]);
    const naoEmpregados = labels.map(curso => data[curso]["Não Empregados"]);

    // Configuração do gráfico
    const chartData = {
        labels: labels,
        datasets: [
            {
                label: 'Empregados',
                data: empregados,
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                stack: 'Stack 1',
            },
            {
                label: 'Não Empregados',
                data: naoEmpregados,
                backgroundColor: 'rgba(255, 99, 132, 0.6)',
                stack: 'Stack 1',
            }
        ]
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: false,
                text: 'Situação de Emprego por Curso',
                position: 'bottom',
            }
        },
        scales: {
            x: {
                stacked: true,
            },
            y: {
                stacked: true,
                beginAtZero: true
            }
        }
    };

    return <Bar data={chartData} options={options} />;
};

export default BarChart;