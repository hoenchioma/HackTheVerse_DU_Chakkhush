const router = require('express').Router();
const path = require('path');
const bcrypt = require('bcryptjs');
const { isRef } = require('joi');
const jwt = require('jsonwebtoken');
const rateLimit = require('express-rate-limit');

const HealthWorker = require('../models/HealthWorker');

const auth = require('../modules/verifyToken').auth;
const {registrationValidation, loginValidation} = require('../modules/validation');
const {createTokens, deleteTokens} = require('./tokens');
const sendMail = require('../modules/mailer').sendMail;

// middleware
const minuteLimiter = rateLimit({
    windowMs: 60 * 1000,
    max: 1,
    skipFailedRequests: true
})

// Fonts
var fonts = {
    Roboto: {
      normal: 'fonts/Roboto-Regular.ttf',
      bold: 'fonts/Roboto-Medium.ttf',
      italics: 'fonts/Roboto-Italic.ttf',
      bolditalics: 'fonts/Roboto-MediumItalic.ttf'
    }
  };

const frontendUrl = 'http://localhost:3000/';

// Route: /HealthWorker
router.get('/', async (req, res) => {
    try{
        res.send("Auth working");
    } catch(err) {
        console.log(err)
        return res.status(400).send(err);
    }
})

/** 
 * Confirm via confirmation link
 * URL Parameters: token
 * */
router.get('/confirmation/:token', async (req, res) => {
    try{
        const token = jwt.verify(req.params.token, process.env.EMAIL_TOKEN_SECRET);
        HealthWorker.findByIdAndUpdate(token.id, {'confirmed': true}, (err, result) => {
            if(err)
                res.status(404).send("Page Not Found");
            else
                res.send("Account Successfully Verified");
        })  
    } catch (err) {
        console.log(err);
        return res.status(404).send("Invalid link")
    }
    
})

/** 
 * Resend confirmation link by logging in
 * Query Parameters:
 * Body: token (Access token)
 * */
router.post('/resendconfirmation', minuteLimiter, auth, async (req, res) => {
    const healthWorker = await HealthWorker.findById(req.healthWorker.id);
    const token = jwt.sign({ id: req.healthWorker.id },
        process.env.EMAIL_TOKEN_SECRET,
        { expiresIn: '7d' }
    );
    sendMail(
        to=healthWorker.email,
        subject='Binidro Confirmation Mail',
        html='Hello,<br> Please Click on the link to verify your email.<br><a href='+ frontendUrl + 'response/' + token + '>Click here to verify</a>'
    );
    res.send('Email successfully sent');    
})

/** 
 * Send mail with one time reset password token
 * Query Parameters:
 * Body: email
 * */
router.post('/resetpassword', minuteLimiter, async (req, res) => {
    if(!req.body.email) return res.status(401).send("Invalid Email");
    const email = req.body.email;

    const healthWorker = await HealthWorker.findOne({email: email});
    if(!healthWorker) return res.status(401).send('Invalid Email');

    const payload = {
        id: healthWorker._id
    };
    const secret = healthWorker.password + '-' + healthWorker.date;

    // Generate token from payload and secret
    const token = jwt.sign(payload, secret);

    // send healthWorkerId and token
    sendMail(
        to=req.body.email,
        subject='Binidro Password Recovery Mail',
        html='Hello,<br> Please Click to reset your password.<br><a href='+ frontendUrl + 'recoverpassword/' + token + '>Click here to reset password</a>'
    );
    res.send("Recovery mail sent");
})

/** 
 * Reset password using onetime token
 * Query Parameters:
 * Body: email
 * */
router.post('/resetpassword/:token', async (req, res) => {
    try{
        const token = req.params.token;
        const newPassword = req.body.newPassword;
        const email = req.body.email;
        if((token === undefined || token === null) || (newPassword === undefined || newPassword === null) || (email === undefined || email === null)){
            res.status(400).send("Bad Request");
        }

        const healthWorker = await HealthWorker.findOne({email: email});
        if(!healthWorker) return res.status(404).send("HealthWorker Not Found");

        const secret = healthWorker.password + '-' + healthWorker.date;
        const verification = jwt.verify(token, secret);
        if(!verification) res.status(404).send("Page Not Found");
        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(newPassword, salt);
        await HealthWorker.findByIdAndUpdate(verification.id, {'password': hashedPassword}, (err, result) => {
            if(err)
                res.status(404).send("Page Not Found");
            else if(result)
                res.send("Password updated successfully");
        })
    } catch (err) {
        console.log(err);
        res.send(err);
    }
})

