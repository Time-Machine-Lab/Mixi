const GithubConfig={
    //请求授权地址
    userAuthorizationUri:"https://github.com/login/oauth/authorize",
    //accessToken请求地址
    accessTokenUri : "https://github.com/login/oauth/access_token",
    //用户信息请求地址
    userInfoUri:"https://api.github.com/user",
    //登出请求地址
    logoutUri:"https://github.com/logout",
    //项目地址
    localuri :"http://localhost:5173",
    //回调地址
    redirect_uri : "http://localhost:5173/LoginView",
    //案例资源服务器地址
    resUri:"",
    //客户端相关标识，请从认证服务器申请
    clientId: "Ov23liTDfyMWm3EU0jqN",
    client_secret:"a883b9c0080a2e5b2cd5b758b3d9d708645e658e",
    //申请的权限范围
    scope:"user",
    //可选参数，客户端的当前状态，可以指定任意值，用于校验，此次案例不做相关认证
    state:"",
    //一些固定的请求参数
    response_type:"token",
    grant_type : "authorization_code",
    code:"",
}

export default GithubConfig;
