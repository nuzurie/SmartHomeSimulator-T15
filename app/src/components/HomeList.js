import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import NavigationBar from './NavigationBar';
import { Link, withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';


class HomeList extends Component {
    
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {homes: [], csrfToken: cookies.get('XSRF-TOKEN'), isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('api/homes', {credentials: 'include'})
            .then(response => response.json())
            .then(data => this.setState({homes: data, isLoading: false}))
            .catch(() => this.props.history.push('/'));
    }

    async remove(id) {
        await fetch(`/api/home/${id}`, {
            method: 'DELETE',
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatedHomes = [...this.state.homes].filter(i => i.id !== id);
            this.setState({homes: updatedHomes});
        });
    }

    render() {
        const {homes, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const homeList = homes.map(home => {
            const address = `${home.address || ''}`;
            return <tr key={home.id}>
                <td style={{whiteSpace: 'nowrap'}}>{home.name}</td>
                <td>{address}</td>
                <td>{home.rooms.map(room => {
                    return <div key={room.id}>
                        <td>{room.name}</td>
                    </div>
                })}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/home/" + home.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(home.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <NavigationBar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/home/new">Add Home</Button>
                    </div>
                    <h3>My JUG Tour</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Location</th>
                            <th>Events</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {homeList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default withCookies(withRouter(HomeList));
