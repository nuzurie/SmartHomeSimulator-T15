import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import ExecuteService from "../../../api/ExecuteServices";


class UserForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: '',
            privilege: 'parent',
            success: null,
        }

        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    render() {
        let {name, privilege} = this.state

        return (
            <div>
                    <h3>Add Users</h3>
                    <div className="container">
                        <Formik
                            initialValues={{name, privilege}}
                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
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
                                            <label>Name</label>
                                            <Field className={"form-control"} name={"name"} type={"text"}
                                                   onChange={event => this.handleChange(event)}></Field>
                                            {/*</fieldset>*/}
                                            {/*<fieldset>*/}
                                            <label>Privileges</label>
                                            <Field className={"form-control"} name={"privilege"} as={"select"}>
                                                <option value="Parent">Parent</option>
                                                <option value="Child">Child</option>
                                                <option value="Guest">Guest</option>
                                                <option value="Stranger">Stranger</option>
                                            </Field>
                                            <button className="btn btn-primary" type="submit">Add</button>
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
        console.log("From user form:", event)
        this.setState({
            success: `A user with name ${event.name} has been created with ${event.privilege}!`,

        })

        let user = {
            name: event.name,
            privilege: event.privilege
        }

        ExecuteService.postUser(user)
            .then(response => console.log(response))
            .catch(error => console.log(error))
    }

    validate(values) {
        let errors = {}
        if (!values.name) {
            errors.name = 'Enter a Name!'
        } else if (values.name.length < 3) {
            errors.name = 'Enter atleast 3 Characters in Name'
        }
        return errors
    }
}


export default UserForm;