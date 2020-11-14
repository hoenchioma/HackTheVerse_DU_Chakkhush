var multer = require('multer');


const storage = multer.diskStorage({
    destination: function(req, file, cb) {
        cb(null, './img');
    },
    filename: function (req, file, cb) {
        cb(null , file.originalname);
    }
});

const imageFilter = (req, file, cb) => {
    if (file.mimetype.startsWith('image')) {
        cb(null, true);
    } else {
        cb(new AppError('Not an image! Please upload an image.', 400), false);
    }
};

// req.file.path
var upload = multer({storage: storage, fileFilter: imageFilter});

module.exports.upload = upload;