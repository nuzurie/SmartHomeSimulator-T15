import React, {Component} from "react";
import ExecuteServices from "../../api/ExecuteServices";


export default class DoorFrame extends Component {

    constructor(props) {
        super(props);
        this.state = {
            doors: [],
        }

        this.toggleDoor = this.toggleDoor.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
        this.refresh()
    }

    toggleDoor(door, action){
        ExecuteServices.toggleDoor(door, action)
            .then(() => {
                this.refresh()
            })
            .catch(() => alert("This door is locked and can't be accessed right now."))
    }

    refresh(){
        this.props.controlParent(Math.random())
        ExecuteServices.getDoorsForUsers()
            .then(response => {
                this.setState({
                    doors: response.data
                })
            })
            .catch(error => console.log(error))
    }

    render() {
        return (
            <div className={"container"}>
                <h3>Doors</h3>
                {this.state.doors.map(door =>
                    <div className={"container"}><span className={"mr-5"}>Door {door.id.toString()} in {door.name} is currently {door.open ? "open" : "closed"} and {door.locked ? "locked." : "unlocked."}</span>
                        <button className={door.open ? "btn btn-outline-danger btn-sm mr-5 " : "btn btn-outline-success btn-sm mr-5"} onClick={()=>this.toggleDoor(door, "open")}>
                            {door.open ? "Close" : "Open"}
                        </button>
                        <button className={"btn btn btn-outline-dark btn-sm mr-5 "} onClick={()=>this.toggleDoor(door, "lock")}>
                            {door.locked ? "Unlock" : "Lock"}
                        </button>
                    </div>
                )}
            </div>
        )
    }

}
