const router = require('express').Router();

router.get('/patient', async (req, res) => {
    try{

    } catch(err) {
        console.log(err)
        return res.status(400).send(err);
    }
})