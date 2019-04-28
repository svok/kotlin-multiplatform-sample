//const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const InterpolateHtmlPlugin = require('react-dev-utils/InterpolateHtmlPlugin');
const WatchMissingNodeModulesPlugin = require('react-dev-utils/WatchMissingNodeModulesPlugin');
const ManifestPlugin = require('webpack-manifest-plugin');

// Webpack config
var publicUrl = '/';

config.plugins.push(
    // Generates an `index.html` file with the <script> injected.
    new HtmlWebpackPlugin({
        inject: true,
        template: path.resolve('../src/main/web/index.html'),
    }),
    // Makes the public URL available as %PUBLIC_URL% in index.html, e.g.:
    // <link rel="shortcut icon" href="%PUBLIC_URL%/favicon.ico">
    new InterpolateHtmlPlugin(HtmlWebpackPlugin, {
        PUBLIC_URL: publicUrl,
        // You can pass any key-value pairs, this was just an example.
        // WHATEVER: 42 will replace %WHATEVER% with 42 in index.html.
    }),

    // If you require a missing module and then `npm install` it, you still have
    // to restart the development server for Webpack to discover it. This plugin
    // makes the discovery automatic so you don't have to restart.
    // See https://github.com/facebook/create-react-app/issues/186
    new WatchMissingNodeModulesPlugin(path.resolve('node_modules')),
);

config.plugins.unshift(
    // Generate a manifest file which contains a mapping of all asset filenames
    // to their corresponding output file so that tools can pick it up without
    // having to parse `index.html`.
    new ManifestPlugin({
      fileName: 'asset-manifest.json',
    }),
);

// const getCSSModuleLocalIdent = require('react-dev-utils/getCSSModuleLocalIdent');

// In your webpack config:
config.module.rules.push(
    // {
    //     test: /\.module\.css$/,
    //     use: [
    //         require.resolve('style-loader'),
    //         {
    //             loader: require.resolve('css-loader'),
    //             options: {
    //                 importLoaders: 1,
    //                 modules: true,
    //                 getLocalIdent: getCSSModuleLocalIdent,
    //             },
    //         },
    //         {
    //             loader: require.resolve('postcss-loader'),
    //             options: {
    //                 ident: 'postcss', // https://webpack.js.org/guides/migrating/#complex-options
    //                 plugins: () => [
    //                     require('postcss-flexbugs-fixes'),
    //                     autoprefixer({
    //                         browsers: [
    //                             '>1%',
    //                             'last 4 versions',
    //                             'Firefox ESR',
    //                             'not ie < 9', // React doesn't support IE8 anyway
    //                         ],
    //                         flexbox: 'no-2009',
    //                     }),
    //                 ],
    //             },
    //         },
    //     ],
    // },
      // ** ADDING/UPDATING LOADERS **
      // The "file" loader handles all assets unless explicitly excluded.
      // The `exclude` list *must* be updated with every change to loader extensions.
      // When adding a new loader, you must add its `test`
      // as a new entry in the `exclude` list in the "file" loader.

      // "file" loader makes sure those assets end up in the `build` folder.
      // When you `import` an asset, you get its filename.
      // {
      //   exclude: [
      //     /\.html$/,
      //     /\.(js|jsx)$/,
      //     /\.css$/,
      //     /\.json$/,
      //     /\.bmp$/,
      //     /\.gif$/,
      //     /\.jpe?g$/,
      //     /\.png$/,
      //   ],
      //   loader: require.resolve('file-loader'),
      //   options: {
      //     name: 'static/media/[name].[hash:8].[ext]',
      //   },
      // },
);

// config.resolve.alias = {
//     "src": "../src/main/kotlin"
// };
