const kafka = require('kafka-node');
const bp = require('body-parser');

require('dotenv').config()
const kafkaTopic = process.env.KAFKA_TOPIC;
const kafkaServer = process.env.KAFKA_SERVER;

// push val as event to the kafkaTopic topic
pushEvent = (val) => {
  const payloads = [{
    topic: kafkaTopic,
    messages: val,
  }];
  let push_status = producer.send(payloads, (err, data) => {
    if (err) {
      console.log('[kafka-producer -> ' + kafkaTopic + ']: broker update failed');
    } else {
      console.log('[kafka-producer -> ' + kafkaTopic + ']: broker update success');
    }
  });
}

try {
  const Producer = kafka.Producer;
  const client = new kafka.KafkaClient(kafkaServer);
  var producer = new Producer(client);
  producer.on('ready', async function () {
    console.log('producer ready');
  });

  producer.on('error', function (err) {
    console.log(err);
    console.log('[kafka-producer -> ' + kafkaTopic + ']: connection errored');
    throw err;
  });
}
catch (e) {
  console.log(e);
}

module.exports = {
  pushEvent
}