import React, {Component} from "react";
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ExecuteService from "../../api/ExecuteServices";

export default class RoomList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            simulation: [],
            rooms: []
        }

        this.refreshSimulation = this.refreshSimulation.bind(this)
        this.userDifference = this.userDifference.bind(this)
        this.addUser = this.addUser.bind(this)
        this.removeUser = this.removeUser.bind(this)
        this.toggleAutoMode = this.toggleAutoMode.bind(this)
        this.callAuthorities = this.callAuthorities.bind(this)
        this.toggleDoor = this.toggleDoor.bind(this)

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


    addUser(roomIndex, user) {
        let userIndex = this.state.simulation.simulationUsers.indexOf(user)
        this.state.simulation.simulationUsers.splice(userIndex, 1)
        this.state.simulation.home.rooms[roomIndex].simulationUsers.push(user)

        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(response=> {
                alert("Intruder Alert!");
                this.callAuthorities()
                this.refreshSimulation();
            })
    }

    callAuthorities(){
        ExecuteService.callAuthorities()
            .then(()=>alert("The authorities have been called."))
            .catch(error => console.log(error))
    }

    toggleDoor(door, action){
        ExecuteService.toggleDoor(door, action)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    toggleWindow(window, action){
        ExecuteService.toggleWindow(window, action)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    switchRoomLight(light) {
        ExecuteService.toggleLight(light)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    toggleAutoMode() {
        ExecuteService.toggleAutoMode()
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    placeUser(room, user){
        ExecuteService.placeUser(room, user)
            .then(() => this.refreshSimulation())
            .catch(() => {
                alert("Intruder Alert!")
                this.refreshSimulation()
            })
    }

    removeUser(room, user){
        ExecuteService.removeUser(room, user)
            .then(response => {
                console.log(response)
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
                                    <th scope={"col"}>
                                                {this.state.simulation.lightsAutoMode ?
                                                    <button className={"btn-sm btn-success"}
                                                            onClick={() => this.toggleAutoMode()}>Lights</button> :
                                                    <button className={"btn-sm btn-danger"}
                                                            onClick={() => this.toggleAutoMode()}>Lights</button>}
                                    </th>
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
                                                    className={"btb btn-sm btn-dark"}>{room.windows.length}</button>)}
                                                position="right center" closeOnDocumentClick modal nested>
                                                {
                                                    <div>
                                                        <div className={"header"}>Modify Windows State</div>
                                                        <div className={"content"}>
                                                            The windows in this room are:
                                                            {room.windows.map((window) =>
                                                                <div>
                                                                    Window #{window.id}, which
                                                                    is {window.blocked ? "blocked " : "unblocked "}
                                                                    and {window.open ? "opened." : "closed."}
                                                                </div>)
                                                            }
                                                            <Popup trigger={<div className="menu-item"> Window </div>}
                                                                   position="top left" on="hover" closeOnDocumentClick
                                                                   mouseLeaveDelay={300} mouseEnterDelay={0}
                                                                   contentStyle={{padding: '0px', border: 'none'}}
                                                                   arrow={false}>
                                                                {room.windows.map((window, windowIndex) => (
                                                                    <div className={"menu-item"}>
                                                                        #{window.id}
                                                                        <button
                                                                            onClick={() => this.toggleWindow(window, "block")}>
                                                                            {window.blocked ? "Unblock?" : "Block?"}
                                                                        </button>
                                                                        {!window.blocked &&
                                                                        <button
                                                                            onClick={() => this.toggleWindow(window, "open")}>
                                                                            {window.open ? "Close?" : "Open?"}
                                                                        </button>}
                                                                    </div>
                                                                ))}
                                                            </Popup>
                                                        </div>
                                                    </div>}
                                            </Popup>
                                            : <div>{room.windows.length}</div>}</td>
                                        <td>{(room.simulationUsers.some(user => user.id === (this.state.simulation.loggedInUser.id)) || this.state.simulation.loggedInUser.privilege === 'Parent') ?
                                            <Popup
                                                trigger={(<button
                                                    className={"btb btn-sm btn-dark"}>{room.doors.length}</button>)}
                                                position="right center" closeOnDocumentClick modal nested>
                                                {
                                                    <div>
                                                        <div className={"header"}>Modify Door State</div>
                                                        <div className={"content"}>
                                                            The doors in this room are:
                                                            {room.doors.map((door) =>
                                                                <div>
                                                                    Door #{door.id}, which
                                                                    is {door.locked ? "locked " : "unlocked "}
                                                                    and {door.open ? "opened." : "closed."}
                                                                </div>)
                                                            }
                                                            <Popup trigger={<div className="menu-item"> Door </div>}
                                                                   position="top left" on="hover" closeOnDocumentClick
                                                                   mouseLeaveDelay={300} mouseEnterDelay={0}
                                                                   contentStyle={{padding: '0px', border: 'none'}}
                                                                   arrow={false}>
                                                                {room.doors.map((door, doorIndex) => (
                                                                    <div className={"menu-item"}>
                                                                        #{door.id}
                                                                        <button
                                                                            onClick={() => this.toggleDoor(door, "lock")}>
                                                                            {door.locked ? "Unlock?" : "Lock?"}
                                                                        </button>
                                                                        {!door.locked &&
                                                                        <button
                                                                            onClick={() => this.toggleDoor(door, "open")}>
                                                                            {door.open ? "Close?" : "Open?"}
                                                                        </button>}
                                                                    </div>
                                                                ))}
                                                            </Popup>
                                                        </div>
                                                    </div>}
                                            </Popup>
                                            : <div>{room.doors.length}</div>}</td>
                                        <td>
                                            <Popup trigger={<div className="menu-item"> 1 </div>}
                                                   position="top left" on="hover" closeOnDocumentClick
                                                   mouseLeaveDelay={300} mouseEnterDelay={0}
                                                   contentStyle={{padding: '0px', border: 'none'}}
                                                   arrow={false}>
                                                <div className={"menu-item"}>
                                                    <button
                                                        onClick={() => this.switchRoomLight(room.lights[0])}>
                                                        {room.lights[0].turnedOn ? "Switch Off?" : "Switch On?"}
                                                    </button>
                                                </div>
                                            </Popup>
                                        </td>
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
                                                                        onClick={() => this.removeUser(room, user)}>{user.name}</button>
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
                                                                        onClick={() => this.placeUser(room, user)}>{user.name}</button>
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
                                <tr>
                                    <th scope={"col"}>Entrance</th>
                                    <th scope={"col"}></th>
                                    <td scope={"col"}></td>
                                    <td scope={"col"}>
                                        <Popup trigger={<div className="menu-item"> 1 </div>}
                                               position="top left" on="hover" closeOnDocumentClick
                                               mouseLeaveDelay={300} mouseEnterDelay={0}
                                               contentStyle={{padding: '0px', border: 'none'}}
                                               arrow={false}>
                                            <div className={"menu-item"}>
                                                <button
                                                    onClick={() => this.toggleDoor(this.state.simulation.home.entranceDoor, "lock")}>
                                                    {this.state.simulation.home.entranceDoor.locked ? "Unlock?" : "Lock?"}
                                                </button>
                                                {!this.state.simulation.home.entranceDoor.locked &&
                                                <button onClick={() => this.toggleDoor(this.state.simulation.home.entranceDoor, "open")}>
                                                    {this.state.simulation.home.entranceDoor.open ? "Close?" : "Open?"}
                                                </button>}
                                            </div>
                                        </Popup>
                                    </td>
                                    <td scope={"col"}>
                                        <Popup trigger={<div className="menu-item"> 1 </div>}
                                               position="top left" on="hover" closeOnDocumentClick
                                               mouseLeaveDelay={300} mouseEnterDelay={0}
                                               contentStyle={{padding: '0px', border: 'none'}}
                                               arrow={false}>
                                            <div className={"menu-item"}>
                                                <button
                                                    onClick={() => this.switchRoomLight(this.state.simulation.home.entranceLight)}>
                                                    {this.state.simulation.home.entranceLight.turnedOn ? "Switch Off?" : "Switch On?"}
                                                </button>
                                            </div>
                                        </Popup>
                                    </td>
                                    <td scope={"col"}></td>
                                </tr>
                                <tr>
                                    <th scope={"col"}>Backyard</th>
                                    <th scope={"col"}></th>
                                    <td scope={"col"}></td>
                                    <td scope={"col"}>
                                        <Popup trigger={<div className="menu-item"> 1 </div>}
                                               position="top left" on="hover" closeOnDocumentClick
                                               mouseLeaveDelay={300} mouseEnterDelay={0}
                                               contentStyle={{padding: '0px', border: 'none'}}
                                               arrow={false}>
                                            <div className={"menu-item"}>
                                                <button
                                                    onClick={() => this.toggleDoor(this.state.simulation.home.backyardDoor, "lock")}>
                                                    {this.state.simulation.home.backyardDoor.locked ? "Unlock?" : "Lock?"}
                                                </button>
                                                {!this.state.simulation.home.backyardDoor.locked &&
                                                <button onClick={() => this.toggleDoor(this.state.simulation.home.backyardDoor, "open")}>
                                                    {this.state.simulation.home.backyardDoor.open ? "Close?" : "Open?"}
                                                </button>}
                                            </div>
                                        </Popup>
                                    </td>
                                    <td scope={"col"}>
                                        <Popup trigger={<div className="menu-item"> 1 </div>}
                                               position="top left" on="hover" closeOnDocumentClick
                                               mouseLeaveDelay={300} mouseEnterDelay={0}
                                               contentStyle={{padding: '0px', border: 'none'}}
                                               arrow={false}>
                                            <div className={"menu-item"}>
                                                <button
                                                    onClick={() => this.switchRoomLight(this.state.simulation.home.backyardLight)}>
                                                    {this.state.simulation.home.backyardLight.turnedOn ? "Switch Off?" : "Switch On?"}
                                                </button>
                                            </div>
                                        </Popup>
                                    </td>
                                    <td scope={"col"}></td>
                                </tr>
                                </tbody>
                            </table>)
                    )
                    : <div><h3>Please login to the simulation as one of the users first!</h3></div>}
            </div>
        )
    }
}