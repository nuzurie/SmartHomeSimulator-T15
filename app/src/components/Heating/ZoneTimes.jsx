import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import moment from 'moment';
import ExecuteServices from "../../api/ExecuteServices";
import Popup from "reactjs-popup";

export default class TimeZones extends Component {
    constructor(props) {
        super(props);

        this.state = {
            numberIntervals: 1,
            time1Start: moment(new Date()).format('HH-mm-ss'),
            time1End: moment(new Date()).format('HH-mm-ss'),
            time2Start: moment(new Date()).format('HH-mm-ss'),
            time2End: moment(new Date()).format('HH-mm-ss'),
            time3Start: moment(new Date()).format('HH-mm-ss'),
            time3End: moment(new Date()).format('HH-mm-ss'),
            success: null,
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount(): void {
        this.refresh()
        this.interval = setInterval(() => {
            ExecuteServices.getHeating()
                .then(response => {
                    if (response.data.zoneAndTimeNumbers.numberIntervals != this.state.numberIntervals)
                        this.setState({
                            numberIntervals: response.data.zoneAndTimeNumbers.numberIntervals
                        })
                })
                .catch(error => console.log(error))
        }, 1000)
    }

    refresh(){
        ExecuteServices.getHeating()
            .then(response => this.setState({
                numberIntervals: response.data.zoneAndTimeNumbers.numberIntervals
            }))
            .catch(error => console.log(error))
    }

    componentWillUnmount(): void {
        clearInterval(this.interval)
    }

    render() {
        let {time1Start, time1End, time2Start, time2End, time3Start, time3End} = this.state

        return (
            <div>
                <div className={"container"}>
                    <h3>Time for Zones</h3>
                    <div className="container">
                        <Formik
                            initialValues={{time1Start, time1End, time2Start, time2End, time3Start, time3End}}
                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
                            enableReinitialize={true}
                        >
                            {
                                (props) => (
                                    <Popup trigger={<button className="button btn-primary" onClick={()=>this.refresh()}> Select Time Intervals for Zones</button>} modal
                                    >
                                        {close => (<div className="modal">
                                            <button className="close" onClick={close}>          &times;        </button>
                                            <div className="header"> Modal Title</div>
                                            <div className="content">
                                            </div>
                                        </div>)}

                                        <Form>
                                            {this.state.success && <div
                                                className={"alert alert-success dismissable"}>{this.state.success}</div>}
                                            <ErrorMessage name="name" component="div"
                                                          className="alert alert-warning"/>
                                            Interval 1
                                            <fieldset className="form-group">
                                                <label>Time (HH-MM AM/PM)</label>
                                                <Field className="form-control" type="time" name="time1Start"/>
                                            </fieldset>
                                            <fieldset className="form-group">
                                                <label>Time (HH-MM AM/PM)</label>
                                                <Field className="form-control" type="time" name="time1End"/>
                                            </fieldset>
                                            {this.state.numberIntervals > 1 && <>
                                            Interval 2
                                                <fieldset className="form-group">
                                                <label>Time (HH-MM AM/PM)</label>
                                                <Field className="form-control" type="time" name="time2Start"/>
                                                </fieldset>
                                                <fieldset className="form-group">
                                                <label>Time (HH-MM AM/PM)</label>
                                                <Field className="form-control" type="time" name="time2End"/>
                                                </fieldset>
                                            </>}
                                            {this.state.numberIntervals > 2 && <>
                                            Interval 3
                                            <fieldset className="form-group">
                                                <label>Time (HH-MM AM/PM)</label>
                                                <Field className="form-control" type="time" name="time3Start"/>
                                            </fieldset>
                                            <fieldset className="form-group">
                                                <label>Time (HH-MM AM/PM)</label>
                                                <Field className="form-control" type="time" name="time3End"/>
                                            </fieldset>
                                            </> }
                                            <button className="btn btn-primary" type="submit">Save</button>
                                        </Form>
                                    </Popup>
                                )
                            }
                        </Formik>
                    </div>
                </div>
            </div>
        )
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value})
    }

    onSubmit(event) {
        ExecuteServices.setZoneIntervals(event)
            .then(response => console.log(response))
            .catch(error => console.log(error))
    }

    validate(values) {

    }
}


export class ZoneTimeNumbers extends Component {
    constructor(props) {
        super(props);

        this.state = {
            numberZones: 1,
            numberIntervals: 1,
            success: null,
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    render() {
        let {numberZones, numberIntervals} = this.state

        return (
            <div>
                <div className={"container"}>
                    <h3>Number of Zones & Intervals</h3>
                    <div className="container">
                        <Formik
                            initialValues={{numberZones, numberIntervals}}
                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
                            enableReinitialize={true}
                        >
                            {
                                (props) => (
                                    <Popup trigger={<button className="button btn-primary"> Select Number of Zones</button>} modal
                                    >
                                        {close => (<div className="modal">
                                            <button className="close" onClick={close}>          &times;        </button>
                                            <div className="header"> Modal Title</div>
                                            <div className="content">
                                            </div>
                                        </div>)}

                                        <Form>
                                            {this.state.success && <div
                                                className={"alert alert-success dismissable"}>{this.state.success}</div>}
                                            <ErrorMessage name="name" component="div"
                                                          className="alert alert-warning"/>
                                            Interval Numbers
                                            <fieldset className="form-group">
                                                <select
                                                    name="numberIntervals"

                                                    onChange={this.handleChange}
                                                    style={{ display: 'block' }}
                                                >
                                                    <option value="" label="Select number of intervals" />
                                                    <option value="1" label="1" />
                                                    <option value="2" label="2" />
                                                    <option value="3" label="3" />
                                                </select>
                                            </fieldset>
                                            Zone Numbers
                                            <fieldset className="form-group">
                                                <select
                                                    name="numberZones"

                                                    onChange={this.handleChange}
                                                    style={{ display: 'block' }}
                                                >
                                                    <option value="" label="Select number of zones" />
                                                    <option value="1" label="1" />
                                                    <option value="2" label="2" />
                                                    <option value="3" label="3" />
                                                </select>
                                            </fieldset>
                                            <button className="btn btn-primary" type="submit">Save</button>
                                        </Form>
                                    </Popup>
                                )
                            }
                        </Formik>
                    </div>
                </div>
            </div>
        )
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value})
    }

    onSubmit(event) {
        ExecuteServices.setZoneAndTimeIntervals(event)
            .then(response => console.log(response))
            .catch(error => console.log(error.message))
    }

    validate(values) {

    }
}


