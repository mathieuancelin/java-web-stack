import React from 'react';
import ReactDOM from 'react-dom';
import { GlassContainer } from './glass';
import { BikeShelter } from './bike';

export const App = React.createClass({
    getInitialState() {
        return {
            shelters: [],
            containers: [],
        };
    },
    componentDidMount() {
        fetch('http://localhost:8886/proxy?url=http://localhost:8887/service')
            .then(r => r.json())
            .then(data => this.setState({ shelters: data.bikerShelters, containers: data.glassContainers }))
    },
    show() {

    },
    render() {
        return (
            <div>
                <h3>Bike Shelters</h3>
                <div style={{ display: 'flex', flexDirection: 'column' }}>
                    {this.state.shelters.map(s => <BikeShelter shelter={s} show={this.show} />)}
                </div>
                <h3>Glass Containers</h3>
                <div style={{ display: 'flex', flexDirection: 'column' }}>
                    {this.state.containers.map(c => <GlassContainer container={c} show={this.show} />)}
                </div>
            </div>
        );
    }
});