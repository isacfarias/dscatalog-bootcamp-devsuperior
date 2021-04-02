import React from 'react';
import ProductPrice from '../ProductPrice';
import { ReactComponent as ProdcuctImage } from '../../../../core/assets/images/product.svg'
import './styles.scss';



const ProductCard = () => (
    <div className="card-base border-radios-10 product-card">
        <ProdcuctImage />
        <div className="product-info">
            <h6 className="product-name">
                Computador Desktop - Intel Core i7
            </h6>

            <ProductPrice price="2.779,00"/>
            
        </div>
    </div>


);

export default ProductCard;