const express = require('express')
const cron = require('node-cron')
const config = require('./config');
const { generateRandomData } = require('./data-gen');
const { pushEvent } = require('./kafka-producer');

const HOST = config.host
const PORT = config.port

const app = express()

app.listen(PORT, HOST, () => {
  console.log(`server running on port ${PORT}`)
})

cron.schedule("*/5 * * * * *", function () {
  pushEvent([generateRandomData()]);
  // console.log('pushed event');
});
