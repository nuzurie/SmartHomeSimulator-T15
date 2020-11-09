import React, {Component} from "react";

export default class UserPermission extends Component {

    render() {
        return (
            <div className={"container"}>
                <h3>The user permissions are as follows</h3>
                <div><h4>SHC:</h4></div>
                Parent: Can do these from anywhere. No location restriction
                <ul>
                    <li>open/close windows</li>
                    <li>unlock doors</li>
                    <li>open/close garage</li>
                    <li>turn on/off lights</li>
                    <li>turn on/off lights automode</li>
                </ul>

                Child: Only if they are in the room.
                <ul>
                    <li>turn on/off lights</li>
                    <li>lock/unlock doors</li>
                    <li>open/close windows</li>
                    <li>turn on/off lights automode</li>
                </ul>
                Guest: Only if they are in the room.
                <ul>
                    <li>turn on/off lights</li>
                    <li>lock/unlock doors</li>
                    <li>open/close windows</li>
                    <li>turn on/off lights automode</li>
                </ul>
                Stranger: No permissions

                <h4>SHP:</h4>
                Parent:
                <ul>
                    <li>turn on/off away mode</li>
                    <li>set timer for lights on during away mode</li>
                    <li>set lights for away mode</li>
                </ul>
                Child:
                No permissions
                <ul></ul>
                Guest:
                No permissions
                <ul></ul>
                Stranger:
                No permissions
            </div>
        )
    }
}