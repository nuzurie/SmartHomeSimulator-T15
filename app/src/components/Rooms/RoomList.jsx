import React, {Component} from "react";
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ExecuteService from "../../api/ExecuteServices";
import UserList from "../Users/UserList";

class WindowPopup extends Component {
    render() {
        return (
            <>
                <Popup trigger={open => (<button className="button">Trigger - {open ? 'Opened' : 'Closed'}</button>)}
                       position="right center" closeOnDocumentClick modal>
                    <div>
                        <UserList/>
                    </div>
                </Popup>
            </>
        )
    }
}

export default class RoomList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            simulation: [],
            rooms: []
        }

        this.removeUser = this.removeUser.bind(this)
        this.refreshSimulation = this.refreshSimulation.bind(this)
    }

    componentDidMount() {
        this.refreshSimulation()
    }

    removeUser(room, user){
        let removedUser = this.state.rooms[room].simulationUsers.splice(user, 1)
        console.log(removedUser, this.state.rooms)
        this.state.simulation.simulationUsers.push(removedUser[0])
        console.log(this.state.simulation)

        ExecuteService.updateUserRooms(this.state.simulation)
            .then(response => {
                console.log("The updated Simulation: ", response)
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    refreshSimulation(){
        ExecuteService.getSimulation()
            .then(response => {
                this.setState({
                    simulation: response.data,
                    rooms: response.data.home.rooms,
                })})
            .catch(error => console.log(error))
        console.log("the new state is: ", this.state.simulation)
    }

    render() {
        return (
            <div className={"container"}>
                <table className={"table table-striped table-dark"}>
                    <thead>
                    <tr>
                        <th scope={"col"}>Name</th>
                        <th scope={"col"}>Temperature</th>
                        <th scope={"col"}>Window</th>
                        <th scope={"col"}>Door</th>
                        <th scope={"col"}>Light</th>
                        <th scope={"col"}>Users</th>
                        <th scope={"col"}>Sensors</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.rooms.map((room, roomIndex) =>
                        <tr>
                            {console.log(room)}
                            <th scope={"col"}>{room.name}</th>
                            <td>{room.temperature}</td>
                            <td><Popup
                                trigger={(<button className={"btb btn-sm btn-dark"}>{room.window.length}</button>)}
                                position="right center" closeOnDocumentClick> <span>Done</span></Popup></td>
                            <td>{room.door.length}</td>
                            <td>{room.lights.length}</td>
                            <td>
                                <Popup trigger={(
                                    <button className={"btb btn-sm btn-dark"}>{room.simulationUsers.length}</button>)}
                                       position="right center" closeOnDocumentClick modal nested>
                                    <div>
                                        <div className={"header"}>Modify User Location</div>
                                        <div className={"content"}>
                                            To be able to add users to this room, you must first remove them from their
                                            rooms.
                                            <Popup trigger={<div className="menu-item"> Remove </div>}
                                                   position="top" on="hover" closeOnDocumentClick
                                                   mouseLeaveDelay={300} mouseEnterDelay={0}
                                                   contentStyle={{padding: '0px', border: 'none'}} arrow={false}>
                                                {room.simulationUsers.map((user, userIndex) =>
                                                    <div className={"menu-item"}>
                                                        <button onClick={()=>this.removeUser(roomIndex, userIndex)}>{user.name}</button>
                                                    </div>
                                                )}
                                            </Popup>
                                        </div>
                                    </div>
                                </Popup>
                            </td>

                            <td>{room.sensors.length}</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        )

    }
}