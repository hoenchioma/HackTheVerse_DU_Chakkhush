const express = require('express')
const cron = require('node-cron')
const { generateRandomData } = require('./data-gen');
const { pushEvent } = require('./kafka-producer');

require('dotenv').config();

const HOST = process.env.HOST || '0.0.0.0'
const PORT = process.env.PORT || 3000

const app = express()

app.listen(PORT, HOST, () => {
  console.log(`server running on port ${PORT}`)
})

cron.schedule("*/5 * * * * *", () => {
  const randData = generateRandomData();
  console.log(randData);
  pushEvent(JSON.stringify(randData));
  // console.log('pushed event');
});
