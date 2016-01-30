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
        }, {
            test: /\.less$/,
            loader: 'style!css?modules!postcss!less'
        }]
    },

    postcss: [
        require('autoprefixer')
    ],

    devtool: 'eval-source-map',

    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, "app/index.html")
        })
    ],

    devServer: {
        colors: true,
        historyApiFallback: true,
        inline: true
    }
};

module.exports = config;
