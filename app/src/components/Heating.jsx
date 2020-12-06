import React, {Component} from "react";
import ExecuteServices from "../api/ExecuteServices";
import {ErrorMessage, Field, Formik} from "formik";
import Form from "reactstrap/es/Form";
//
// export default class Zones extends Component {
//
//     constructor(props) {
//         super(props);
//         this.state = {
//             simulation: [],
//             numberOfZones: 1,
//             zone1start: '',
//             zone2start: '',
//             zone3start: '',
//             zone1end: '',
//             zone2end: '',
//             zone3end: '',
//             success: '',
//         }
//         this.submitHeating = this.submitHeating.bind(this)
//         this.handleChange = this.handleChange.bind(this)
//         this.handleChange2 = this.handleChange2.bind(this)
//     }
//
//     componentDidMount() {
//         ExecuteServices.getSimulation()
//             .then(response => this.setState({
//                 simulation: response.data
//             }))
//     }
//
//     submitHeating(event) {
//         console.log(event)
//     }
//
//     handleChange(event) {
//         console.log(event.target)
//         this.setState({
//             numberOfZones: parseInt(event.target.value)
//         })
//     }
//
//     handleChange2(event) {
//         console.log("2", event.target)
//     }
//
//     render() {
//         const initialValues = this.state.numberOfZones
//         const zones = ['0', '1', '2', '3']
//         const loop = 0
//         return (
//             // <div>
//             //                 <div className={"container"}>
//             //                     <h1>Set Simulation Parameters</h1>
//             //                     <div className="container">
//             //                         <Formik
//             //                             initialValues={{temperature, date, time}}
//             //                             onSubmit={this.onSubmit}
//             //                             validateOnChange={false}
//             //                             validateOnBlur={false}
//             //                             validate={this.validate}
//             //                             enableReinitialize={true}
//             //                         >
//             //                             {
//             //                                 (props) => (
//             //
//             //                                     <Form>
//             //                                         {this.state.success && <div className={"alert alert-success dismissable"}>{this.state.success}</div>}
//             //                                         <ErrorMessage name="name" component="div"
//             //                                                       className="alert alert-warning"/>
//             //                                         <fieldset className="form-group">
//             //                                             <label>Temperature (˚C)</label>
//             //                                             <Field className="form-control" type="number" name="temperature" />
//             //                                         </fieldset>
//             //                                         <fieldset className="form-group">
//             //                                             <label>Date (YYYY-MM-DD)</label>
//             //                                             <Field className="form-control" type="date" name="date" />
//             //                                         </fieldset>
//             //                                         <fieldset className="form-group">
//             //                                             <label>Time (HH-MM AM/PM)</label>
//             //                                             <Field className="form-control" type="time" name="time" />
//             //                                         </fieldset>
//             //
//             //                                         <button className="btn btn-primary" type="submit">Save</button>
//             //                                     </Form>
//             //                                 )
//             //                             }
//             //                         </Formik>
//             //                     </div>
//             //                     <div className={"container"}><UserList/></div>
//             //                 </div>
//             //             </div>
//             <div className={"container"}>
//                 <Formik
//                     initialValues={initialValues}
//                     onSubmit={this.submitHeating}
//                     enableReinitialize={true}
//                 >
//                     {
//                         (props) => (
//
//                             <Form>
//                                 {this.state.success &&
//                                 <div className={"alert alert-success dismissable"}>{this.state.success}</div>}
//                                 <ErrorMessage name="name" component="div"
//                                               className="alert alert-warning"/>
//                                 <fieldset className="form-group">
//                                     <label>Temperature (˚C)</label>
//                                     <Field className="form-control" type="number" name="temperature"/>
//                                 </fieldset>
//                                 <fieldset className="form-group">
//                                     <label>Date (YYYY-MM-DD)</label>
//                                     <Field className="form-control" type="date" name="date"/>
//                                 </fieldset>
//                                 <fieldset className="form-group">
//                                     <label>Time (HH-MM AM/PM)</label>
//                                     <Field className="form-control" type="time" name="time"/>
//                                 </fieldset>
//
//                                 <button className="btn btn-primary" type="submit">Save</button>
//                             </Form>
//                         )
//                     }
//                     {/*<Form*/}
//                     {/*    onSubmit={(e) => {*/}
//                     {/*        console.log(e)*/}
//                     {/*        e.preventDefault();*/}
//                     {/*        // this.submitHeating(e);*/}
//                     {/*    }}>*/}
//                     {/*    <select*/}
//                     {/*        name={"numberOfZones"}*/}
//                     {/*        onChange={this.handleChange}*/}
//                     {/*        value={this.state.numberOfZones}>*/}
//                     {/*        <option value={0}>0</option>*/}
//                     {/*        <option value={1}>1</option>*/}
//                     {/*        <option value={2}>2</option>*/}
//                     {/*        <option value={3}>3</option>*/}
//                     {/*    </select>*/}
//                     {/*    /!*{[...Array(this.state.numberOfZones)].map(*!/*/}
//                     {/*    /!*    (value: [], index: number) => (*!/*/}
//                     {/*    /!*        <>*!/*/}
//                     {/*    /!*            <br/>Zone {index+1}*!/*/}
//                     {/*    /!*            <fieldset className="form-group">*!/*/}
//                     {/*    /!*                <label>Start Time (HH-MM AM/PM)</label>*!/*/}
//                     {/*    /!*                <Field*!/*/}
//                     {/*    /!*                    className="form-control"*!/*/}
//                     {/*    /!*                    type="time"*!/*/}
//                     {/*    /!*                    name={"zone"+(index+1)+"start"}*!/*/}
//                     {/*    /!*                    onChange={this.handleChange2}*!/*/}
//                     {/*    /!*                />*!/*/}
//                     {/*    /!*            </fieldset>*!/*/}
//                     {/*    /!*            <fieldset className="form-group">*!/*/}
//                     {/*    /!*                <label>End Time (HH-MM AM/PM)</label>*!/*/}
//                     {/*    /!*                <Field className="form-control" type="time" name={"zone"+(index+1)+"end"}*!/*/}
//                     {/*    /!*                       onChange={this.handleChange2}*!/*/}
//                     {/*    /!*                />*!/*/}
//                     {/*    /!*            </fieldset>*!/*/}
//                     {/*    /!*        </>*!/*/}
//                     {/*    /!*    )*!/*/}
//                     {/*    /!*)}*!/*/}
//                     {/*    <button type={"submit"} className={"btn btn-sm btn-primary"}>Submit</button>*/}
//                     {/*</Form>*/}
//                 </Formik>
//             </div>
//         );
//     }
// }

