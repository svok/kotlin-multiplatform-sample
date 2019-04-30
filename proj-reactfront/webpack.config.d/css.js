const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
// module.exports = {
//     entry: { main: './src/index.js' },
//     output: {
//         path: path.resolve(__dirname, 'dist'),
//         filename: 'main.js'
//     },
//     module: {
//         rules: [
//             // {
//             //     test: /\.js$/,
//             //     exclude: /node_modules/,
//             //     use: {
//             //         loader: "babel-loader"
//             //     }
//             // },
//             {
//                 test: /\.css$/,
//                 use: ExtractTextPlugin.extract(
//                     {
//                         fallback: 'style-loader',
//                         use: ['css-loader']
//                     })
//             }
//         ]
//     },
//     plugins: [
//         new ExtractTextPlugin({filename: 'style.css'})
//     ]
// };

// config.module.rules.push(
//     {
//         test: /\.css$/,
//         use: ExtractTextPlugin.extract(
//             {
//                 fallback: 'style-loader',
//                 use: ['css-loader', 'sass-loader']
//             })
//     }
// );
// config.plugins.push(
//     new ExtractTextPlugin({filename: 'style.css'})
// );
