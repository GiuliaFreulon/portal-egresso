import React from "react";
import './CargoCard.css'

const CargoCard = ({ local, anoInicio, anoFim, descricao}) => {
    const anoFimDisplay = anoFim ? anoFim : 'Atual';

    return (
            <div className="cargo__container">
                <p className="cargo__title">{local}</p>
                <p className="cargo__data">{anoInicio}-{anoFimDisplay}</p>
                <span className="cargo__descricao">{descricao}</span>
            </div>
    )
}

export default CargoCard;