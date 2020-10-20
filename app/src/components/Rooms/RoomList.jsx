import React, {Component} from "react";
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ExecuteService from "../../api/ExecuteServices";
import Button from "@material-ui/core/Button";
import {Link} from 'react-router-dom';

export default class RoomList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            simulation: [],
            rooms: []
        }

        this.removeUser = this.removeUser.bind(this)
        this.refreshSimulation = this.refreshSimulation.bind(this)
        this.userDifference = this.userDifference.bind(this)
        this.addUser = this.addUser.bind(this)
        this.blockWindow = this.blockWindow.bind(this)

    }

    componentDidMount() {
        this.refreshSimulation()
    }

    userDifference(roomUsers) {
        function comparer(otherArray) {
            return function (current) {
                return otherArray.filter(function (other) {
                    return other.id == current.id && other.name == current.name && other.privilege == current.privilege
                }).length == 0;
            }
        }
        let simulationUsers = this.state.simulation.simulationUsers;
        return simulationUsers.filter(comparer(roomUsers))
    }

    removeUser(room, user) {
        let removedUser = this.state.rooms[room].simulationUsers.splice(user, 1)
        this.state.simulation.simulationUsers.push(removedUser[0])

        ExecuteService.updateUserRooms(this.state.simulation)
            .then(response => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    addUser(roomIndex, user) {
        let userIndex = this.state.simulation.simulationUsers.indexOf(user)
        this.state.simulation.simulationUsers.splice(userIndex, 1)
        this.state.simulation.home.rooms[roomIndex].simulationUsers.push(user)

        ExecuteService.updateUserRooms(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))

    }

    blockWindow(roomIndex, windowIndex) {
        (this.state.simulation.home.rooms[roomIndex].window[windowIndex].blocked = !this.state.simulation.home.rooms[roomIndex].window[windowIndex].blocked)
        console.log(this.state.simulation)
        ExecuteService.updateUserRooms(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    refreshSimulation() {
        ExecuteService.getSimulation()
            .then(response => {
                this.setState({
                    simulation: response.data,
                    rooms: response.data.home.rooms,
                })
            })
            .catch(error => console.log(error))
    }

    render() {
        return (
            <div className={"container"}>
                {(this.state.simulation.loggedInUser != null) ?
                    (this.state.simulation.loggedInUser.privilege === 'Stranger' ?
                            <h3>Strangers have no permissions</h3> :
                            (<table className={"table table-striped table-dark"}>
                                <thead>
                                <tr>
                                    <th scope={"col"}>Name</th>
                                    <th scope={"col"}>Temperature</th>
                                    <th scope={"col"}>Window</th>
                                    <th scope={"col"}>Door</th>
                                    <th scope={"col"}>Light</th>
                                    <th scope={"col"}>Users</th>
                                    {/*<th scope={"col"}>Sensors</th>*/}
                                </tr>
                                </thead>
                                <tbody>
                                {this.state.rooms.map((room, roomIndex) =>
                                    <tr>
                                        <th scope={"col"}>{room.name}</th>
                                        <td>{room.temperature}</td>
                                        <td>{(room.simulationUsers.some(user => user.id === (this.state.simulation.loggedInUser.id)) || this.state.simulation.loggedInUser.privilege === 'Parent') ?
                                            <Popup
                                                trigger={(<button
                                                    className={"btb btn-sm btn-dark"}>{room.window.length}</button>)}
                                                position="right center" closeOnDocumentClick modal nested>
                                                {
                                                    <div>
                                                        <div className={"header"}>Modify Windows State</div>
                                                        <div className={"content"}>
                                                            To be able to block the windows, you must be have the parent
                                                            privilege.
                                                            <Popup trigger={<div className="menu-item"> Window </div>}
                                                                   position="top left" on="hover" closeOnDocumentClick
                                                                   mouseLeaveDelay={300} mouseEnterDelay={0}
                                                                   contentStyle={{padding: '0px', border: 'none'}}
                                                                   arrow={false}>
                                                                {room.window.map((window, windowIndex) => (
                                                                    <div className={"menu-item"}>
                                                                        <button
                                                                            onClick={() => this.blockWindow(roomIndex, windowIndex)}>
                                                                            {window.blocked ? "Unblock?" : "Block?"}
                                                                        </button>
                                                                        {!window.blocked &&
                                                                        <button>
                                                                            {window.open ? "Close?" : " Open?"}
                                                                        </button>}
                                                                    </div>
                                                                ))}
                                                            </Popup>
                                                        </div>
                                                    </div>}
                                            </Popup>
                                            : <div>{room.window.length}</div>}</td>
                                        <td>{room.door.length}</td>
                                        <td>{room.lights.length}</td>
                                        <td>
                                            <Popup trigger={(
                                                <button
                                                    className={"btb btn-sm btn-dark"}>{room.simulationUsers.length}</button>)}
                                                   position="center" closeOnDocumentClick modal nested>
                                                <div>
                                                    <div className={"header"}>Modify User Location</div>
                                                    <div className={"content"}>
                                                        To be able to add users to this room, you must first remove them
                                                        from their
                                                        rooms.
                                                        <Popup trigger={<div className="menu-item"> Remove </div>}
                                                               position="top left" on="click" closeOnDocumentClick
                                                               mouseLeaveDelay={300} mouseEnterDelay={0}
                                                               contentStyle={{padding: '0px', border: 'none'}}
                                                               arrow={false}>
                                                            {room.simulationUsers.map((user, userIndex) =>
                                                                <div className={"menu-item"}>
                                                                    <button
                                                                        onClick={() => this.removeUser(roomIndex, userIndex)}>{user.name}</button>
                                                                </div>
                                                            )}
                                                        </Popup>
                                                        <Popup trigger={<div className="menu-item"> Add </div>}
                                                               position="top left" on="click" closeOnDocumentClick
                                                               mouseLeaveDelay={300} mouseEnterDelay={0}
                                                               contentStyle={{padding: '0px', border: 'none'}}
                                                               arrow={false}>
                                                            {this.userDifference(room.simulationUsers).map((user, userIndex) =>
                                                                <div className={"menu-item"}>
                                                                    <button
                                                                        onClick={() => this.addUser(roomIndex, user)}>{user.name}</button>
                                                                </div>
                                                            )}
                                                        </Popup>
                                                    </div>
                                                </div>
                                            </Popup>
                                        </td>

                                        {/*<td>{room.sensors.length}</td>*/}
                                    </tr>
                                )}
                                </tbody>
                            </table>)
                    )
                    : <div><h3>Please login to the simulation as one of the users first!</h3></div>}
            </div>
        )

    }
}