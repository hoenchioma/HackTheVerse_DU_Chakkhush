const nodemailer = require('nodemailer');

let transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.EMAIL,
        pass: process.env.PASS
    }
})

let mailOptions = {
    from: 'tutorialc925@gmail.com',
    to: 'saminyaser.24csedu.016@gmail.com',
    subject: 'Testing and tEsting'
    // text: 'Hello Hello'
}

module.exports.sendMail = (to, subject, html=null) =>{
    
    to? mailOptions.to = to : null;
    subject? mailOptions.subject = subject : null;
    html? mailOptions.html = html : null;
    console.log(mailOptions);
    try{
        transporter.sendMail(mailOptions, (err, result) => {
            if(err){
                console.log('Error occured: ', err);
            } else {
                console.log("Email sent!");
            }
        })
    }catch (err) {
        console.log(err);
    }
} 