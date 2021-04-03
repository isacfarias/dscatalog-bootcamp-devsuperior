import React from 'react';
import ProductPrice from '../ProductPrice';

import './styles.scss';
import { type } from 'node:os';
import { Product } from '../../../../core/types/Product';

type Props = {
    product: Product;
}

const ProductCard = ({ product }: Props) => (
    <div className="card-base border-radios-10 product-card">
        <img src={product.imgUrl} alt={product.name} className="product-card-image" />
        
        <div className="product-info">
            <h6 className="product-name">
                {product.name}
            </h6>

            <ProductPrice price={product.price}/>
            
        </div>
    </div>


);

export default ProductCard;