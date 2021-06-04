const path = require("path");

module.exports = {
    outputDir: path.resolve(__dirname, "../main/resources/static"),
    indexPath: path.resolve(__dirname, "../main/resources/static/index.html"),

    devServer: {
        proxy: {
            "": {
                // target: "http://localhost:8080",
                target: "http://52.79.45.29:8080",

                ws: true,
                changeOrigin: true,
                disableHostCheck: true,
            },
        },
    },

    transpileDependencies: ["vuetify"],
};
