import React from 'react';
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

export function mainListItems() {
    return (
        <div>

            {/*<ListItem button>*/}
            {/*  <ListItemIcon>*/}
            {/*    <DashboardIcon />*/}
            {/*  </ListItemIcon>*/}
            {/*  <ListItemText primary="Logged in" />*/}
            {/*</ListItem>*/}
            {/*<ListItem button>*/}
            {/*  <ListItemIcon>*/}
            {/*    <ShoppingCartIcon />*/}
            {/*  </ListItemIcon>*/}
            {/*  <ListItemText primary="Orders" />*/}
            {/*</ListItem>*/}
            <ListItem button>
                <ListItemIcon>
                    <PeopleIcon/>
                </ListItemIcon>
                <ListItemText primary={sessionStorage.getItem('authenticatedName')}/>
            </ListItem>
            {/*<ListItem button>*/}
            {/*  <ListItemIcon>*/}
            {/*    <BarChartIcon />*/}
            {/*  </ListItemIcon>*/}
            {/*  <ListItemText primary="Reports" />*/}
            {/*</ListItem>*/}
            {/*<ListItem button>*/}
            {/*  <ListItemIcon>*/}
            {/*    <LayersIcon />*/}
            {/*  </ListItemIcon>*/}
            {/*  <ListItemText primary="Integrations" />*/}
            {/*</ListItem>*/}
        </div>
    );
}

export function secondaryListItems() {
    return (
        <div>
            <ListSubheader inset>Simulation conditions!</ListSubheader>
            <ListItem button>
                <ListItemIcon>
                    <AssignmentIcon/>
                </ListItemIcon>
                <ListItemText primary={`Temperature: ${sessionStorage.getItem('temperature')}`}/>
            </ListItem>
            <ListItem button>
                <ListItemIcon>
                    <AssignmentIcon/>
                </ListItemIcon>
                <ListItemText primary={`Time: ${sessionStorage.getItem('time')}`}/>
            </ListItem>
            <ListItem button>
                <ListItemIcon>
                    <AssignmentIcon/>
                </ListItemIcon>
                <ListItemText primary={`Date: ${sessionStorage.getItem('date')}`}/>
            </ListItem>
        </div>
    );
}
