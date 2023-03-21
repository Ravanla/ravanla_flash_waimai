// 'use strict'
// // const utils = require('./utils')
// // const webpack = require('webpack')
// const config_client = require('../config_client')
// // const merge = require('webpack-merge')
// const path = require('path')
// // const baseWebpackConfig = require('./webpack.base.conf')
// const CopyWebpackPlugin = require('copy-webpack-plugin')
// // const HtmlWebpackPlugin = require('html-webpack-plugin')
// const FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin')
// const portfinder = require('portfinder')
// const HOST = process.env.HOST
// const PORT = process.env.PORT && Number(process.env.PORT)
// #############################################################end

var config = require('../config')
var webpack = require('webpack')
var merge = require('webpack-merge')
var utils = require('./utils')
var baseWebpackConfig = require('./webpack.base.conf')
var HtmlWebpackPlugin = require('html-webpack-plugin')

// add hot-reload related code to entry chunks
Object.keys(baseWebpackConfig.entry).forEach(function(name) {
    baseWebpackConfig.entry[name] = ['./build/dev-client'].concat(baseWebpackConfig.entry[name])
})

module.exports = merge(baseWebpackConfig, {
    module: {
        loaders: utils.styleLoaders({
            sourceMap: config.dev.cssSourceMap
        })
    },
    // eval-source-map is faster for development
    devtool: '#eval-source-map',
    plugins: [
        new webpack.DefinePlugin({
            'process.env': config.dev.env
        }),
        // https://github.com/glenjamin/webpack-hot-middleware#installation--usage
        new webpack.optimize.OccurenceOrderPlugin(),
        new webpack.HotModuleReplacementPlugin(),
        new webpack.NoErrorsPlugin(),
        // https://github.com/ampedandwired/html-webpack-plugin
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'index.html',
            favicon: 'favicon.ico',
            inject: true
        })
    ]
})

// #############################################################start
// const devWebpackConfig = merge(baseWebpackConfig, {
//   module: {
//     rules: utils.styleLoaders({ sourceMap: config_client.dev.cssSourceMap, usePostCSS: true }),
//     loaders: utils.styleLoaders({
//       sourceMap: config.dev.cssSourceMap
//     })
//   },
//   // cheap-module-eval-source-map is faster for development
//   devtool: config_client.dev.devtool,
//
//   // these devServer options should be customized in /config/index.js
//   devServer: {
//     clientLogLevel: 'warning',
//     historyApiFallback: {
//       rewrites: [
//         { from: /.*/, to: path.posix.join(config_client.dev.assetsPublicPath, 'index.html') },
//       ],
//     },
//     hot: true,
//     contentBase: false, // since we use CopyWebpackPlugin.
//     compress: true,
//     host: HOST || config_client.dev.host,
//     port: PORT || config_client.dev.port,
//     open: config_client.dev.autoOpenBrowser,
//     overlay: config_client.dev.errorOverlay
//       ? { warnings: false, errors: true }
//       : false,
//     publicPath: config_client.dev.assetsPublicPath,
//     proxy: config_client.dev.proxyTable,
//     quiet: true, // necessary for FriendlyErrorsPlugin
//     watchOptions: {
//       poll: config_client.dev.poll,
//     }
//   },
//   plugins: [
//     new webpack.DefinePlugin({
//       'process.env': require('../config_client/dev.env')
//     }),
//     new webpack.HotModuleReplacementPlugin(),
//     new webpack.NamedModulesPlugin(), // HMR shows correct file names in console on update.
//     new webpack.NoEmitOnErrorsPlugin(),
//     new webpack.optimize.OccurenceOrderPlugin(),
//     // new webpack.HotModuleReplacementPlugin(),
//     new webpack.NoErrorsPlugin(),
//     // // https://github.com/ampedandwired/html-webpack-plugin
//     // new HtmlWebpackPlugin({
//     //   filename: 'index.html',
//     //   template: 'index.html',
//     //   favicon: 'favicon.ico',
//     //   inject: true
//     // })
//     // https://github.com/ampedandwired/html-webpack-plugin
//     new HtmlWebpackPlugin({
//       filename: 'index.html',
//       template: 'index.html',
//       favicon: 'favicon.ico',
//       inject: true
//     }),
//     // copy custom static assets
//     new CopyWebpackPlugin([
//       {
//         from: path.resolve(__dirname, '../static'),
//         to: config_client.dev.assetsSubDirectory,
//         ignore: ['.*']
//       }
//     ])
//   ]
// })
//
// module.exports = new Promise((resolve, reject) => {
//   portfinder.basePort = process.env.PORT || config_client.dev.port
//   portfinder.getPort((err, port) => {
//     if (err) {
//       reject(err)
//     } else {
//       // publish the new Port, necessary for e2e tests
//       process.env.PORT = port
//       // add port to devServer config
//       devWebpackConfig.devServer.port = port
//
//       // Add FriendlyErrorsPlugin
//       devWebpackConfig.plugins.push(new FriendlyErrorsPlugin({
//         compilationSuccessInfo: {
//           messages: [`Your application is running here: http://${devWebpackConfig.devServer.host}:${port}`],
//         },
//         onErrors: config_client.dev.notifyOnErrors
//           ? utils.createNotifierCallback()
//           : undefined
//       }))
//
//       resolve(devWebpackConfig)
//     }
//   })
// })
