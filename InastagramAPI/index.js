const express = require("express");
const mongoose = require("mongoose");
const morgan = require('morgan');
const dotenv = require('dotenv').config();
// const uploadRouter = require('./routes/upload');
const cors = require('cors');
 const auth = require('./auth');
const userRouter = require('./routes/users');


const app = express();
app.use(morgan('tiny'));
app.use(express.json());
app.options('*', cors());
app.use(express.urlencoded({extended: true}));
app.use(express.static(__dirname + "/public"));

mongoose.connect(process.env.URL, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false, useCreateIndex: true})
.then((db) => {
    console.log("Successfully connected to MongoDB server");    
}, (err) => console.log(err));

app.use('/users', userRouter);
// app.use('/fileUpload', uploadRouter);
// app.use(auth.verifyUser);


app.use((err, req, res, next) => {
    console.error(err.stack);
    res.statusCode = 500;
    res.json({ status: err.message});
});

app.listen(process.env.PORT, ()=>{
    console.log(`App is running at localhost:${process.env.PORT}`);
});