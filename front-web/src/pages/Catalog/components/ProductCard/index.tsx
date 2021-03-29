import React from 'react';
import { ReactComponent as ProdcuctImage } from '../../../../core/assets/images/product.svg'
import './styles.scss';

const ProductCard = () => (
    <div className="card-base border-radios-10 product-card">
        <ProdcuctImage />
        <div className="product-info">
            <h6 className="product-name">
                Computador Desktop - Intel Core i7
            </h6>
            <div className="prodct-price-container">
                <span className="product-currency">R$</span>
                <h3 className="product-price">2779,00</h3>
            </div>
        </div>
    </div>


);

export default ProductCard;