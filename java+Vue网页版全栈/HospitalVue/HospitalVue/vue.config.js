module.exports = {
    lintOnSave: false, // 关闭eslint校验
    devServer: {
        host: "localhost",
        port: 8089,
        https: false,
        proxy: {
            '/api': {
                target: 'http://localhost:9999',
                changeOrigin: true,
                pathRewrite: { '^/api': '' }
            },       
            '/llm': {
                target: 'https://dify.alphalio.cn',
                changeOrigin: true,
                pathRewrite: { '^/llm': '/v1/chat-messages' },
                secure: false,
                onProxyReq: (proxyReq) => {
                    // Ensure the proxy doesn't change the HTTP method
                    if (proxyReq.method === 'GET') {
                        proxyReq.method = 'POST';
                    }
                },
                logLevel: 'debug' // Add this line to log debug info
            }
        },
        overlay: { // 关闭eslint校验
            warning: false,
            errors: false
        },
    }
};

//设置代理解决跨域问题