export default class Heating extends Component {
    constructor(props) {
        super(props);
        this.state = {
            simulation: [],
            numberOfZones: 1,
            zone1start: '',
            zone2start: '',
            zone3start: '',
            zone1end: '',
            zone2end: '',
            zone3end: '',
            success: '',
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.validate = this.validate.bind(this)
    }

    componentDidMount() {
        ExecuteServices.getSimulation()
            .then(response => this.setState({
                simulation: response.data
            }))
    }

    render() {
        let {temperature, date, time} = this.state

        return (
            <div>
                <div className={"container"}>
                    <h1>Set Simulation Parameters</h1>
                    <div className="container">
                        <Formik
                            initialValues={{temperature, date, time}}
                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
                            enableReinitialize={true}
                        >
                            {
                                (props) => (

                                    <Form>
                                        <select
                                            name={"numberOfZones"}
                                            onChange={this.handleChange}
                                            value={this.state.numberOfZones}>
                                            <option value={0}>0</option>
                                            <option value={1}>1</option>
                                            <option value={2}>2</option>
                                            <option value={3}>3</option>
                                        </select>
                                        {[...Array(this.state.numberOfZones)].map(
                                            (value: [], index: number) => (
                                                <>
                                                    <br/>Zone {index+1}
                                                    <fieldset className="form-group">
                                                        <label>Start Time (HH-MM AM/PM)</label>
                                                        <Field
                                                            className="form-control"
                                                            type="time"
                                                            name={"zone"+(index+1)+"start"}
                                                            onChange={this.handleChange2}
                                                        />
                                                    </fieldset>
                                                    <fieldset className="form-group">
                                                        <label>End Time (HH-MM AM/PM)</label>
                                                        <Field className="form-control" type="time" name={"zone"+(index+1)+"end"}
                                                               onChange={this.handleChange2}
                                                        />
                                                    </fieldset>
                                                </>
                                            )
                                        )}

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
    }

    validate(values) {
        console.log(values)
    }

}

export class HVAC extends Component{
    constructor() {
        super();
        this.hvac = this.hvac.bind(this)

        this.state = {
            on: false,
        }
    }
    render() {
        return (
            <button className={this.state.on ? "ml-5 btn btn-success" : "ml-5 btn btn-danger"} onClick={this.hvac}>Turn {this.state.on ? "off" : "on"} HVAC</button>
        )
    }

    hvac(){
        ExecuteServices.onHVAC()
            .then(response => this.setState({
                on: response.data
            }))
            .catch(response => console.log(response))
    }
}
