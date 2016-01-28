import React from 'react';
import ReactDOM from 'react-dom';

const GlassContainer = React.createClass({
    propTypes: {
        container: React.PropTypes.object.isRequired,
        show: React.PropTypes.func.isRequired,
    },
    render() {
        return (
            <li>
                <img src="http://icons.iconarchive.com/icons/icons8/ios7/256/Transport-Bicycle-icon.png" style={{ width: 20, height: 20 }} />
                <p>{this.props.container.address}</p>
            </li>
        );
    }
});

const BikeShelter = React.createClass({
    propTypes: {
        shelter: React.PropTypes.object.isRequired,
        show: React.PropTypes.func.isRequired,
    },
    render() {
        return (
            <li>
                <img src="http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/magic-marker-icons-food-beverage/115446-magic-marker-icon-food-beverage-drink-bottle1.png" style={{ width: 20, height: 20 }} />
                <p>{this.props.shelter.address}</p>
            </li>
        );
    }
});

const App = React.createClass({
    getInitialState() {
        return {
            shelters: [],
            containers: [],
        };
    },
    componentDidMount() {
      fetch('http://localhost:8887/service')
          .then(r => r.json())
          .then(data => this.setState({ shelters: data.bikerShelters, containers: data.glassContainers }))
    },
    show() {

    },
    render() {
        return (
            <div>
                <h3>Bike Shelters</h3>
                <ul>
                    {this.state.shelters.map(s => <BikeShelter shelter={s} show={this.show} />)}
                </ul>
                <h3>Glass Containers</h3>
                <ul>
                    {this.state.containers.map(c => <GlassContainer container={c} show={this.show} />)}
                </ul>
            </div>
        );
    }
});

ReactDOM.render(<App />, document.getElementById('app'));
