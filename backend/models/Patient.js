const mongoose = require('mongoose');

const PatientSchame = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        min: 6,
        max: 255
    },
    disease: {
        type: String,
        required: true,
        min: 6,
        max: 255
    },
    ward: {
        type: String,
    },
    sensors: {
        type: String,
        required: true,
        max: 1024,
        min: 6
    }
});

module.exports = mongoose.model('Patient', PatientSchame);