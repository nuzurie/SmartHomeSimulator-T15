import React from 'react';
import axios from 'axios';

class ExecuteService {
    getAllUsers(){
        return axios.get('/api/users')
    }

    postUser(user){
        return axios.post('/api/user', user)
    }

    deleteUser(user){
        return axios.delete(`/api/user/${user.id}`)
    }

    updateUser(user){
        console.log(user)
        return axios.put(`/api/user/${user.id}`, user)
    }

    getUser(id){
        return axios.get(`/api/user/${id}`)
    }

    updateSimulation(simulation){
        return axios.put(`/api/simulation`, simulation)
    }

    updateUserRooms(simulation){

        // axios.delete(`/api/simulation`)
        //     .then(response => console.log(response))
        //     .catch(error => console.log(error))
        return axios.put(`/api/simulation/user-rooms`, simulation)
    }

    getSimulation(){
        return axios.get(`/api/simulation`)
    }


}

export default new ExecuteService()