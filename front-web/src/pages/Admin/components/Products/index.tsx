import React from 'react';
import { Route, Switch } from 'react-router';
import './styles.scss';


const Products = () => {

    return (
        <div>
            <Switch>
                <Route path="/admin/products" exact>
                    <h1>Produtos list</h1>
                </Route>

                <Route path="/admin/products/create">
                    <h1>Create product</h1>
                </Route>

                <Route path="/admin/products/:productId">
                    <h1>Update product</h1>
                </Route>

            </Switch>

        </div>
    );

}

export default Products;