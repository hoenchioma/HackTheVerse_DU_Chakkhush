const router = require('express').Router();

const Patient = require('../models/Patient');

/** 
 * Confirm via confirmation link
 * Query Parameters: ward
 * */
router.get('/patient', async (req, res) => {
    try{
        let ward = req.query.ward;
        let patients = await Patient.find({ward: ward});
        res.send(patients);
    } catch(err) {
        console.log(err)
        return res.status(400).send(err);
    }
})

/** 
 * Confirm via confirmation link
 * Body Parameters: ward
 * */
router.post('/patient', async(req, res) => {
    // Creating new Patient
    const patient = Patient({
        name: req.body.name,
        disease: req.body.disease,
        ward: req.body.ward,
        sensors: req.body.sensors
    })
    try{
        const patient = await patient.save();
        res.send(patient);
    } catch(err) {
        console.log(err)
        return res.status(400).send(err);
    }
})