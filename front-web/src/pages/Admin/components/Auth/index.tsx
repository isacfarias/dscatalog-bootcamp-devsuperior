import React from 'react';
import { Route, Switch } from 'react-router';
import './styles.scss';


const Auth = () => {

    return (
        <div>
            <Switch>
                <Route path="/auth/login">
                    <h1>Logar</h1>
                </Route>

                <Route path="/auth/login">
                    <h1>Registrar</h1>
                </Route>

                <Route path="/auth/recover">
                    <h1>recuperar</h1>
                </Route>

            </Switch>

        </div>
    );
}

export default Auth;