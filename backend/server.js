const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv/config');

const app = express();

// Middlewares
app.use(cors());

// Routes
app.get('/', (req, res) => {
    res.send('We are on home');
})

// Connect to DB
mongoose.connect(process.env.DB_CONNECT,{
    useNewUrlParser: true,
    useFindAndModify: false,
    useUnifiedTopology: true,
    retryWrites: true,
    w: "majority"},
() => console.log("Connected to DB"));

//Boot server
app.listen(25565, "0.0.0.0", (req, res) => { console.log("Server Started") });