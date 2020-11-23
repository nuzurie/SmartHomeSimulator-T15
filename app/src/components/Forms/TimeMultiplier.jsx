import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import ExecuteServices from "../../api/ExecuteServices";


class TimeMultiplierForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            multiplier: 0,
            success: null
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
    }

    render() {
        let {multiplier} = this.state

        return (
            <div className={"container"}>
                <h3>Set Time Speed</h3>
                <div className="container">
                    <Formik
                        initialValues={{multiplier}}
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
                                        <label>Multiplier</label>
                                        <Field className={"form-control"} name={"multiplier"} type={"number"}
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
        ExecuteServices.setTimeMultiplier(event.multiplier)
            .then(()=> this.setState({
                success: `The time now runs at ${event.multiplier}x speed.`,
            }))
            .catch(()=> alert("You don't have the privileges to perform this action."))
    }

}


export default TimeMultiplierForm;