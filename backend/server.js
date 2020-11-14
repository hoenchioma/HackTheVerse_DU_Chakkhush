const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');
require('dotenv/config');

const app = express();

// Import Routes
const authRoute = require('./routes/auth');
const patientRoute = require('./routes/patient');

// Middlewares
app.use(cors());
app.options('*', cors());
app.use(bodyParser.json());

// Route Middlewares
app.use('/healthworker', authRoute);
app.use('/patients', patientRoute);

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


const { setupConsumer } = require('./kafka-consumer');
// setup kafka consumer
setupConsumer();

const HOST = process.env.HOST || '0.0.0.0';
const PORT = process.env.PORT || 25565;

//Boot server
app.listen(PORT, HOST, (req, res) => { console.log("Server Started") });