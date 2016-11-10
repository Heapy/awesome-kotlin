const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');

// Settings
// export NAME=VALUE
const NODE_ENV = process.env.NODE_ENV || 'development'; // 'production'
const SOURCE_MAP = process.env.SOURCE_MAP || 'eval-source-map'; // 'source-map'

console.log(`
Build started with following configuration:
===========================================
→ NODE_ENV: ${NODE_ENV}
→ SOURCE_MAP: ${SOURCE_MAP}
`);

const config = {
    entry: {
        vendor: [
            'babel-polyfill'
        ],
        app: [
            path.resolve(__dirname, 'app', 'src', 'main.tsx')
        ]
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].js?[hash]',
        publicPath: '/'
    },
    resolve: {
      extensions: ['', '.tsx', '.js']
    },
    module: {
        loaders: [{
            test: /\.json$/,
            loader: 'json'
        }, {
            test: /\.tsx?$/,
            exclude: /node_modules/,
            loader: 'ts'
        }, {
            test: /\.less$/,
            loader: 'style!css?modules&localIdentName=[name]_[local]!postcss!less'
        }, {
            test: /\.(png|jpg|gif|svg)$/,
            loader: 'url?limit=32768'
        }]
    },
    postcss: [
        require('autoprefixer')
    ],
    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, 'app', 'index.html'),
            favicon: path.resolve(__dirname, 'app', 'favicon.ico'),
            hash: true
        }),
        new webpack.DefinePlugin({
            'process.env': {
                'NODE_ENV': JSON.stringify(NODE_ENV)
            }
        })
    ],
    devtool: SOURCE_MAP,
    devServer: {
        colors: true,
        inline: false
    }
};

if (NODE_ENV === 'production') {
    config.plugins = [
        ...config.plugins,
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false
            }
        }),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor',
            minChunks: 2
        })
    ];
}

module.exports = config;
