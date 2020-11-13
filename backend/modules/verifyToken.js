const jwt = require('jsonwebtoken');

const auth  = (req, res, next) => {
    const token = req.body.token;
    // req.header('auth');
    if(!token) return res.status(401).send('Access Denied');

    try{
        req.healthWorker = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);
        next();
    } catch (err) {
        res.status(401).send('Token Expired');
    }
}

const adminAuth = (req, res, next) => {
    const token = req.body.token;
    // req.header('auth');
    if(!token) return res.status(401).send('Access Denied');

    try{
        req.user = jwt.verify(token, process.env.ADMIN_TOKEN_SECRET);
        next();
    } catch (err) {
        res.status(401).send('Token Expired');
    }
}

module.exports.auth = auth;
module.exports.adminAuth = adminAuth;