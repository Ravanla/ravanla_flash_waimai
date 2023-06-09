'use strict'
// const path = require('path')
const config_client = require('../config_client')
// const ExtractTextPlugin = require('extract-text-webpack-plugin')
const packageConfig = require('../package.json')
// #############################################################end

var path = require('path')
var config = require('../config')
var ExtractTextPlugin = require('extract-text-webpack-plugin')

exports.assetsPath = function(_path) {
    var assetsSubDirectory = process.env.NODE_ENV === 'production' ? config.build.assetsSubDirectory : config.dev.assetsSubDirectory
    return path.posix.join(assetsSubDirectory, _path)
}

exports.cssLoaders = function(options) {
    options = options || {}
        // generate loader string to be used with extract text plugin
    function generateLoaders(loaders) {
        var sourceLoader = loaders.map(function(loader) {
            var extraParamChar
            if (/\?/.test(loader)) {
                loader = loader.replace(/\?/, '-loader?')
                extraParamChar = '&'
            } else {
                loader = loader + '-loader'
                extraParamChar = '?'
            }
            return loader + (options.sourceMap ? extraParamChar + 'sourceMap' : '')
        }).join('!')

        // (which is the case during production build)
        if (options.extract) {
            return ExtractTextPlugin.extract('vue-style-loader', sourceLoader)
        } else {
            return ['vue-style-loader', sourceLoader].join('!')
        }
    }

    // http://vuejs.github.io/vue-loader/en/configurations/extract-css.html
    return {
        css: generateLoaders(['css']),
        postcss: generateLoaders(['css']),
        less: generateLoaders(['css', 'less']),
        sass: generateLoaders(['css', 'sass?indentedSyntax']),
        scss: generateLoaders(['css', 'sass']),
        stylus: generateLoaders(['css', 'stylus']),
        styl: generateLoaders(['css', 'stylus'])
    }
}

// Generate loaders for standalone style files (outside of .vue)
exports.styleLoaders = function(options) {
    var output = []
    var loaders = exports.cssLoaders(options)
    for (var extension in loaders) {
        var loader = loaders[extension]
        output.push({
            test: new RegExp('\\.' + extension + '$'),
            loader: loader
        })
    }
    return output
}
// #############################################################start


// exports.assetsPath = function (_path) {
//   const assetsSubDirectory = process.env.NODE_ENV === 'production'
//     ? config_client.build.assetsSubDirectory
//     : config_client.dev.assetsSubDirectory
//
//   return path.posix.join(assetsSubDirectory, _path)
// }
//
// exports.cssLoaders = function (options) {
//   options = options || {}
//
//   const cssLoader = {
//     loader: 'css-loader',
//     options: {
//       sourceMap: options.sourceMap
//     }
//   }
//
//   const postcssLoader = {
//     loader: 'postcss-loader',
//     options: {
//       sourceMap: options.sourceMap
//     }
//   }
//
//   // generate loader string to be used with extract text plugin
//   function generateLoaders (loader, loaderOptions) {
//     const loaders = options.usePostCSS ? [cssLoader, postcssLoader] : [cssLoader]
//
//     if (loader) {
//       loaders.push({
//         loader: loader + '-loader',
//         options: Object.assign({}, loaderOptions, {
//           sourceMap: options.sourceMap
//         })
//       })
//     }
//
//     // Extract CSS when that option is specified
//     // (which is the case during production build)
//     if (options.extract) {
//       return ExtractTextPlugin.extract({
//         use: loaders,
//         fallback: 'vue-style-loader'
//       })
//     } else {
//       return ['vue-style-loader'].concat(loaders)
//     }
//   }
//
//   // https://vue-loader.vuejs.org/en/configurations/extract-css.html
//   return {
//     css: generateLoaders(),
//     postcss: generateLoaders(),
//     less: generateLoaders('less'),
//     sass: generateLoaders('sass', { indentedSyntax: true }),
//     scss: generateLoaders('sass'),
//     stylus: generateLoaders('stylus'),
//     styl: generateLoaders('stylus')
//   }
// }
//
// // Generate loaders for standalone style files (outside of .vue)
// exports.styleLoaders = function (options) {
//   const output = []
//   const loaders = exports.cssLoaders(options)
//
//   for (const extension in loaders) {
//     const loader = loaders[extension]
//     output.push({
//       test: new RegExp('\\.' + extension + '$'),
//       use: loader
//     })
//   }
//
//   return output
// }
//
// exports.createNotifierCallback = () => {
//   const notifier = require('node-notifier')
//
//   return (severity, errors) => {
//     if (severity !== 'error') return
//
//     const error = errors[0]
//     const filename = error.file && error.file.split('!').pop()
//
//     notifier.notify({
//       title: packageConfig.name,
//       message: severity + ': ' + error.name,
//       subtitle: filename || '',
//       icon: path.join(__dirname, 'logo.png')
//     })
//   }
// }
