import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import ExecuteServices from "../../api/ExecuteServices";


export default class CallAuthoritiesForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            timer: 0,
            success: null
        }
        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
    }

    render() {
        let {timer} = this.state

        return (
            <div className={"container"}>
                <h3>Set Timer to call authorities.</h3>
                <div className="container">
                    <Formik
                        initialValues={{timer}}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (

                                <Form>
                                    {this.state.success &&
                                    <div className={"alert alert-success dismissable"}>{this.state.success}</div>}
                                    <ErrorMessage name="name" component="div"
                                                  className="alert alert-warning"/>
                                    <fieldset className="form-group">
                                        <label>Timer(minutes)</label>
                                        <Field className={"form-control"} name={"timer"} type={"number"}
                                               onChange={event => this.handleChange(event)}></Field>
                                        <button className="btn btn-primary" type="submit">Update</button>
                                    </fieldset>
                                </Form>
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
            success: `Authorities will be called after ${event.timer}!`,
        })
        ExecuteServices.callAuthoritiesTimer(event.timer)
            .then(response => console.log(response))
            .catch(error => console.log(error))
    }

}


