import React, {Component} from "react";
import ExecuteServices from "../../api/ExecuteServices";


export default class LightsFrame extends Component {

    constructor(props) {
        super(props);
        this.state = {
            lights: [],
            autoMode: false
        }

        this.toggleLight = this.toggleLight.bind(this)
        this.toggleAutoMode = this.toggleAutoMode.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
       this.refresh()
    }

    toggleLight(light){
        ExecuteServices.toggleLight(light)
            .then(() => this.refresh())
            .catch(error => console.log(error))
    }

    toggleAutoMode() {
        ExecuteServices.toggleAutoMode()
            .then(() => {
                this.refresh()
            })
            .catch(error => console.log(error))
    }

    refresh(){
        this.props.controlParent(Math.random())
        ExecuteServices.getLightsForUsers()
            .then(response => {
                this.setState({
                    lights: response.data
                })
            })
            .catch(error => console.log(error))
        ExecuteServices.getSimulation()
            .then(response => {
                this.setState({
                    autoMode: response.data.lightsAutoMode
                })
            })
    }

    render() {
        return (
            <div className={"container"}>
                <h3>Lights</h3>
                {this.state.lights.map(light =>
                    <div className={"container"}><span className={"mr-5"}>Light {light.id.toString()} in {light.name} is currently {light.turnedOn ? "on." : "off."}</span>
                        <button className={light.turnedOn ? "btn btn-outline-danger btn-sm mr-5 " : "btn btn-outline-success btn-sm mr-5"} onClick={()=>this.toggleLight(light)}>
                            {light.turnedOn ? "Turn off" : "Turn on"}
                        </button>
                    </div>
                )}
                <button className={"btn btn-sm btn-primary"}
                        onClick={this.toggleAutoMode}>{this.state.autoMode ? "Turn auto-mode off" : "Turn auto-mode on"}</button>
            </div>
        )
    }

}
