import React from 'react';
import { useParams } from 'react-router';
import { Link } from 'react-router-dom';
import { ReactComponent as ArrowIcon } from '../../../../core/assets/images/arrow.svg';
import './styles.scss';


type ParamsType = {
    productId: string;
}

const ProductDetails = () =>  {

    const { productId } = useParams<ParamsType>();

    return (
        <div className="product-details-container">
            <div className="card-base border-radios-20 product-datails">
                <Link to="/produts" className="product-datails-goback">
                    <ArrowIcon className="icon-goback" />
                    <h1 className="text-goback">Voltar</h1>
                </Link>
            </div>
        </div>
    );
};

export default ProductDetails;