/** 
 * Reset password using onetime token
 * Query Parameters:
 * Body: name, email, password, phone, 
 * */
router.post('/register', async (req, res) => {
    // Validation
    const {error} = registrationValidation(req.body);
    if(error)  return res.status(401).send(error);

    // HealthWorker already exists
    const emailExists = await HealthWorker.findOne({email: req.body.email});
    if(emailExists) return res.status(409).send("Email already exists");

    // Hash Password
    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(req.body.password, salt);

    // Creating new health worker
    const healthWorker = HealthWorker({
        name: req.body.name,
        email: req.body.email,
        password: hashedPassword,
        phone: req.body.phone,
        confirmed: false,
        type: req.body.type,
        ward: req.body.ward
    })
    try {
        const savedHealthWorker = await healthWorker.save();
        const token = jwt.sign({ id: savedHealthWorker._id },
            process.env.EMAIL_TOKEN_SECRET,
            { expiresIn: '7d' }
        );
        sendMail(
            to = savedHealthWorker.email,
            subject = 'Binidro Confirmation Mail',
            html = 'Hello,<br> Please Click on the link to verify your email.<br><a href='+ frontendUrl + 'response/' + token + '>Click here to verify</a>'
        );
        res.status(200).send("An e-mail has been sent to your e-mail address for verification\nPlease verify your e-mail to complete registration.");
    } catch(err) {
        res.status(400).send(err);
    }
})

/** 
 * Login using email and password
 * Query Parameters:
 * Body: email, password
 * */
router.post('/login', async (req, res) => {
    // Login Validation
    const {error} = loginValidation(req.body);
    if(error) return res.status(401).send(error.details[0].message);
    const healthWorker = await HealthWorker.findOne({email: req.body.email});
    if(!healthWorker) return res.status(401).send('Invalid Email');

    
    // Password is Correct
    const validPass = await bcrypt.compare(req.body.password, healthWorker.password);
    if(!validPass) return res.status(400).send('Invalid Password');

    // Create and Assign a token
    const token = await createTokens(healthWorker);
    res.json({ accessToken: token[0], refreshToken: token[1],
        name: healthWorker.name, 
        email: healthWorker.email,
        phone: healthWorker.phone,
        confirmed: healthWorker.confirmed,
        type: healthWorker.type,
        ward: healthWorker.ward
    });
})

/** 
 * Logout
 * Body: token
 * */
router.post('/logout', async (req, res) => {
    try{
        deleteTokens(req.body.token);
        res.send("Successfully logged out");
    } catch {
        res.send("Error occured while logging out");
    }
})

/** 
 * Update profile
 * Body: name, phone, type, ward, password
 * */
router.patch('/', auth, async (req, res) => {
    var setObject = {};
    
    // Fill default fields
    setObject.name = req.body.name;
    setObject.phone = req.body.phone;
    setObject.type = req.body.type;
    setObject.ward = req.body.ward;
    
    // Get health worker from database
    const healthWorker = await HealthWorker.findById(req.healthWorker.id);

    // Password matching
    const validPass = await bcrypt.compare(req.body.password, healthWorker.password);
    if(!validPass) return res.status(400).send('Invalid Password');

    // Hash new password if required
    if(req.body.newPassword != null && req.body.newPassword != "")
        setObject.password = await bcrypt.hash(req.body.newPassword, 10);
    else
        req.body.newPassword = null;
    
    // Update Profile
    HealthWorker.findByIdAndUpdate(req.healthWorker.id, { $set: setObject }, (err, result) => {
        if(err) return res.status(502).send("Error occured");
        res.send("Profile updated");
    });
})

module.exports = router;