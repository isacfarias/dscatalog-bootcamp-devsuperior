import { makeRequest } from 'core/utils/request';
import { url } from 'node:inspector';
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
        const payload = {
            ...formData,
            imgUrl: 'https://fujiokadistribuidor.vteximg.com.br/arquivos/ids/178128',
            categories: [{ id: formData.category}]
        }
        
        makeRequest({ url:'/produtcs', method:'POST', data: payload });      
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
                            <option value="1">Livros</option>
                            <option value="2">Eletronicos</option>
                            <option value="3">Computadores</option>
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