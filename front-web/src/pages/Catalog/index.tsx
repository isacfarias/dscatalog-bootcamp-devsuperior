import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ProductResponse } from 'core/types/Product';
import { makeRequest } from 'core/utils/request';
import ProductCard from './components/ProductCard';
import ProductCardLoader from './components/Loaders/ProductCardLoader';
import './styles.scss';
import Pagination from 'core/components/Pagination';

const Catalog = () => {

    const [productResponse, setProductResponse] = useState<ProductResponse>();
    const [isLoading, setIsLoading] = useState(false);
    const [activePage, setActivePage] = useState(0);
    const [linesPerPage, setLinesPerPage] = useState(12);



    useEffect(() => {

        const params = {
            page: activePage,
            linesPerPage: linesPerPage
        };

        setIsLoading(true);
        makeRequest({ url: '/produtcs/', params })
            .then(response => setProductResponse(response.data))
            .finally(() => {
                setIsLoading(false);
            });

    }, [activePage, linesPerPage]);

    return (
        <div className="catalog-container ">
            <h1 className="catalog-title">
                Catálogo de produtos
        </h1>

            <div className="catalog-products">

                {isLoading ? <ProductCardLoader listPerPage={linesPerPage} /> : (
                    productResponse?.content.map(product => (
                        <Link to={`/products/${product.id}`} key={product.id} >
                            <ProductCard product={product} />
                        </Link>
                    ))
                )}
            </div>

            { productResponse && (
                <Pagination 
                    totalPages={productResponse.totalPages}
                    activePage={activePage}
                    onChange={page => setActivePage(page)} />
            )}

        </div>
    );
}

export default Catalog;