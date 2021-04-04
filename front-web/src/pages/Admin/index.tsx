import React from 'react';
import { Route, Switch } from 'react-router';
import Navbar from './components/Navbar';
import './styles.scss';

const Admin = () => (
    <div className="admin-container">
        <Navbar />

        <div className="admin-content">
            <Switch>

                <Route path="/admin/products">
                    <h1>Produtos</h1>
                </Route>

                <Route path="/admin/categories">
                    <h1>Categorias</h1>
                </Route>

                <Route path="/admin/users">
                    <h1>Users</h1>
                </Route>

                <Route path="/auth">
                    <h1>Auth</h1>
                </Route>

            </Switch>
        </div>
    </div>
);

export default Admin;