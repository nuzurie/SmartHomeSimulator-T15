import React, {Component} from "react";

import ExecuteService from "../../api/ExecuteServices";
import {Link as RouterLink} from 'react-router-dom';
import UserForm from "./Forms/UserForm";

class UserList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            users: []
        }

        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this)
        this.handleError = this.handleError.bind(this)
        this.deleteUser = this.deleteUser.bind(this)
        this.refreshContent = this.refreshContent.bind(this)
        this.loginUser = this.loginUser.bind(this)
    }

    componentDidMount() {
        this.refreshContent()
    }

    render() {
        return (
            <div className={"container"}>
                <h3>Current Users</h3>
                <div className={"container"}>
                    <table className={"table"}>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Privilege</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.users.map(
                                (user) =>
                                    <tr key={user.id}>
                                        <td key={user.id}>{user.id}</td>
                                        <td key={user.name}>{user.name}</td>
                                        <td key={user.privilege}>{user.privilege}</td>
                                        <button className={"btn btn-outline-danger btn-sm"} onClick={()=> this.deleteUser(user)}>Delete</button>
                                        <RouterLink to={`/user/${user.id}`}><button className={"btn btn-outline-warning btn-sm"}>Update</button></RouterLink>
                                        <button className={"btn btn-outline-success btn-sm"} onClick={()=> this.loginUser(user)}>Login</button>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
                <button className={"btn btn-sm btn-primary"} onClick={ExecuteService.saveUsers}>Save to file</button>
                <button className={"ml-3 btn btn-sm btn-primary"} onClick={ExecuteService.loadUsers}>Load from file</button>
                <UserForm/>
            </div>
        )
    }

    handleSuccessfulResponse(response) {
        this.setState({users: response.data})
    }

    handleError(error) {
        console.log(error)
    }

    deleteUser(user){
        ExecuteService.deleteUser(user)
            .then(
                response => {
                    this.setState({message:'Successfully deleted'})
                    this.refreshContent()
                }
            )
    }

    loginUser(user){
        ExecuteService.loginSimulationUser(user)
            .then(response => console.log(response))
            .catch(error => console.log(error))

        sessionStorage.setItem('authenticated', user.id)
        sessionStorage.setItem('authenticatedName', user.name)
    }

    refreshContent(user) {
        ExecuteService.getAllUsers()
            .then(response => this.handleSuccessfulResponse(response))
            .catch(error => this.handleError(error))
    }
}

export default UserList;
