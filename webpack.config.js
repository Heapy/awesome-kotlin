var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
const isBuild = Boolean(process.env.build);

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
            loader: 'style!css?modules&localIdentName=[name]_[local]!postcss!less'
        }, {
            test: /\.(png|jpg|gif|svg)$/,
            loader: "url?limit=8192"
        }]
    },

    postcss: [
        require('autoprefixer')
    ],

    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, "app/index.html"),
            favicon: path.resolve(__dirname, "app/favicon.ico"),
            hash: true
        })
    ],

    devServer: {
        colors: true,
        historyApiFallback: true,
        inline: true
    }
};

if (isBuild) {
    config.devtool = 'source-map';
} else {
    config.devtool = 'eval-source-map';
}

module.exports = config;
