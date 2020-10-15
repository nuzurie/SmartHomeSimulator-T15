import React, {Component} from "react";
import ExecuteService from "../../../api/ExecuteServices";import UserForm from "./UserForm";

export default class UserUpdate extends Component {
    constructor(props) {
        super(props);

        this.state = {
            user: null
        }
    }

    componentDidMount() {
        console.log(this.props)
        ExecuteService.getUser(this.props.id)
            .then(response => {
                console.log(response)
                this.setState({
                    user: response.data
                })
            })
            .catch(error => console.log(error))
    }

    render() {
        return (
            <div className={"container"}>
                <h3>Current Users</h3>
                <div className={"container"}>
                    <table className={"table"}>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Privilege</th>
                        </tr>
                        </thead>
                        <tbody>


                        {this.state.user && <tr key={this.state.user.id}>
                            <td key={this.state.user.name}>{this.state.user.name}</td>
                            <td key={this.state.user.privilege}>{this.state.user.privilege}</td>
                        </tr>}

                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}