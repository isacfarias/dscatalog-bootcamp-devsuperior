import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import ProductPrice from '../ProductPrice';
import { ReactComponent as ArrowIcon } from '../../../../core/assets/images/arrow.svg';
import { ReactComponent as ProductImage } from '../../../../core/assets/images/product.svg';
import './styles.scss';
import { makeRequest } from '../../../../core/utils/request';
import { Product } from '../../../../core/types/Product';


type ParamsType = {
    productId: string;
}

const ProductDetails = () => {

    const { productId } = useParams<ParamsType>();

    const [product, setProduct] = useState<Product>();
    
    useEffect(() => {
            
        makeRequest({ url:`/produtcs/${productId}`})
        .then(response => setProduct(response.data));

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
                        <div className="product-details-card border-radios-20 text-center">
                           { product?.imgUrl &&  <img src={product?.imgUrl} alt={product?.name} className="product-details-image" /> }
                        </div>
                        <h1 className="product-datails-name">
                            {product?.name}
                        </h1>
                        
                        { product?.price && <ProductPrice price={product?.price} /> }
                        
                    </div>

                    <div className="col-6 product-details-card border-radios-20">
                        <h1 className="product-description-title">Descrição do Produto</h1>
                        <p className="product-description-text">
                            { product?.description }
                        </p>
                    </div>

                </div>

            </div>
        </div>
    );
};

export default ProductDetails;