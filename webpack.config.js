const path = require("path");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const autoprefixer = require("autoprefixer");

module.exports = function (options = {}) {
  // Settings
  // --env.NODE_ENV root --env.SOURCE_MAP source-map ...
  const NODE_ENV = options.NODE_ENV || "development"; // "production"
  const SOURCE_MAP = options.SOURCE_MAP || "eval-source-map"; // "source-map"

  console.log(`
Build started with following configuration:
===========================================
→ NODE_ENV: ${NODE_ENV}
→ SOURCE_MAP: ${SOURCE_MAP}
`);

  return {
    entry: {
      vendor: [
        "babel-polyfill"
      ],
      app: [
        path.resolve(__dirname, "app", "src", "main.tsx")
      ]
    },
    output: {
      path: path.resolve(__dirname, "dist"),
      filename: "[name].js?[hash]",
      publicPath: "/"
    },
    resolve: {
      extensions: [".ts", ".tsx", ".js"]
    },
    bail: false,
    devtool: SOURCE_MAP,
    module: {
      rules: [{
        test: /\.tsx?$/,
        exclude: /node_modules/,
        loader: "ts-loader"
      }, {
        test: /\.less$/,
        use: [{
          loader: "style-loader"
        }, {
          loader: "css-loader",
          options: {
            modules: true,
            localIdentName: "[name]_[local]"
          }
        }, {
          loader: "postcss-loader",
          options: {
            plugins: function () {
              return [
                autoprefixer
              ];
            }
          }
        }, {
          loader: "less-loader"
        }]
      }, {
        test: /\.(png|jpg|gif|svg)$/,
        loader: "url-loader",
        options: {
          limit: 32768
        }
      }]
    },
    plugins: createListOfPlugins({NODE_ENV}),
    devServer: {
      stats: {
        chunkModules: false,
        colors: true
      },
      historyApiFallback: true,
      inline: false
    }
  }
};

function createListOfPlugins({NODE_ENV}) {
  const plugins = [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, "app", "index.html"),
      favicon: path.resolve(__dirname, "app", "favicon.ico"),
      hash: true
    }),
    new webpack.DefinePlugin({
      "process.env": {
        "NODE_ENV": JSON.stringify(NODE_ENV)
      }
    })
  ];

  if (NODE_ENV === "production") {
    plugins.push(
      new webpack.optimize.CommonsChunkPlugin({
        name: "vendor",
        minChunks: 2
      })
    );
  }

  return plugins;
}
