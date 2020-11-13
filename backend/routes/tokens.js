const router = require('express').Router();
const jwt = require('jsonwebtoken');

const User = require('../models/User');

var refreshTokenList = [];

const createTokens = async (user) => {
    const accessToken = generateAccessToken({ id: user.id });

    const refreshToken = generateRefreshToken({ id: user.id });

    refreshTokenList.push(refreshToken);

    return Promise.all([accessToken, refreshToken]);
}

const deleteTokens = async (token) => {
    refreshTokenList = refreshTokenList.filter(params => params != token);
}

router.post('/', (req, res) =>{
    rToken = req.body.token;
    if(!rToken) return res.status(401).send("No Token Found");
    const isValidRefreshToken = refreshTokenList.includes(rToken);
    if(!isValidRefreshToken) return res.status(403).send("Logged Out");

    jwt.verify(rToken, process.env.REFRESH_TOKEN_SECRET, (err, user) => {
        if(err) return res.status(403).send("Logged Out");
        const accessToken = generateAccessToken({ id: user.id })
        return res.json({ accessToken: accessToken });
    })
})

router.post('/refresh', async (req, res) => {
    rToken = req.body.token; // refresh token
    if(!rToken) return res.status(401).send("No Token Found");
    try {
    const payload = jwt.verify(rToken, process.env.REFRESH_TOKEN_SECRET);
    const isValidRefreshToken = refreshTokenList.includes(rToken);
    if(!isValidRefreshToken) return res.status(403).send("Logged Out");
    
    deleteTokens(rToken);

    const refreshToken = generateRefreshToken({ id: payload.id })
    refreshTokenList.push(refreshToken);

    const user = await User.findById(payload.id);
    return res.json({ refreshToken: refreshToken, confirmed: user.confirmed });
    } catch(err) {
        const isValidRefreshToken = refreshTokenList.includes(rToken);
        if(isValidRefreshToken) deleteTokens(rToken);
        res.status(403).send("Logged Out");
    }
})

function generateAccessToken(user) {
    return jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '30m' });
}

function generateRefreshToken(user) {
    return jwt.sign(user, process.env.REFRESH_TOKEN_SECRET, { expiresIn: '7d' });
}

module.exports.tokenRouter = router;
module.exports.createTokens = createTokens;
module.exports.deleteTokens = deleteTokens;