import React from 'react';
import ReactDOM from 'react-dom';

export const GlassContainer = React.createClass({
    propTypes: {
        container: React.PropTypes.object.isRequired,
        show: React.PropTypes.func.isRequired,
    },
    render() {
        return (
            <div style={{ display: 'flex' }}>
                <img src="http://icons.iconarchive.com/icons/icons8/ios7/256/Transport-Bicycle-icon.png" style={{ width: 20, height: 20 }} />
                <span style={{ marginRight: 10 }}>{this.props.container.address}</span>
            </div>
        );
    }
});