import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import ProductPrice from '../ProductPrice';
import { ReactComponent as ArrowIcon } from 'core/assets/images/arrow.svg';
import ProductInfoLoader from '../../components/Loaders/ProductInfoLoader';
import ProductDescriptionLoader from '../../components/Loaders/ProductDescriptionLoader';
import './styles.scss';
import { makeRequest } from 'core/utils/request';
import { Product } from 'core/types/Product';


type ParamsType = {
    productId: string;
}

const ProductDetails = () => {

    const { productId } = useParams<ParamsType>();
    const [product, setProduct] = useState<Product>();
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {

        setIsLoading(true);
        makeRequest({ url: `/produtcs/${productId}` })
            .then(response => setProduct(response.data))
            .finally(() => {
                setIsLoading(false);
            });
    }, [productId]);


    return (
        <div className="product-details-container">
            <div className="card-base border-radios-20 product-datails">
                <Link to="/products" className="product-datails-goback">
                    <ArrowIcon className="icon-goback" />
                    <h1 className="text-goback">Voltar</h1>
                </Link>
                <div className="row">
                    <div className="col-6 pr-5">
                        {isLoading ? <ProductInfoLoader /> : (
                            <>
                                <div className="product-details-card border-radios-20 text-center">
                                    {<img src={product?.imgUrl} alt={product?.name} className="product-details-image" />}
                                </div>
                                <h1 className="product-datails-name">
                                    {product?.name}
                                </h1>
                                {product?.price && <ProductPrice price={product?.price} />}
                            </>
                        )}
                    </div>

                    <div className="col-6 product-details-card border-radios-20">
                        {
                            isLoading ? <ProductDescriptionLoader /> : (
                                <>
                                    <h1 className="product-description-title">Descri????o do Produto</h1>
                                    <p className="product-description-text">
                                        {product?.description}
                                    </p>
                                </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetails;