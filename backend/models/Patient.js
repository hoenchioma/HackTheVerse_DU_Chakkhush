const mongoose = require('mongoose');

const HealthWorkerSchema = new mongoose.Schema({
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
    sensors: {
        type: String,
        required: true,
        max: 1024,
        min: 6
    }
});

module.exports = mongoose.model('HealthWorker', HealthWorkerSchema);