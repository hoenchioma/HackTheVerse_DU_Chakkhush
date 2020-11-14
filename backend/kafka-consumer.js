const kafka = require('kafka-node');
const bp = require('body-parser');

const kafkaServer = process.env.KAFKA_SERVER;
const kafkaTopic = process.env.KAFKA_TOPIC;

module.exports = {
  setupConsumer: () => {
    try {
      var Consumer = kafka.Consumer;
      var Offset = kafka.Offset;
      var Client = kafka.KafkaClient;
      var topic = kafkaTopic;

      var client = new Client({ kafkaHost: kafkaServer });
      var topics = [{ topic: topic, partitions: 1 }, { topic: topic, partitions: 0 }];
      var options = { autoCommit: false, fetchMaxWaitMs: 1000, fetchMaxBytes: 1024 * 1024 };

      var consumer = new Consumer(client, topics, options);
      var offset = new Offset(client);

      consumer.on('message', function (message) {
        const data = JSON.parse(message.value);
        // TODO: do stuff with consumed data
        console.log(message);
      });

      consumer.on('error', function (err) {
        console.log('error', err);
      });

      /*
      * If consumer get `offsetOutOfRange` event, fetch data from the smallest(oldest) offset
      */
      consumer.on('offsetOutOfRange', function (topic) {
        topic.maxNum = 2;
        offset.fetch([topic], function (err, offsets) {
          if (err) {
            return console.error(err);
          }
          var min = Math.min.apply(null, offsets[topic.topic][topic.partition]);
          consumer.setOffset(topic.topic, topic.partition, min);
        });
      });
    }
    catch (e) {
      console.log(e);
    }
  }
}