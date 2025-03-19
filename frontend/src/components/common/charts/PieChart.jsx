import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart = ({ labels, dataValues }) => {
    // Função para gerar cores dinamicamente com base no número de labels
    const generateColors = (count) => {
        return Array.from({ length: count }, (_, index) => {
            const hue = Math.floor((index * 360) / count); // Distribui tons igualmente no círculo HSL
            return `hsl(${hue}, 70%, 50%)`; // Saturação 70%, Luminosidade 50%
        });
    };

    // Configuração dinâmica do gráfico
    const data = {
        labels: labels, // Labels recebidas por props
        datasets: [
            {
                data: dataValues, // Valores recebidos por props
                backgroundColor: generateColors(labels?.length || 0),
                hoverBackgroundColor: generateColors(labels?.length || 0),
            }
        ]
    };

    return (
        <Pie data={data} />
    );
};

export default PieChart;