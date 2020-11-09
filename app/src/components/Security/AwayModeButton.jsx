import React, {Component} from "react";
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ExecuteService from "../../api/ExecuteServices";
import AwayModeLightsTimerForm from "../Forms/AwayModeLightsTimerForm";
import LightsAwayMode from "../Forms/LightsAwayMode";

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
            .catch(() => alert("Away Mode can't be set while people inside. Please evacuate first."))
    }

    render() {
        return (
            <>
                {this.state.simulation.loggedInUser ? (this.state.simulation.loggedInUser.privilege == 'Parent' || this.state.simulation.loggedInUser.privilege == 'parent') &&
                    <div className={"container"}>
                        <button className={"btn btn-primary"}
                                onClick={() => this.toggleAwayMode()}>{this.state.simulation.awayMode ? "Turn Off Away Mode" : "Turn On Away Mode"}</button>
                    </div>
                    : <div><h4>Only parents have permission to access security settings.</h4></div>}
                {this.state.simulation.loggedInUser ? (this.state.simulation.loggedInUser.privilege == 'Parent' || this.state.simulation.loggedInUser.privilege == 'parent') &&
                    <AwayModeLightsTimerForm/>
                    : <div></div>}
                {this.state.simulation.loggedInUser ? (this.state.simulation.loggedInUser.privilege == 'Parent' || this.state.simulation.loggedInUser.privilege == 'parent') &&
                    <LightsAwayMode/>
                    : <div></div>}
            </>
        )
    }
}