config.module.rules.push({
  test: /\.(png|svg|jpg|jpeg|gif|ico|json)$/,
  exclude: /node_modules/,
  use: ['file-loader?name=[name].[ext]'] // ?name=[name].[ext] is only necessary to preserve the original file name
});

