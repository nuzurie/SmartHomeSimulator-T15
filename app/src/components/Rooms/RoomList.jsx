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

        this.removeUser = this.removeUser.bind(this)
        this.refreshSimulation = this.refreshSimulation.bind(this)
        this.userDifference = this.userDifference.bind(this)
        this.addUser = this.addUser.bind(this)
        this.blockWindow = this.blockWindow.bind(this)
        this.openWindow = this.openWindow.bind(this)
        this.openOutsideDoor = this.openOutsideDoor.bind(this)
        this.lockOutsideDoor = this.lockOutsideDoor.bind(this)
        this.switchOutsideLight = this.switchOutsideLight.bind(this)
        this.lockDoor = this.lockDoor.bind(this)
        this.openDoor = this.openDoor.bind(this)

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

        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(response => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    addUser(roomIndex, user) {
        let userIndex = this.state.simulation.simulationUsers.indexOf(user)
        this.state.simulation.simulationUsers.splice(userIndex, 1)
        this.state.simulation.home.rooms[roomIndex].simulationUsers.push(user)

        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))

    }

    blockWindow(roomIndex, windowIndex) {
        (this.state.simulation.home.rooms[roomIndex].windows[windowIndex].blocked = !this.state.simulation.home.rooms[roomIndex].windows[windowIndex].blocked)
        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    openWindow(roomIndex, windowIndex) {
        (this.state.simulation.home.rooms[roomIndex].windows[windowIndex].open = !this.state.simulation.home.rooms[roomIndex].windows[windowIndex].open)
        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    openOutsideDoor(door){
        if (door === "entrance"){
            this.state.simulation.home.entranceDoor.open = !this.state.simulation.home.entranceDoor.open
        }
        else{
            this.state.simulation.home.backyardDoor.open = !this.state.simulation.home.backyardDoor.open
        }

        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    lockOutsideDoor(door){
        if (door === "entrance"){
            this.state.simulation.home.entranceDoor.locked = !this.state.simulation.home.entranceDoor.locked
        }
        else{
            this.state.simulation.home.backyardDoor.locked = !this.state.simulation.home.backyardDoor.locked
        }

        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    lockDoor(roomIndex, doorIndex){
        (this.state.simulation.home.rooms[roomIndex].doors[doorIndex].locked = !this.state.simulation.home.rooms[roomIndex].doors[doorIndex].locked)
        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    openDoor(roomIndex, doorIndex){
        (this.state.simulation.home.rooms[roomIndex].doors[doorIndex].open = !this.state.simulation.home.rooms[roomIndex].doors[doorIndex].open)
        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    switchOutsideLight(light){
        if (light === "entrance"){
            this.state.simulation.home.entranceLight.turnedOn = !this.state.simulation.home.entranceLight.turnedOn
        }
        else{
            this.state.simulation.home.backyardLight.turnedOn = !this.state.simulation.home.backyardLight.turnedOn
        }

        ExecuteService.updateSimulationDetails(this.state.simulation)
            .then(() => {
                this.refreshSimulation()
            })
            .catch(error => console.log(error))
    }

    switchRoomLight(roomIndex){
        this.state.simulation.home.rooms[roomIndex].lights[0].turnedOn = !this.state.simulation.home.rooms[roomIndex].lights[0].turnedOn

        ExecuteService.updateSimulationDetails(this.state.simulation)
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
                                                    className={"btb btn-sm btn-dark"}>{room.windows.length}</button>)}
                                                position="right center" closeOnDocumentClick modal nested>
                                                {
                                                    <div>
                                                        <div className={"header"}>Modify Windows State</div>
                                                        <div className={"content"}>
                                                            The windows in this room are:
                                                            {room.windows.map((window) =>
                                                                <div>
                                                                    Window #{window.id}, which is {window.blocked? "blocked ":"unblocked "}
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
                                                                            onClick={() => this.blockWindow(roomIndex, windowIndex)}>
                                                                            {window.blocked ? "Unblock?" : "Block?"}
                                                                        </button>
                                                                        {!window.blocked &&
                                                                        <button onClick={() => this.openWindow(roomIndex, windowIndex)}>
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
                                                                    Door #{door.id}, which is {door.locked? "locked ":"unlocked "}
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
                                                                            onClick={() => this.lockDoor(roomIndex, doorIndex)}>
                                                                            {door.locked ? "Unlock?" : "Lock?"}
                                                                        </button>
                                                                        {!door.locked &&
                                                                        <button onClick={() => this.openDoor(roomIndex, doorIndex)}>
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
                                                        onClick={() => this.switchRoomLight(roomIndex)}>
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
                                                        onClick={() => this.lockOutsideDoor("entrance")}>
                                                        {this.state.simulation.home.entranceDoor.locked ? "Unlock?" : "Lock?"}
                                                    </button>
                                                    {!this.state.simulation.home.entranceDoor.locked &&
                                                    <button onClick={() => this.openOutsideDoor("entrance")}>
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
                                                    onClick={() => this.switchOutsideLight("entrance")}>
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
                                                    onClick={() => this.lockOutsideDoor("backyard")}>
                                                    {this.state.simulation.home.backyardDoor.locked ? "Unlock?" : "Lock?"}
                                                </button>
                                                {!this.state.simulation.home.backyardDoor.locked &&
                                                <button onClick={() => this.openOutsideDoor("backyard")}>
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
                                                    onClick={() => this.switchOutsideLight("backyard")}>
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