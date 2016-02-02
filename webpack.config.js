var webpack = require('webpack');

var plugins = [
  new webpack.optimize.DedupePlugin(),
  new webpack.optimize.OccurenceOrderPlugin(),
  new webpack.DefinePlugin({
    '__DEV__': process.env.NODE_ENV === 'production',
    'process.env': {
      NODE_ENV: JSON.stringify(process.env.NODE_ENV || 'dev')
    }
  })
];
if (process.env.NODE_ENV === 'production') {
  plugins.push(new webpack.optimize.UglifyJsPlugin({
    compressor: {
      screw_ie8: true,
      warnings: false
    }
  }));
} else {
  plugins.push(new webpack.HotModuleReplacementPlugin());
  plugins.push(new webpack.NoErrorsPlugin());
}

module.exports = {
  output: {
    path: './src/main/resources/public',
    publicPath: '/assets/',
    filename: 'app.js',
    library: 'App',
    libraryTarget: 'umd'
  },
  devServer: {
    port: '8885'
  },
  entry: [
    './src/main/javascript/main.js'
  ],
  resolve: {
    extensions: ['', '.js', '.jsx', 'es6']
  },
  module: {
    loaders: [
      {
        test: /\.js|\.jsx|\.es6$/,
        exclude: /node_modules/,
        loader: 'babel-loader'
      }
    ]
  },
  plugins: plugins
};
