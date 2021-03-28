import React from 'react';

type Props = {
  text?: string; //? usa o  ? para dizer que não é obrigatŕio
}


const Alert = ({text}: Props) => (
  <div className = "alert alert-primary">
        Hellos {text}
    </div>
);

export default Alert;