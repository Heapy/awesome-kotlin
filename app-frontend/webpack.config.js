const path = require("path");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const CssMinimizerPlugin = require("css-minimizer-webpack-plugin");

module.exports = function (options = {}) {
  // Settings
  // --env.NODE_ENV root --env.SOURCE_MAP source-map ...
  const NODE_ENV = options.NODE_ENV || "development"; // "production"
  const SOURCE_MAP = options.SOURCE_MAP || "eval-source-map"; // "source-map"

  const APP_DIR = path.resolve(__dirname, "app");

  return {
    entry: {
      app: [
        path.resolve(APP_DIR, "src", "index.tsx"),
      ],
    },
    output: {
      path: path.resolve(__dirname, "dist"),
      filename: "[name].js?[contenthash]",
      chunkFilename: "[name].bundle.js?[chunkhash]",
      publicPath: "/",
      clean: true,
    },
    optimization: {
      minimizer: [
        "...",
        new CssMinimizerPlugin(),
      ],
      splitChunks: {
        chunks: "all",
      },
    },
    resolve: {
      extensions: [".ts", ".tsx", ".js"],
    },
    bail: false,
    devtool: SOURCE_MAP,
    module: {
      rules: [
        {
          test: /\.tsx?$/,
          use: [
            {
              loader: "ts-loader",
            },
          ],
        },
        {
          test: /\.scss$/,
          use: [
            MiniCssExtractPlugin.loader,
            {
              loader: "css-loader",
            },
            {
              loader: "sass-loader",
            },
          ],
        },
        {
          test: /\.(png|jpg|gif|svg)$/,
          type: "asset",
          parser: {
            dataUrlCondition: {
              maxSize: 1024,
            },
          },
        }
      ]
    },
    plugins: createListOfPlugins({NODE_ENV}, APP_DIR),
  }
};

function createListOfPlugins({NODE_ENV}, APP_DIR) {
  return [
    new MiniCssExtractPlugin({
      filename: "[name]_[contenthash].css",
      chunkFilename: "[id]_[contenthash].css",
    }),
    new HtmlWebpackPlugin({
      template: path.resolve(APP_DIR, "index.html"),
      hash: true,
    }),
    new CopyWebpackPlugin({
      patterns: [
        {from: path.resolve(APP_DIR, "icons")},
      ],
    }),
    new webpack.DefinePlugin({
      "process.env": {
        "NODE_ENV": JSON.stringify(NODE_ENV),
      },
    }),
  ];
}
