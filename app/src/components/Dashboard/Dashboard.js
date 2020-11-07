import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import Box from '@material-ui/core/Box';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Link from '@material-ui/core/Link';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import PowerSettingsNewIcon from '@material-ui/icons/PowerSettingsNew';
import mainListItems from './listItems.jsx';
import Deposits from "./Desposits";

import SimulationForm from "../Users/Forms/FullForm";
import RoomList from "../Rooms/RoomList";
import {Link as RouterLink} from 'react-router-dom'
import SecondaryListItems from "./listItems";
import ExecuteServices from "../../api/ExecuteServices";
import AwayModeButton from "../Security/AwayModeButton";
import TimeMultiplierForm from "../Forms/TimeMultiplier";
import CallAuthoritiesForm from "../Forms/CallAuthoritiesForm";


function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit">
        SmartHomeSimulation
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  toolbar: {
    paddingRight: 24, // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 240,
  },
}))

export default function Dashboard() {
  const classes = useStyles();
  const [open, setOpen] = React.useState(true);
  const [contentDiv, setContentDiv] = React.useState('SHS')
  // const [simulation, setSimulation] = React.useState()
  const [changeLogin, setLogin] = React.useState([
    sessionStorage.getItem('authenticatedName'),
    sessionStorage.getItem('temperature'),
    sessionStorage.getItem('time'),
    sessionStorage.getItem('date')
  ])
  const handleUpdateUser = () => {
    setLogin([
      sessionStorage.getItem('authenticatedName'),
      sessionStorage.getItem('temperature'),
      sessionStorage.getItem('time'),
      sessionStorage.getItem('date')
    ])
  }
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);
  const showSHS = () => {
    setContentDiv('SHS')
  }
  const showSHC = () => {
    setContentDiv('SHC')
  }
  const showSHH = () => {
    setContentDiv('SHP')
  }
  const test = () => {
    ExecuteServices.toggleAwayMode()
        .then(response => console.log(response))
        .catch(response => console.log(response))
  }
  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="absolute" className={clsx(classes.appBar, open && classes.appBarShift)}>
        <Toolbar className={classes.toolbar}>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            className={clsx(classes.menuButton, open && classes.menuButtonHidden)}
          >
            <MenuIcon />
          </IconButton>
          <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
            Smart Home Simulator
            <button className={"ml-4 btn btn-outline-info btn-sm"} onClick={showSHS}>SHS</button>
            <button className={"btn btn-outline-info btn-sm"} onClick={showSHC}>SHC</button>
            <button className={"btn btn-outline-info btn-sm"} onClick={showSHH}>SHH</button>
            <button className={"ml-4 btn btn-success btn-sm"} onClick={handleUpdateUser}>Update Sidebar!</button>
          </Typography>
          <RouterLink to={'/'}>
            <IconButton color="inherit">
                <PowerSettingsNewIcon/>
            </IconButton>
          </RouterLink>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        classes={{
          paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
        }}
        open={open}
      >
        <div className={classes.toolbarIcon}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        {/*<List>{mainListItems(changeLogin)}</List>*/}

        <List><SecondaryListItems/></List>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Container maxWidth="lg" className={classes.container}>
          <Grid container spacing={3}>
            {/* Chart */}
            <Grid item xs={12} md={8} lg={9}>
              <Paper className={fixedHeightPaper}>
                <RoomList/>
              </Paper>
            </Grid>
            {/* Recent Deposits */}
            <Grid item xs={12} md={4} lg={3}>
              <Paper className={fixedHeightPaper}>
                <div>This space is reserved for settings of other modules.</div>
              </Paper>
            </Grid>
            {/* Recent Orders */}
            <Grid item xs={12}>
              <Paper className={classes.paper}>
                {contentDiv==='SHS' &&
                <div className={"container"}>
                  <SimulationForm />
                </div>}
                {contentDiv==='SHC' &&
                <div>
                  <TimeMultiplierForm/>
                </div>}
                {contentDiv==='SHP' &&<div>
                  <AwayModeButton/>
                  <CallAuthoritiesForm/>
                </div>}tar
                {/*{contentDiv==='SHC' &&<Orders />}*/}
              </Paper>
            </Grid>
          </Grid>
          <Box pt={4}>
            <Copyright />
          </Box>
        </Container>
      </main>
    </div>
  );
}
