const { v4: uuidv4 } = require('uuid');

const PATIENT_NO = 5;

const patients = [];
const types = ['spo2', 'bp', 'heartRate'];

function fillPatientArray() {
  for (i = 0; i < PATIENT_NO; i++) {
    patients.push(uuidv4());
  }
}

/**
 * Returns a random number between min (inclusive) and max (exclusive)
 */
function getRandomArbitrary(min, max) {
  return Math.random() * (max - min) + min;
}

/**
 * Returns a random integer between min (inclusive) and max (inclusive).
*/
function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateValue(type) {
  switch(type) {
    case 'spo2':
      return getRandomArbitrary(70, 110);
    case 'bp':
      return [
        getRandomArbitrary(60, 200), 
        getRandomArbitrary(60, 100)
      ];
    case 'heartRate':
      return getRandomArbitrary(50, 120);
  }
}

module.exports = {
  generateRandomData: () => {
    const type = types[getRandomInt(0, types.length-1)];
    return {
      patientId: patients[getRandomInt(0, PATIENT_NO-1)],
      type, 
      value: generateValue(type),
      time: Date.now()
    };
  } 
}

fillPatientArray();