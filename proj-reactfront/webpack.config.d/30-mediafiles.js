
// In your webpack config:
config.module.rules.push(
    // ** ADDING/UPDATING LOADERS **
    // The "file" loader handles all assets unless explicitly excluded.
    // The `exclude` list *must* be updated with every change to loader extensions.
    // When adding a new loader, you must add its `test`
    // as a new entry in the `exclude` list in the "file" loader.
    //
    // "file" loader makes sure those assets end up in the `build` folder.
    // When you `import` an asset, you get its filename.
    {
        exclude: [
            /\.html$/,
            /\.(js|jsx)$/,
            /\.css$/,
            /\.json$/,
            /\.bmp$/,
            /\.gif$/,
            /\.jpe?g$/,
            /\.png$/,
        ],
        loader: require.resolve('file-loader'),
        options: {
            name: 'static/media/[name].[hash:8].[ext]',
        },
    },
);
