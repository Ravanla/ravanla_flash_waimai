// https://github.com/shelljs/shelljs
// #############################################################start
// 'use strict'
// require('./check-versions')()
//
// process.env.NODE_ENV = 'production'
// #############################################################end

require('shelljs/global')
env.NODE_ENV = 'production'

var path = require('path')
var config = require('../config')
var ora = require('ora')
var webpack = require('webpack')
var webpackConfig = require('./webpack.prod.conf')


// #############################################################start
// const rm = require('rimraf')
// const chalk = require('chalk')
// const config_client = require('../config_client')
// const webpackConfig = require('./webpack.prod.conf')
// #############################################################end


var spinner = ora('building for production...')
spinner.start()

var assetsPath = path.join(config.build.assetsRoot, config.build.assetsSubDirectory)
rm('-rf', assetsPath)
mkdir('-p', assetsPath)
cp('-R', 'static/*', assetsPath)

webpack(webpackConfig, function(err, stats) {
    spinner.stop()
    if (err) throw err
    process.stdout.write(stats.toString({
        colors: true,
        modules: false,
        children: false,
        chunks: false,
        chunkModules: false
    }) + '\n')
})
// #############################################################start
// rm(path.join(config_client.build.assetsRoot, config_client.build.assetsSubDirectory), err => {
//   if (err) throw err
//   webpack(webpackConfig, (err, stats) => {
//     spinner.stop()
//     if (err) throw err
//     process.stdout.write(stats.toString({
//       colors: true,
//       modules: false,
//       children: false, // If you are using ts-loader, setting this to true will make TypeScript errors show up during build.
//       chunks: false,
//       chunkModules: false
//     }) + '\n\n')
//
//     if (stats.hasErrors()) {
//       console.log(chalk.red('  Build failed with errors.\n'))
//       process.exit(1)
//     }
//
//     console.log(chalk.cyan('  Build complete.\n'))
//     console.log(chalk.yellow(
//       '  Tip: built files are meant to be served over an HTTP server.\n' +
//       '  Opening index.html over file:// won\'t work.\n'
//     ))
//   })
// })
// #############################################################end
