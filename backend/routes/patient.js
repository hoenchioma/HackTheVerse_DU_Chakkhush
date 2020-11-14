const router = require('express').Router();

const Patient = require('../models/Patient');

/** 
 * Confirm via confirmation link
 * Query Parameters: ward
 * */
router.get('/', async (req, res) => {
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
router.post('/', async(req, res) => {
    // Creating new Patient
    const patient = Patient({
        name: req.body.name,
        disease: req.body.disease,
        ward: req.body.ward,
        sensors: req.body.sensors
    })
    try{
        const savedPatient = await patient.save();
        res.send(savedPatient);
    } catch(err) {
        console.log(err)
        return res.status(400).send(err);
    }
})

module.exports = router;