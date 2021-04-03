import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ProductResponse } from '../../core/types/Products';
import { makeRequest } from '../../core/utils/request';
import ProductCard from './components/ProductCard';
import './styles.scss';

const Catalog = () => {
    // quando o componente iniciar o componente deve buscar a lista
    // quando a lista de produtos estiver disponivel,
     const [productResponse, setProductResponse] = useState<ProductResponse>();
    
    // popular um estado no componente, e listar os produtos dinamicamente
    useEffect(() => {

        const params = {
            page: 0,
            linesPerPage:5
        };
            
        makeRequest({ url:'/produtcs/', params })
        .then(response => setProductResponse(response.data));

    }, []);




    return (
        <div className="catalog-container ">
            <h1 className="catalog-title">
                Catálogo de produtos
        </h1>

            <div className="catalog-products">
                {
                    productResponse?.content.map(product => (
                        <Link to="/products/1" key={product.id} ><ProductCard product={product} /></Link>
                    ))
                }
            </div>

        </div>
    );
}

export default Catalog;