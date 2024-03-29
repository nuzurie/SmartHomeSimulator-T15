import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import ExecuteServices from "../../api/ExecuteServices";
import Popup from "reactjs-popup";


export default class ZoneRooms extends Component {
    constructor(props) {
        super(props);

        this.state = {
            numberZones: 1,
            numberIntervals: 1,
            rooms: [],
            zone1: [],
            zone2: [],
            zone3: [],
            zone1TempInterval1: 20,
            zone1TempInterval2: 20,
            zone1TempInterval3: 20,
            zone2TempInterval1: 20,
            zone2TempInterval2: 20,
            zone2TempInterval3: 20,
            zone3TempInterval1: 20,
            zone3TempInterval2: 20,
            zone3TempInterval3: 20,
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
        this.refresh()
        this.interval = setInterval(() => {
            ExecuteServices.getHeating()
                .then(response => {
                    if (response.data.zoneAndTimeNumbers.numberZones !== this.state.numberZones)
                        this.setState({
                            numberZones: response.data.zoneAndTimeNumbers.numberZones,
                            numberIntervals: response.data.zoneAndTimeNumbers.numberIntervals,
                        })
                })
                .catch(error => console.log(error))
        }, 1000)
        console.log(this.state)
    }

    componentWillUnmount(): void {
        clearInterval(this.interval)
    }

    refresh() {
        ExecuteServices.getAllRooms()
            .then(response => {
                this.setState({
                        rooms: response.data
                    }
                )
            })
            .catch(error => console.log(error))

        console.log(this.state)
    }


    render() {
        let {zone1} = this.state.zone1

        return (
            <div className={"container"}>
                <h3>Rooms for Zones</h3>
                <div className="container">
                    <Formik
                        initialValues={{zone1}}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Popup
                                    trigger={<button className="button btn-primary"> Select Rooms for Zones </button>}
                                    modal
                                >
                                    {close => (<div className="modal">
                                        <button className="close" onClick={close}>          &times;        </button>
                                        <div className="header"> Modal Title</div>
                                        <div className="content">
                                        </div>
                                    </div>)}
                                    < Form>
                                        {
                                            this.state.success &&
                                            <div
                                                className={"alert alert-success dismissable"}>{this.state.success}</div>
                                        }
                                        <ErrorMessage name="name" component="div"
                                                      className="alert alert-warning"/>
                                        <div id="checkbox-group"><h5>Select Rooms For Zone 1</h5></div>
                                        <div role="group" aria-labelledby="checkbox-group">
                                            {this.state.rooms.map((room) =>
                                                <div>
                                                    <fieldset className="form-group">
                                                        <label>
                                                            <Field type="checkbox" key={room.id} name={"zone1"}
                                                                   value={room.id.toString()}/>
                                                            {room.name}
                                                        </label>
                                                    </fieldset>
                                                </div>
                                            )}
                                        </div>
                                        <fieldset className="form-group">
                                            <label>Zone 1 Interval 1 temperature</label>
                                            <Field type={"number"} name={"zone1TempInterval1"}></Field>
                                        </fieldset>
                                        {this.state.numberIntervals > 1 &&
                                        <fieldset className="form-group">
                                            <label>Zone 1 Interval 2 temperature</label>
                                            <Field type={"number"} name={"zone1TempInterval2"}></Field>
                                        </fieldset>}
                                        {this.state.numberIntervals > 2 &&
                                        <fieldset className="form-group">
                                            <label>Zone 1 Interval 3 temperature</label>
                                            <Field type={"number"} name={"zone1TempInterval3"}></Field>
                                        </fieldset>}
                                        {this.state.numberZones > 1 && <>
                                            <div id="checkbox-group"><h5>Select Rooms For Zone 2</h5></div>
                                            <div role="group" aria-labelledby="checkbox-group">
                                                {this.state.rooms.map((room) =>
                                                    <div>
                                                        <fieldset className="form-group">
                                                            <label>
                                                                <Field type="checkbox" key={room.id} name={"zone2"}
                                                                       value={room.id.toString()}/>
                                                                {room.name}
                                                            </label>
                                                        </fieldset>
                                                    </div>
                                                )}
                                            </div>
                                            <fieldset className="form-group">
                                                <label>Zone 2 Interval 1 temperature</label>
                                                <Field type={"number"} name={"zone2TempInterval1"}></Field>
                                            </fieldset>
                                            {this.state.numberIntervals > 1 &&
                                            <fieldset className="form-group">
                                                <label>Zone 2 Interval 2 temperature</label>
                                                <Field type={"number"} name={"zone2TempInterval2"}></Field>
                                            </fieldset>}
                                            {this.state.numberIntervals > 2 &&
                                            <fieldset className="form-group">
                                                <label>Zone 2 Interval 3 temperature</label>
                                                <Field type={"number"} name={"zone2TempInterval3"}></Field>
                                            </fieldset>}
                                        </>}
                                        {this.state.numberZones > 2 && <>
                                            <div id="checkbox-group"><h5>Select Rooms For Zone 3</h5></div>
                                            <div role="group" aria-labelledby="checkbox-group">
                                                {this.state.rooms.map((room) =>
                                                    <div>
                                                        <fieldset className="form-group">
                                                            <label>
                                                                <Field type="checkbox" key={room.id} name={"zone3"}
                                                                       value={room.id.toString()}/>
                                                                {room.name}
                                                            </label>
                                                        </fieldset>
                                                    </div>
                                                )}
                                            </div>
                                            <fieldset className="form-group">
                                                <label>Zone 3 Interval 1 temperature</label>
                                                <Field type={"number"} name={"zone3TempInterval1"}></Field>
                                            </fieldset>
                                            {this.state.numberIntervals > 1 &&
                                            <fieldset className="form-group">
                                                <label>Zone 3 Interval 2 temperature</label>
                                                <Field type={"number"} name={"zone3TempInterval2"}></Field>
                                            </fieldset>}
                                            {this.state.numberIntervals > 2 &&
                                            <fieldset className="form-group">
                                                <label>Zone 3 Interval 3 temperature</label>
                                                <Field type={"number"} name={"zone3TempInterval3"}></Field>
                                            </fieldset>}
                                        </>}
                                        <button className="btn btn-primary" type="submit">Update</button>
                                    </Form>
                                </Popup>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value})
    }

