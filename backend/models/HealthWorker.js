const mongoose = require('mongoose');

const HealthWorkerSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        min: 6,
        max: 255
    },
    email: {
        type: String,
        required: true,
        min: 6,
        max: 255
    },
    password: {
        type: String,
        required: true,
        max: 1024,
        min: 6
    },
    confirmed: {
        type: Boolean,
        require: true
    },
    phone: {
        type: String,
        required: true,
        max: 14,
        min: 10
    }
});

module.exports = mongoose.model('HealthWorker', HealthWorkerSchema);