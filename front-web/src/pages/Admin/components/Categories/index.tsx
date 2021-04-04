import React from 'react';
import { Route, Switch } from 'react-router';
import './styles.scss';


const Categories = () => {

    return (
        <div>
            <Switch>
                <Route path="/admin/categories" exact>
                    <h1>Categories list</h1>
                </Route>

                <Route path="/admin/categories/create">
                    <h1>Create category</h1>
                </Route>

                <Route path="/admin/categories/:categoryId">
                    <h1>Update category</h1>
                </Route>

            </Switch>

        </div>
    );
}

export default Categories;