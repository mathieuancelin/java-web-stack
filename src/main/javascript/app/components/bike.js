import React from 'react';
import ReactDOM from 'react-dom';

export const BikeShelter = React.createClass({
    propTypes: {
        shelter: React.PropTypes.object.isRequired,
        show: React.PropTypes.func.isRequired,
    },
    render() {
        return (
            <div style={{ display: 'flex' }}>
                <img src="http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/magic-marker-icons-food-beverage/115446-magic-marker-icon-food-beverage-drink-bottle1.png" style={{ width: 20, height: 20 }} />
                <span style={{ marginRight: 10 }}>{this.props.shelter.address}</span>
            </div>
        );
    }
});