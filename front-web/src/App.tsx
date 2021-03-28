import React, { useState, useEffect } from 'react';

const App = () => {

  const [counter, setCounter] = useState(0);

  useEffect(() => {
    console.log("componente inicializado", counter);
  }, [counter]);

  return (
    <div className="container mt-5">
      <button type="button" className="btn btn-primary mr-5"
      onClick={() => setCounter(counter + 1)}
      >
        +
      </button>
      <span>
      {counter}
      </span>
      <button type="button" className="btn btn-primary ml-5"
      onClick={() => setCounter(counter - 1)}
      >
        -
      </button>

      { counter > 5 &&  <h1> Valor é maior que 5</h1> }
    </div>
  );
}

export default App;