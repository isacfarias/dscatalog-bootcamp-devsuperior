import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ProductResponse } from 'core/types/Product';
import { makeRequest } from 'core/utils/request';
import ProductCard from './components/ProductCard';
import ProductCardLoader from './components/Loaders/ProductCardLoader';
import './styles.scss';

const Catalog = () => {
    // quando o componente iniciar o componente deve buscar a lista
    // quando a lista de produtos estiver disponivel,
    const [productResponse, setProductResponse] = useState<ProductResponse>();
    const [isLoading, setIsLoading] = useState(false);

    // popular um estado no componente, e listar os produtos dinamicamente
    useEffect(() => {

        const params = {
            page: 0,
            linesPerPage: 5
        };

        setIsLoading(true);
        makeRequest({ url: '/produtcs/', params })
            .then(response => setProductResponse(response.data))
            .finally(() => {
                setIsLoading(false);
            });

    }, []);

    return (
        <div className="catalog-container ">
            <h1 className="catalog-title">
                Catálogo de produtos
        </h1>

            <div className="catalog-products">

                { isLoading ? <ProductCardLoader listPerPage={5} /> : (
                    productResponse?.content.map(product => (
                        <Link to={`/products/${product.id}`} key={product.id} >
                            <ProductCard product={product} />
                        </Link>
                    ))
                )}
            </div>

        </div>
    );
}

export default Catalog;