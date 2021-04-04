import React from 'react';
import { Route, Switch } from 'react-router';
import './styles.scss';


const Users = () => {

    return (
        <div>
            <Switch>
                <Route path="/admin/users" exact>
                    <h1>Users list</h1>
                </Route>

                <Route path="/admin/users/create">
                    <h1>Create users</h1>
                </Route>

                <Route path="/admin/users/:usersId">
                    <h1>Update users</h1>
                </Route>

            </Switch>

        </div>
    );

}

export default Users;