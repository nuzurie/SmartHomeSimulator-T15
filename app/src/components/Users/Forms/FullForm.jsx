import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import moment from 'moment';
import UserForm from "./UserForm";
import UserList from "../UserList";
import ExecuteService from "../../../api/ExecuteServices";

class SimulationForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            temperature: 25.0,
            date: moment(new Date()).format('YYYY-MM-DD'),
            time: moment(new Date()).format('HH-mm-ss'),
            success: null,
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    render() {
        let {temperature, date} = this.state

        return (
            <div>
                <div className={"container"}>
                    <h1>Set Simulation Parameters</h1>
                    <div className="container">
                        <Formik
                            initialValues={{temperature, date}}
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
                                            <label>Temperature (˚C)</label>
                                            <Field className="form-control" type="number" name="temperature" />
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Date (YYYY-MM-DD)</label>
                                            <Field className="form-control" type="date" name="date" />
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Time (HH-MM AM/PM)</label>
                                            <Field className="form-control" type="time" name="time" />
                                        </fieldset>

                                        <button className="btn btn-primary" type="submit">Save</button>
                                    </Form>
                                )
                            }
                        </Formik>
                        {/*{this.state.success && <Link to={'/'}>Add users</Link>}*/}
                    </div>
                    <div className={"container"}><UserList/></div>
                </div>
            </div>
        )
    }

    handleChange(event) {
        this.setState({[event.target.name]: event.target.value})
    }

    onSubmit(event) {
        this.setState({success: 'Simulation parameters changed.'})
        sessionStorage.setItem('temperature', event.temperature)
        sessionStorage.setItem('date', event.date)
        sessionStorage.setItem('time', event.time)

        ExecuteService.updateSimulation(event)
            .then(response => console.log(response))
            .catch(error => console.log(error))
    }

    validate(values) {
        // let errors = {}
        // if (!values.name) {
        //     errors.name = 'Enter a Name!'
        // } else if (values.name.length < 3) {
        //     errors.name = 'Enter atleast 3 Characters in Name'
        // }
        //
        //
        // return errors
    }


}


export default SimulationForm;