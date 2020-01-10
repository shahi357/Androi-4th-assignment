const express = require('express');
const mongoose = require('mongoose');
const uploadRouter = express.Router();
const userScema = new mongoose.Schema({
    firstName: {
        type: String,
        required: true
    },
    lastName: {
        type: String,
        required: true
    },
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    image: {
        type: String
    }
});

module.exports = mongoose.model('User', userScema);