import React, { Component } from 'react';

class UserInfo extends Component {

  constructor(props) {
    super(props);
    this.state = {
      name: "",
      username: "",
      email: "",
      id: ""
    };
    this.props.keycloak.loadUserInfo().then(userInfo => {
        this.setState({name: userInfo.name, username: userInfo.preferred_username ,email: userInfo.email, id: userInfo.sub})
    });
  }

  render() {
    return (
      <div className="UserInfo">
        <p>Name: {this.state.name}</p>
        <p>Username: {this.state.username}</p>
        <p>Email: {this.state.email}</p>
        <p>ID: {this.state.id}</p>
      </div>
    );
  }
}
export default UserInfo;