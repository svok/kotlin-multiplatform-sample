const path = require('path');

config.module.rules.push(
    {
        test: /\.css$/,
        // use: ['style-loader', 'css-loader'],
        loader: 'style-loader!css-loader',
        // include: [
        // //     path.resolve("../src/main/web")
        //     path.resolve('../src/main/web/index.css')
        // ],
        options: {
            modules: true,
            context: path.resolve(__dirname, '../src/main/web'),
        },

    },
    {
        test: /\.(png|jpe?g|gif|svg|eot|ttf|woff|woff2)$/,
        loader: 'url-loader',
        options: {
            limit: 10000,
        },
    },
);

