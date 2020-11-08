import React, {Component} from "react";
import ExecuteServices from "../../api/ExecuteServices";
import LightsFrame from "./LightsFrame";
import WindowsFrame from "./WindowsFrame";
import DoorFrame from "./DoorFrames";
import Typography from "@material-ui/core/Typography";


export default class CoreControlFrames extends Component{

    constructor(props) {
        super(props);
        this.state = {
            show: 'Lights'
        }
        this.changeFrame = this.changeFrame.bind(this)
    }

    changeFrame(frame){
        this.setState({
            show: frame
        })
    }

    render() {
        return (
            <div>
                <button className={"ml-4 btn btn-outline-info btn-sm"} onClick={()=>this.changeFrame("Lights")}> Lights </button>
                <button className={"btn btn-outline-info btn-sm"} onClick={()=>this.changeFrame("Windows")}>Windows</button>
                <button className={"btn btn-outline-info btn-sm"} onClick={()=>this.changeFrame("Doors")}>  Doors </button>
                {this.state.show==='Lights' && <LightsFrame controlParent = {this.props.controlParent}/>}
                {this.state.show==='Windows' && <WindowsFrame controlParent = {this.props.controlParent}/>}
                {this.state.show==='Doors' && <DoorFrame controlParent = {this.props.controlParent}/>}
            </div>
        )
    }

}
