const kafka = require('kafka-node');
const bp = require('body-parser');

const checkUrgent = require('./classifier');

const kafkaServer = process.env.KAFKA_SERVER;
const kafkaTopic = process.env.KAFKA_TOPIC;

const MAX_QUEUE_SIZE = 20;

// simple queue data structure
class Queue {
  constructor() {
    this.items = []
  }
  enqueue(element) {
    this.items.push(element);
  }
  dequeue() {
    if (this.isEmpty()) throw "Underflow";
    return this.items.shift();
  }
  front() {
    if (this.isEmpty()) throw "No elements in Queue";
    return this.items[0];
  }
  size() {
    return this.items.length;
  }
  isEmpty() {
    return this.items.length == 0;
  }
  toArray() {
    return this.items;
  }
}

const sensorData = new Map();

function addData({ patientId, type, value }) {
  const queue = sensorData.get([patientId, type]);
  if (!queue) { // queue not present create one
    queue = new Queue();
    sensorData.set([patientId, type], queue);
  }
  queue.enqueue(value);
  // if size of queue exceeds max
  if (queue.size() > MAX_QUEUE_SIZE) {
    queue.dequeue();
  }
}

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
        checkUrgent(data);
        addData(data);
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
  },
  sensorData
}