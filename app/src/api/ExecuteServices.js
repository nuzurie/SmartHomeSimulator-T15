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

    updateSimulationContext(simulation){
        return axios.put(`/api/simulation`, simulation)
    }

    updateSimulationDetails(simulation){
        // return axios.put(`/api/simulation/update`, simulation)
    }

    getSimulation(){
        return axios.get(`/api/simulation`)
    }


    loginSimulationUser(user) {
        return axios.put(`/api/simulation/loginUser/${user.id}`)
    }

    saveUsers(){
        return axios.get('/api/users/save')
    }

    loadUsers(){
        return axios.get('/api/users/load')
    }

    toggleAutoMode(){
        return axios.put('/api/simulation/autoMode')
    }

    toggleAwayMode(){
        return axios.put('/api/security/awayMode')
    }

    setTimeMultiplier(multiplier){
        return axios.put(`/api/simulation/time-multiplier/${multiplier}`)
    }

    callAuthoritiesTimer(timer){
        return axios.put(`/api/simulation/call-timer/${timer}`)
    }

    callAuthorities(){
        return axios.get('/api/simulation/callAuthorities')
    }

    getLightsForUsers(){
        return axios.get('/api/simulation/rooms-for-user')
    }

    getWindowsForUsers(){
        return axios.get('/api/simulation/windows-for-user')
    }

    getDoorsForUsers(){
        return axios.get('/api/simulation/doors-for-user')
    }

    toggleLight(light){
        return axios.put('/api/simulation/toggleLight', light)
    }

    toggleWindow(window, action){
        return axios.put(`/api/simulation/toggleWindow/${action}`, window)
    }

    toggleDoor(door, action){
        return axios.put(`/api/simulation/toggleDoor/${action}`, door)
    }

    placeUser(room, user){
        return axios.put(`/api/simulation/addUserToRoom/${room.id}`, user)
    }

    removeUser(room, user){
        return axios.put(`/api/simulation/removeUsersFromRoom/${room.id}`, user)
    }

    awayModeLightsTimes(times){
        return axios.post('/api/simulation/awaymode-lights-time', times)
    }

    awayModeLights(lights){
        return axios.post('/api/simulation/awaymode-lights', lights)
    }

    setZoneIntervals(timeIntervals){
        return axios.post('/api/heating/zones/timeIntervals', timeIntervals)
    }

    getAllRooms(){
        return axios.get('/api/simulation/all-rooms')
    }

    setZoneAndTimeIntervals(zoneTimeNumbers){
        return axios.post('/api/heating/zone-interval-numbers', zoneTimeNumbers)
    }

    getHeating(){
        return axios.get('/api/heating/')
    }

    setZoneRoomsAndTemp(zonesRooms){
        return axios.post('/api/heating/zone-rooms', zonesRooms)
    }
}

export default new ExecuteService()