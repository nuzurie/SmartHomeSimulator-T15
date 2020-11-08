import React, {Component} from "react";
import ExecuteServices from "../../api/ExecuteServices";


export default class WindowsFrame extends Component {

    constructor(props) {
        super(props);
        this.state = {
            windows: [],
        }

        this.toggleWindow = this.toggleWindow.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
        this.refresh()
    }

    toggleWindow(window, action){
        ExecuteServices.toggleWindow(window, action)
            .then(() => {
                this.refresh()
            })
            .catch(() => alert("This window is blocked and can't be controlled right now."))
    }

    refresh(){
        this.props.controlParent(Math.random())
        ExecuteServices.getWindowsForUsers()
            .then(response => {
                this.setState({
                    windows: response.data
                })
            })
            .catch(error => console.log(error))
    }

    render() {
        return (
            <div className={"container"}>
                <h3>Windows</h3>
                {this.state.windows.map(window =>
                    <div className={"container"}><span className={"mr-5"}>Window {window.id} in {window.name} is currently {window.open ? "open" : "closed"} and {window.blocked ? "blocked." : "unblocked."}</span>
                        <button className={window.open ? "btn btn-outline-danger btn-sm mr-5 " : "btn btn-outline-success btn-sm mr-5"} onClick={()=>this.toggleWindow(window, "open")}>
                            {window.open ? "Close" : "Open"}
                        </button>
                        <button className={"btn btn-outline-dark btn-sm mr-5 "} onClick={()=>this.toggleWindow(window, "block")}>
                            {window.blocked ? "Unblock" : "Block"}
                        </button>
                    </div>
                )}
            </div>
        )
    }

}
