var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

var config = {
    entry: path.resolve(__dirname, 'app/src/main.js'),
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: "bundle.js"
    },


    module: {
        loaders: [{
            test: /\.json$/,
            loader: "json"
        }, {
            test: /\.jsx?$/,
            exclude: /node_modules/,
            loader: 'babel'
        }]
    },


    devtool: 'eval-source-map',

    plugins: [
        new HtmlWebpackPlugin({
            template: __dirname + "/app/index.html"
        })
    ],

    devServer: {
        colors: true,
        historyApiFallback: true,
        inline: true
    }
};

module.exports = config;
