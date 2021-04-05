import { makeRequest } from 'core/utils/request';
import { url } from 'node:inspector';
import React, { useState } from 'react';
import BaseForm from '../../BaseForm';
import './styles.scss';

type FormState = {
    name: string;
    category: string;
    price: string;
    description: string;
}

type FormEvent = React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>;

const Form = () => {

    const [formData, setFormData] = useState<FormState>({
        name:  '',
        category: '',
        price: '',
        description:''
    });

    const handleOnChange = (event: FormEvent ) => {
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
        
        makeRequest({ url:'/produtcs', method:'POST', data: payload })
        .then(() => {
            setFormData({name:'', category:'', price:'', description:''});
        });      
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
                    <div className="col-6">

                        <textarea className="form-control  mb-5"
                                  name="description"
                                  cols={30}
                                  rows={10}
                                  onChange={handleOnChange}
                                  value={formData.description}
                                  />
                    </div>
                </div>

            </BaseForm>
        </form>
    );

}

export default Form;