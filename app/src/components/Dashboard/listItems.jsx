import React, {Component, useEffect, useState} from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import DashboardIcon from '@material-ui/icons/Dashboard';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import PeopleIcon from '@material-ui/icons/People';
import BarChartIcon from '@material-ui/icons/BarChart';
import LayersIcon from '@material-ui/icons/Layers';
import AssignmentIcon from '@material-ui/icons/Assignment';
import ExecuteServices from "../../api/ExecuteServices";
import Divider from "@material-ui/core/Divider";

export default class SecondaryListItems extends Component {
    constructor(props) {
        super(props);
        this.state = {
            simulation: []
        }
    }

    componentDidMount() {
        console.log(this.state.simulation)
        ExecuteServices.getSimulation()
            .then(response => {
                console.log(response, "1")
                this.setState({
                    simulation: response.data,
                })
            })
            .catch(error => console.log(error))
    }

    render() {
        return (
            <div>
                <div>
                    <ListItem button>
                        <ListItemIcon>
                            <PeopleIcon/>
                        </ListItemIcon>
                        {console.log(this.state.simulation)}
                        {/*IS THIS UNDEFINED OR OBJECT??*/}
                        <ListItemText primary={`Name = ${this.state.simulation.loggedInUser}`}/>
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

//
// export function SecondaryListItems() {
//     let simulation1 = null;
//     ExecuteServices.getSimulation()
//         .then(response => simulation1 = (response.data))
//             .catch(response => console.log(response))
//
//     const [simulation, setSumulation] = React.useState(simulation1);
//     // useEffect( ()=>{
//     //     ExecuteServices.getSimulation()
//     //         .then(response => simulation1 = (response.data))
//     //         .catch(response => console.log(response))
//     //     }
//     // )
//     return (
//         <div>
//             <ListSubheader inset>Simulation conditions!</ListSubheader>
//             <ListItem button>
//                 <ListItemIcon>
//                     <AssignmentIcon/>
//                 </ListItemIcon>
//                 <ListItemText primary={`Temperature: ${simulation.temperature}`}/>
//             </ListItem>
//             <ListItem button>
//                 <ListItemIcon>
//                     <AssignmentIcon/>
//                 </ListItemIcon>
//                 <ListItemText primary={`Time: ${simulation.time}`}/>
//             </ListItem>
//             <ListItem button>
//                 <ListItemIcon>
//                     <AssignmentIcon/>
//                 </ListItemIcon>
//                 <ListItemText primary={`Date: ${simulation.date}`}/>
//             </ListItem>
//         </div>
//     );
// }
