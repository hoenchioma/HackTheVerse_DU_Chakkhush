// Validation
const Joi = require('joi');

const registrationValidation = (data) => {
    const schema = Joi.object ({
        name: Joi.string().min(6).required(),
        email: Joi.string().required().email(),
        password: Joi.string().min(6).required(),
        phone: Joi.string().min(11).max(14).pattern(/^(\+88)?(01[3-9]{1}\d{8}$)/).required(),
    });

    // Validate User
    return schema.validate(data);
}

const loginValidation = (data) => {
    const schema = Joi.object ({
        email: Joi.string().min(6).required().email(),
        password: Joi.string().min(6).required()
    });

    // Validate User
    return schema.validate(data);
}

module.exports.registrationValidation = registrationValidation;
module.exports.loginValidation = loginValidation;
