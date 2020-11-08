import React, {Component} from "react";
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ExecuteService from "../../api/ExecuteServices";

export default class AwayMode extends Component {

    constructor(props) {
        super(props);

        this.state = {
            simulation: []
        }
        this.refreshState = this.refreshState.bind(this)
    }

    componentDidMount() {
        this.refreshState()
    }

    refreshState() {
        ExecuteService.getSimulation()
            .then(response => this.setState({
                simulation: response.data
            }))
            .catch(reason => console.log(reason))
    }

    toggleAwayMode() {
        ExecuteService.toggleAwayMode()
            .then(response => {
                if (response.data.length > 0)
                    alert("The following windows were blocked and can't be accessed: " + response.data)
                this.refreshState()
            })
            .catch(()=>alert("Away Mode can't be set while people inside. Please evacuate first."))
    }

    render() {
        return (
            <div className={"container"}>
                <button className={"btn btn-primary"}
                        onClick={() => this.toggleAwayMode()}>{this.state.simulation.awayMode ? "Turn Off Away Mode" : "Turn On Away Mode"}</button>
            </div>
        )
    }
}