import React, {Component} from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import PeopleIcon from '@material-ui/icons/People';
import AssignmentIcon from '@material-ui/icons/Assignment';
import ExecuteServices from "../../api/ExecuteServices";
import Divider from "@material-ui/core/Divider";

export default class SecondaryListItems extends Component {
    constructor(props) {
        super(props);
        this.state = {
            simulation: [],
            random: this.props.random
        }
    }

    componentDidMount() {
        this.interval = setInterval(() => ExecuteServices.getSimulation()
            .then(response => {
                this.setState({
                    simulation: response.data,
                })
            })
            .catch(error => console.log(error)), 1000);

    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }


    render() {
        return (
            <div>
                <div>
                    <ListItem button>
                        <ListItemIcon>
                            <PeopleIcon/>
                        </ListItemIcon>
                        <div>
                        <ListItemText primary={`Name = ${this.state.simulation.loggedInUser ? this.state.simulation.loggedInUser.name : "No user"}`}/>
                            <p><u>{this.state.simulation.loggedInUser ? this.state.simulation.loggedInUser.privilege : "No user"}</u></p>
                        </div>
                    </ListItem>
                </div>
                <Divider/>
                <div>
                    <ListSubheader inset>Simulation conditions</ListSubheader>
                    <ListItem button>
                        <ListItemIcon>
                            <AssignmentIcon/>
                        </ListItemIcon>
                        <ListItemText primary={`Temperature: ${this.state.simulation.temperature}`}/>
                    </ListItem>
                    <ListItem button>
                        <ListItemIcon>
                            <AssignmentIcon/>
                        </ListItemIcon>
                        <ListItemText primary={`Time: ${this.state.simulation.time}`}/>
                    </ListItem>
                    <ListItem button>
                        <ListItemIcon>
                            <AssignmentIcon/>
                        </ListItemIcon>
                        <ListItemText primary={`Date: ${this.state.simulation.date}`}/>
                    </ListItem>
                </div>
            </div>
        );
    }
}
