import React from 'react';
import ReactDOM from 'react-dom';

export const BikeShelter = React.createClass({
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