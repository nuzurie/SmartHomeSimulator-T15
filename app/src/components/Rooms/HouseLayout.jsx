import React, {Component} from "react";
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ExecuteService from "../../api/ExecuteServices";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faWindowMaximize, faWindowClose, faDoorOpen, faDoorClosed, faLightbulb, faMoon, faUser
} from "@fortawesome/free-solid-svg-icons";

export default class HouseLayoutUI extends Component {

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
                                    <th scope={"col"}>Lights</th>
                                    <th scope={"col"}>Users</th>
                                    {/*<th scope={"col"}>Sensors</th>*/}
                                </tr>
                                </thead>
                                <tbody>
                                {this.state.rooms.map((room, roomIndex) =>
                                    <tr>
                                        <th scope={"col"}>{room.name}</th>
                                        <td>{room.temperature}</td>
                                        <td>
                                            {room.windows.map((window) =>
                                                    window.open ? <span className={"mr-2"}><FontAwesomeIcon  icon={faWindowMaximize}></FontAwesomeIcon></span> : <span className={"mr-2"}><FontAwesomeIcon icon={faWindowClose}></FontAwesomeIcon></span>
                                                )}
                                        </td>
                                        <td>
                                            {room.doors.map((door) =>
                                                door.open ? <span className={"mr-2"}><FontAwesomeIcon  icon={faDoorOpen}></FontAwesomeIcon></span> : <span className={"mr-2"}><FontAwesomeIcon icon={faDoorClosed}></FontAwesomeIcon></span>
                                            )}
                                        </td>
                                        <td>
                                            {room.lights[0].turnedOn ? <span><FontAwesomeIcon  icon={faLightbulb}></FontAwesomeIcon></span> : <span className={"mr-2"}><FontAwesomeIcon icon={faMoon}></FontAwesomeIcon></span>}
                                        </td>
                                        <td>
                                            {room.simulationUsers.map(() =>
                                                <span className={"mr-2"}><FontAwesomeIcon  icon={faUser}></FontAwesomeIcon></span>
                                            )}
                                        </td>

                                    </tr>
                                )}
                                <tr>
                                    <th scope={"col"}>Entrance</th>
                                    <th scope={"col"}></th>
                                    <td scope={"col"}></td>
                                    <td scope={"col"}>
                                        {this.state.simulation.home.entranceDoor.locked ? <span className={"mr-2"}><FontAwesomeIcon  icon={faDoorOpen}></FontAwesomeIcon></span> : <span className={"mr-2"}><FontAwesomeIcon icon={faDoorClosed}></FontAwesomeIcon></span>}
                                    </td>
                                    <td scope={"col"}>
                                        {this.state.simulation.home.entranceLight.turnedOn ? <span><FontAwesomeIcon  icon={faLightbulb}></FontAwesomeIcon></span> : <span className={"mr-2"}><FontAwesomeIcon icon={faMoon}></FontAwesomeIcon></span>}
                                    </td>
                                    <td scope={"col"}></td>
                                </tr>
                                <tr>
                                    <th scope={"col"}>Backyard</th>
                                    <th scope={"col"}></th>
                                    <td scope={"col"}></td>
                                    <td scope={"col"}>
                                            <div className={"menu-item"}>
                                                {this.state.simulation.home.backyardDoor.locked ? <span className={"mr-2"}><FontAwesomeIcon  icon={faDoorOpen}></FontAwesomeIcon></span> : <span className={"mr-2"}><FontAwesomeIcon icon={faDoorClosed}></FontAwesomeIcon></span>}
                                            </div>
                                    </td>
                                    <td scope={"col"}>
                                        {this.state.simulation.home.backyardLight.turnedOn ? <span><FontAwesomeIcon  icon={faLightbulb}></FontAwesomeIcon></span> : <span className={"mr-2"}><FontAwesomeIcon icon={faMoon}></FontAwesomeIcon></span>}
                                    <td scope={"col"}></td>
                                    </td>
                                </tr>
                                </tbody>
                            </table>)
                    )
                    : <div><h3>Please login to the simulation as one of the users first!</h3></div>}
            </div>
        )
    }
}