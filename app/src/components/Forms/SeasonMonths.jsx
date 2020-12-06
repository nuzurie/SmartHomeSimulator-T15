import React, {Component} from "react";
import ExecuteServices from "../../api/ExecuteServices";
import {ErrorMessage, Field, Form, Formik} from "formik";
import Popup from "reactjs-popup";

export default class SeasonMonths extends Component {
    constructor(props) {
        super(props);

        this.state = {
            numberZones: 1,
            numberIntervals: 1,
            months: [],
            summer: [],
            winter: [],
            summerTemperature: 24,
            winterTemperature: 19,
            message: '',
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.refresh = this.refresh.bind(this)
    }

    componentDidMount() {
        this.refresh()
    }


    refresh() {
        ExecuteServices.getMonths()
            .then(response => this.setState({
                months: response.data
            }))
            .catch(error => console.log(error))
    }


    render() {
        let {summer, winter, summerTemperature, winterTemperature}
        = [this.state.summer, this.state.winter, this.state.summerTemperature, this.state.winterTemperature]

        return (
            <div className={"container"}>
                <h3>Months for Summer/Winter</h3>
                <div className="container">
                    <Formik
                        initialValues={{summer, winter, summerTemperature, winterTemperature}}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Popup
                                    trigger={<button className="button btn-primary"> Select Months for
                                        Seasons </button>}
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
                                        <div id="checkbox-group"><h5>Select Months for Summer</h5></div>
                                        <div role="group" aria-labelledby="checkbox-group">
                                            {this.state.months.map((month) =>
                                                <div>
                                                    <fieldset className="form-group">
                                                        <label>
                                                            <Field type="checkbox" key={month} name={"summer"}
                                                                   value={month}/>
                                                            {month}
                                                        </label>
                                                    </fieldset>
                                                </div>
                                            )}
                                        </div>
                                        <div id="checkbox-group"><h5>Select Default Temperature for Summer</h5></div>
                                        <fieldset className="form-group">
                                            <label></label>
                                            <Field type="number" name={"summerTemperature"}/>
                                        </fieldset>
                                        <div id="checkbox-group"><h5>Select Months for Winter</h5></div>
                                        <div role="group" aria-labelledby="checkbox-group">
                                            {this.state.months.map((month) =>
                                                <div>
                                                    <fieldset className="form-group">
                                                        <label>
                                                            <Field type="checkbox" key={month} name={"winter"}
                                                                   value={month}/>
                                                            {month}
                                                        </label>
                                                    </fieldset>
                                                </div>
                                            )}
                                        </div>
                                        <div id="checkbox-group"><h5>Select Default Temperature for Winter</h5></div>
                                        <fieldset className="form-group">
                                            <label></label>
                                            <Field type="number" name={"winterTemperature"}/>
                                        </fieldset>
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
    //TODO: VALIDATE THE MONTHS
    onSubmit(event) {
        console.log(event)
        ExecuteServices.setMonths(event)
            .then(response => this.setState({
                success: `Months updated`
            }))
            //TODO Catch exception here
            .catch(error => alert(error.response.data, " Please try again."))
    }
}