    onSubmit(event) {
        this.setState({
            success: ``,
            zone1: event.zone1,
            zone2: event.zone2,
            zone3: event.zone3,
            zone1TempInterval1: event.zone1TempInterval1,
            zone1TempInterval2: event.zone1TempInterval2,
            zone1TempInterval3: event.zone1TempInterval3,
            zone2TempInterval1: event.zone2TempInterval1,
            zone2TempInterval2: event.zone2TempInterval2,
            zone2TempInterval3: event.zone2TempInterval3,
            zone3TempInterval1: event.zone3TempInterval1,
            zone3TempInterval2: event.zone3TempInterval2,
            zone3TempInterval3: event.zone3TempInterval3,
        })
        console.log(this.state)
        ExecuteServices.setZoneRoomsAndTemp(event)
            .then(response => this.setState({
                success: "Rooms updated"
            }))
            //CATCH EXCEPTION HERE
            .catch(error => alert(error.response.data))
    }
}

export class RoomTemps extends Component {
    constructor(props) {
        super(props);

        this.state = {
            rooms: [],
            message: null
        }
    }

    componentDidMount(): void {
        ExecuteServices.getAllRooms()
            .then(response =>
                this.setState({rooms: response.data}))
            .catch(error => console.log(error))
        this.interval = setInterval(() =>
            ExecuteServices.getAllRooms()
                .then(response => {
                    if (response.data !== this.state.rooms)
                        this.setState({
                            rooms: response.data
                        })
                    // this.state.rooms.map(room => {
                    //     if (room.temperature <= 0.1)
                    //         this.setState({
                    //             message: "Warning. Pipes may burst!"
                    //         })
                    //     else {
                    //         this.setState({
                    //             message: null
                    //         })
                    //     }
                    // })
                    if (this.state.rooms.some(room => room.temperature <= 0.1))
                        this.setState({
                            message: "Warning. Pipes may burst!"
                        })
                    else {
                        this.setState({
                            message: null
                        })
                    }
                })
                .catch(err => console.log(err)), 1000
        )
    }

