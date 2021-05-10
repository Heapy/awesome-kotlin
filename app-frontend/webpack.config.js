const path = require("path");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");

module.exports = function (options = {}) {
  // Settings
  // --env.NODE_ENV root --env.SOURCE_MAP source-map ...
  const NODE_ENV = options.NODE_ENV || "development"; // "production"
  const SOURCE_MAP = options.SOURCE_MAP || "eval-source-map"; // "source-map"

  const APP_DIR = path.resolve(__dirname, "app");

  console.error(`
Build started with following configuration:
===========================================
→ NODE_ENV: ${NODE_ENV}
→ SOURCE_MAP: ${SOURCE_MAP}
`);

  return {
    entry: {
      app: [
        path.resolve(APP_DIR, "src", "main.tsx")
      ]
    },
    output: {
      path: path.resolve(__dirname, "dist"),
      filename: "[name].js?[contenthash]",
      chunkFilename: "[name].bundle.js?[chunkhash]",
      publicPath: "/"
    },
    optimization: {
      splitChunks: {
        chunks: "all"
      }
    },
    resolve: {
      extensions: [".ts", ".tsx", ".js"]
    },
    bail: false,
    devtool: SOURCE_MAP,
    module: {
      rules: [{
        test: /\.tsx?$/,
        use: [{
          loader: "ts-loader"
        }]
      }, {
        test: /\.less$/,
        use: [{
          loader: "style-loader"
        }, {
          loader: "css-loader"
        }, {
          loader: "postcss-loader",
          options: {
            postcssOptions: {
              plugins: [["autoprefixer"]]
            },
          }
        }, {
          loader: "less-loader"
        }]
      }, {
        test: /\.(png|jpg|gif|svg)$/,
        type: "asset",
        parser: {
          dataUrlCondition: {
            maxSize: 32768
          }
        }
      }]
    },
    plugins: createListOfPlugins({NODE_ENV}, APP_DIR),
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

function createListOfPlugins({NODE_ENV}, APP_DIR) {
  return [
    new HtmlWebpackPlugin({
      template: path.resolve(APP_DIR, "index.html"),
      hash: true
    }),
    new CopyWebpackPlugin({
      patterns: [
        {from: path.resolve(APP_DIR, "icons")}
      ]
    }),
    new webpack.DefinePlugin({
      "process.env": {
        "NODE_ENV": JSON.stringify(NODE_ENV)
      }
    })
  ];
}
