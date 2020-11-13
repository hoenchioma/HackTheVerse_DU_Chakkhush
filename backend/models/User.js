const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
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
    },
    address: {
        type: String,
        required: true,
        max: 1024,
        min: 10
    },
    date: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('User', userSchema);