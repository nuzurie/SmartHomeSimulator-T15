import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import moment from 'moment';
import ExecuteServices from "../../api/ExecuteServices";

export default class AwayModeLightsTimerForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            time1: moment(new Date()).format('HH-mm-ss'),
            time2: moment(new Date()).format('HH-mm-ss'),
            success: null,
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    render() {
        let {time1, time2} = this.state

        return (
            <div>
                <div className={"container"}>
                    <h3>Set times for lights on during away mode.</h3>
                    <div className="container">
                        <Formik
                            initialValues={{time1, time2}}
                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
                            enableReinitialize={true}
                        >
                            {
                                (props) => (

                                    <Form>
                                        {this.state.success && <div className={"alert alert-success dismissable"}>{this.state.success}</div>}
                                        <ErrorMessage name="name" component="div"
                                                      className="alert alert-warning"/>
                                        <fieldset className="form-group">
                                            <label>Time (HH-MM AM/PM)</label>
                                            <Field className="form-control" type="time" name="time1" />
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Time (HH-MM AM/PM)</label>
                                            <Field className="form-control" type="time" name="time2" />
                                        </fieldset>
                                        <button className="btn btn-primary" type="submit">Save</button>
                                    </Form>
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
        console.log(event)
        ExecuteServices.awayModeLightsTimes(event)
            .then(response => console.log(response))
            .catch(error => console.log(error))
        // ExecuteService.updateSimulationContext(event)
        //     .then(response => console.log(response))
        //     .catch(error => console.log(error))
    }

    validate(values) {

    }


}
