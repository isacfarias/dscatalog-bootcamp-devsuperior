import React, { useState } from 'react';
import BaseForm from '../../BaseForm';
import './styles.scss';

type FormState = {
    name: string;
    category: string;
    price: string;
}


const Form = () => {

    const [formData, setFormData] = useState<FormState>({
        name:  '',
        category: '',
        price: ''
    });

    const handleOnChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const name = event.target.name;
        const value = event.target.value; 
        setFormData(data => ({ ...data, [name]: value}));
        console.log({name, value});

    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(formData);
      
    }

    return (
        <form onSubmit={handleSubmit}>
            <BaseForm title="Cadastrar Produto">
                <div className="row">
                    <div className="col-6">
                        
                        <input type="text"
                            name="name"
                            className="form-control  mb-5  my-5"
                            onChange={handleOnChange}
                            value={formData.name}
                        />

                        <select value={formData.category}
                           className="form-control mb-5" 
                           onChange={handleOnChange}
                           name="category">
                            <option value="livro">Livros</option>
                            <option value="eletronicos">Eletronicos</option>
                            <option value="computadores">Computadores</option>
                        </select>

                         <input type="text"
                            name="price"
                            className="form-control  mb-5"
                            onChange={handleOnChange}
                            value={formData.price}
                        />
                    </div>
                    <div className="col-6"></div>
                </div>

            </BaseForm>
        </form>
    );

}

export default Form;