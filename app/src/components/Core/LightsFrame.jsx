import React, {Component} from "react";
import ExecuteServices from "../../api/ExecuteServices";


export default class LightsFrame extends Component {

    constructor(props) {
        super(props);
        this.state = {
            lights: [],
        }

        this.toggleLight = this.toggleLight.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
       this.refresh()
    }

    toggleLight(light){
        ExecuteServices.toggleLight(light)
            .then(this.refresh())
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
    }

    render() {
        return (
            <div className={"container"}>
                <h3>Lights</h3>
                {this.state.lights.map(light =>
                    <div className={"container"}><span className={"mr-5"}>Light {light.id} in {light.name} is currently {light.turnedOn ? "on." : "off."}</span>
                        <button className={light.turnedOn ? "btn btn-outline-danger btn-sm mr-5 " : "btn btn-outline-success btn-sm mr-5"} onClick={()=>this.toggleLight(light)}>
                            {light.turnedOn ? "Turn off" : "Turn on"}
                        </button>
                    </div>
                )}
            </div>
        )
    }

}
