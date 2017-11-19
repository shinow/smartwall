const Path = require('path');
const Webpack = require("webpack");
const ExtractTextPlugin = require('extract-text-webpack-plugin');

function resolve(dir) {
    return Path.join(__dirname, dir);
}


module.exports = {
    entry: {
        base_info: './src/js/base_info',
        vendors: './src/vendors'
    },
    output: {
        path: resolve('../dist'),
        filename: '[name].js',
        chunkFilename: '[name].chunk.js'
    },
    module: {
        rules: [{
            test: /\.vue$/,
            loader: 'vue-loader',
            options: {
                loaders: {

                    less: ExtractTextPlugin.extract({
                        use: ['css-loader?minimize', 'autoprefixer-loader', 'less-loader'],
                        fallback: 'vue-style-loader'
                    }),

                    css: ExtractTextPlugin.extract({
                        use: ['css-loader', 'autoprefixer-loader'],
                        fallback: 'vue-style-loader'
                    })
                }
            }
        }, {
            test: /iview\/.*?js$/,
            loader: 'babel-loader'
        }, {
            test: /\.js$/,
            loader: 'babel-loader',
            exclude: /node_modules/
        }, {
            //     test: /\.css$/,
            //     loader: 'style-loader!css-loader'
            // }, {
            test: /\.css$/,
            use: ExtractTextPlugin.extract({
                use: ['css-loader?minimize', 'autoprefixer-loader'],
                fallback: 'style-loader'
            })
        }, {
            test: /\.less/,
            use: ExtractTextPlugin.extract({
                use: ['autoprefixer-loader', 'less-loader'],
                fallback: 'style-loader'
            })
        }, {
            test: /\.scss$/,
            loader: 'style!css!sass?sourceMap'
        }, {
            test: /\.(gif|jpg|png)\??.*$/,
            loader: 'url-loader?limit=8192&name=images/[hash:8].[name].[ext]'
        }, {
            test: /\.(woff|svg|eot|ttf)\??.*$/,
            loader: 'url-loader?limit=8192&name=fonts/[name].[ext]'
        }, {
            test: /\.(html|tpl)$/,
            loader: 'html-loader'
        }]
    },
    plugins: [
        new Webpack.BannerPlugin("黄奇制作"),
        new Webpack.optimize.CommonsChunkPlugin({
            name: 'vendors',
            filename: 'vendors.js'
        }),
        new ExtractTextPlugin("baseinfo.css"),
    ],
    resolve: {
        extensions: ['.js', '.vue'],
        alias: {
            'vue': 'vue/dist/vue.esm.js',
            '@': resolve('../src')
        }
    }
}