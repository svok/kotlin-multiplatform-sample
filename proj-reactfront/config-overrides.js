// module.exports = function override(config, env) {
//     //do stuff with the webpack config...
//     return config;
// };

const path = require('path');

module.exports = {
    paths: function (paths, env) {
        paths.appIndexJs = path.resolve(__dirname, 'build/src/index.js');
        paths.appSrc = path.resolve(__dirname, 'build/src');
        paths.appBuild = path.resolve(__dirname, 'dist');
        paths.appPublic = path.resolve(__dirname, 'src/main/resources');
        paths.appHtml = path.resolve(__dirname, 'src/main/resources/index.html');
        return paths;
    },
};
