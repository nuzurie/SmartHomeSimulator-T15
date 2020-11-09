// import React, {Component} from "react";
// import ExecuteServices from "../../api/ExecuteServices";
// import {Field, Formik} from "formik";
// import Form from "reactstrap/es/Form";
//
// export default class LightsAwayMode extends Component {
//
//     constructor(props) {
//         super(props);
//
//         this.state = {
//             user: [],
//             lights: [],
//             checked: []
//         }
//
//         this.refresh = this.refresh.bind(this)
//         this.submit = this.submit.bind(this)
//
//     }
//
//     componentDidMount() {
//         this.refresh()
//     }
//
//     submit(event) {
//         ExecuteServices.getLightsForUsers()
//             .then()
//             .catch()
//     }
//
//     refresh() {
//         ExecuteServices.getSimulation()
//             .then(response => {
//                 this.setState({
//                     user: response.data.loggedInUser
//                 })
//             })
//             .catch(error => console.log(error))
//
//         ExecuteServices.getLightsForUsers()
//             .then(response => this.setState({
//                 lights: response.data
//             }))
//             .catch(error => console.log(error))
//
//     }
//
//     render() {
//         return (
//             <div className={"container"}>
//                 <h1>Select Lights</h1>
//                 <Formik
//                     initialValues={{
//                         checked: [],
//                     }}
//                     onSubmit={this.submit}
//                 >
//                     {
//                         (props,
//                          handleSubmit,) => (
//                             <Form>
//
//                                 <div id="checkbox-group">Checked</div>
//                                 <div role="group" aria-labelledby="checkbox-group">
//                                     {this.state.lights.map((light) =>
//                                         <div>
//                                             <label>
//                                                 <Field type="checkbox" key={light.id} name="checked"
//                                                        value={light.id.toString()}/>
//                                                 {light.name}
//                                             </label>
//                                         </div>
//                                     )}
//                                 </div>
//
//                                 <button className={"btn btn-primary"} onSubmit={(e) => {
//                                     e.preventDefault();
//                                     handleSubmit();
//                                 }}>Submit</button>
//                             </Form>
//                         )}
//                 </Formik>
//             </div>)
//     }
// }


import React, {Component} from 'react';
import {Formik, Form, Field, ErrorMessage} from 'formik';
import ExecuteServices from "../../api/ExecuteServices";


export default class CallAuthoritiesForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            user: [],
            lights: [],
            checked: []
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
        this.refresh()
    }

    refresh() {
        ExecuteServices.getSimulation()
            .then(response => {
                this.setState({
                        user: response.data.loggedInUser
                    }
                )
            })
            .catch(error => console.log(error))

        ExecuteServices.getLightsForUsers()
            .then(response => this.setState({
                lights: response.data
            }))
            .catch(error => console.log(error))

    }


    render() {
        let {checked} = this.state.checked

        return (
            <div className={"container"}>
                <h3>Lights for auto-mode.</h3>
                <div className="container">
                    <Formik
                        initialValues={{checked}}
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
                                    <div id="checkbox-group"><h5>Select lights</h5></div>
                                    <div role="group" aria-labelledby="checkbox-group">
                                        {this.state.lights.map((light) =>
                                            <div>
                                                <fieldset className="form-group">
                                                    <label>
                                                        <Field type="checkbox" key={light.id} name="checked"
                                                               value={light.id.toString()}/>
                                                        {light.name}
                                                    </label>
                                                </fieldset>
                                            </div>
                                        )}
                                    </div>
                                    {/*<fieldset className="form-group">*/}
                                    {/*    <label>Timer(minutes)</label>*/}
                                    {/*    <Field className={"form-control"} name={"timer"} type={"number"}*/}
                                    {/*           onChange={event => this.handleChange(event)}></Field>*/}
                                    {/*    <button className="btn btn-primary" type="submit">Update</button>*/}
                                    {/*</fieldset>*/}
                                    <button className="btn btn-primary" type="submit">Update</button>
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
            success: `Auto mode Lights updated.`,
        })
        ExecuteServices.awayModeLights(event)
            .then(response => console.log(response))
            .catch(response => console.log(response))
    }

}