    componentWillUnmount(): void {
        clearInterval(this.interval)
    }

    render() {
        return (
            <div className={"container"}>
                <h3>Show Temperatures</h3>
                <div className={"container"}>
                    <Popup
                        trigger={<button className="button btn-primary"> Show Temperatures </button>}
                        modal
                    >
                        {close =>
                            <div className={"container table"}>
                                {this.state.message &&
                                <div className="alert alert-danger" role="alert">
                                    "The pipes may burst. Temperature below 0˚C"
                                </div>}
                                <table>
                                    <thead>
                                    <tr>
                                        <th scope={"col"}>Room</th>
                                        <th scope={"col"}>Temperature</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {this.state.rooms.map(room =>
                                        <tr>
                                            <td>{room.name}</td>
                                            <td>{room.temperature.toFixed(1)}</td>
                                        </tr>
                                    )}
                                    </tbody>
                                </table>
                            </div>}
                    </Popup>
                </div>
            </div>
        )
    }
}

export class OverrideRooms extends Component {
    constructor(props) {
        super(props);

        this.state = {
            success: '',
            rooms: [],
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
        this.refresh()

    }

    refresh() {
        ExecuteServices.getRoomsForUsers()
            .then(response => {
                this.setState({
                        rooms: response.data
                    }
                )
            })
            .catch(error => console.log(error))

        console.log(this.state)
    }


    render() {
        let id = []
        return (
            <div className={"container"}>
                <h3>Rooms for Overriding</h3>
                <div className="container">
                    <Formik
                        initialValues={id}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Popup
                                    trigger={<button className="button btn-primary"> Select Rooms for Overriding </button>}
                                    modal
                                >
                                    {close => (<div className="modal">
                                        <button className="close" onClick={close}>          &times;        </button>
                                        <div className="header"> Modal Title</div>
                                        <div className="content">
                                        </div>
                                    </div>)}
                                    < Form>
                                        {
                                            this.state.success &&
                                            <div
                                                className={"alert alert-success dismissable"}>{this.state.success}</div>
                                        }
                                        <ErrorMessage name="name" component="div"
                                                      className="alert alert-warning"/>
                                        <div id="checkbox-group"><h5>Select Rooms For Zone 1</h5></div>
                                        <div role="group" aria-labelledby="checkbox-group">
                                            {this.state.rooms.map((room) =>
                                                <div>
                                                    <fieldset className="form-group">
                                                        <label>
                                                            <Field type="checkbox" key={room.id} name={"rooms"}
                                                                   value={room.id.toString()}/>
                                                            {room.name}
                                                        </label>
                                                    </fieldset>
                                                    {/*<fieldset className="form-group">*/}
                                                    {/*    <label>Temperature for the room</label>*/}
                                                    {/*    <Field type={"number"} name={room.id}></Field>*/}
                                                    {/*</fieldset>*/}
                                                </div>
                                            )}
                                        </div>

                                        <button className="btn btn-primary" type="submit">Update</button>
                                    </Form>
                                </Popup>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value})
    }

    onSubmit(event) {
        this.setState({
            success: `Rooms updated`,
        })
        console.log("The event is:", event)
        // ExecuteServices.setZoneRoomsAndTemp(event)
        //     .then(response => console.log(response))
        //     //CATCH EXCEPTION HERE
        //     .catch(error => console.log(error.message))
    }
}


