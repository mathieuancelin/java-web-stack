import React from 'react';

const icon = 'http://icons.iconarchive.com/icons/icons8/ios7/256/Transport-Bicycle-icon.png';

export const BikeShelter = React.createClass({
  propTypes: {
    shelter: React.PropTypes.object.isRequired,
    show: React.PropTypes.func.isRequired,
  },
  render() {
    return (
      <div style={{ display: 'flex' }}>
        <img src={icon} style={{ width: 20, height: 20 }} />
        <span style={{ marginRight: 10 }}>{this.props.shelter.address}</span>
      </div>
    );
  },
});
