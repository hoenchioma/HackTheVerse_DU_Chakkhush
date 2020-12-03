import kafka from "kafka-node";

const KAFKA_ADDR = process.env.KAFKA_ADDR || "http://localhost:2181";
const CLIENT_ID = process.env.CLIENT_ID || "my-client-id";

const client = new kafka.Client(KAFKA_ADDR, CLIENT_ID, {
  sessionTimeout: 300,
  spinDelay: 100,
  retries: 2
});

const producer = new kafka.HighLevelProducer(client);
producer.on("ready", function () {
  console.log("Kafka Producer is connected and ready.");
});

// log producer errors to the console.
producer.on("error", function (error) {
  console.error(error);
});

const KafkaService = {
  sendValue: ({ type, value }, topic, callback = () => {}) => {
    const event = {
      timestamp: Date.now(),
      type: type,
      value: value,
    };
    const buffer = new Buffer.from(JSON.stringify(event));
    const record = [
      {
        topic: topic,
        messages: buffer,
        attributes: 1 /* Use GZip compression for the payload */
      }
    ];
    //Send record to Kafka and log result/error
    producer.send(record, callback);
  },

  // sendRecord: ({ type, userId, sessionId, data }, callback = () => { }) => {
  //   if (!userId) {
  //     return callback(new Error("A userId must be provided."));
  //   }

  //   const event = {
  //     id: uuid.v4(),
  //     timestamp: Date.now(),
  //     userId: userId,
  //     sessionId: sessionId,
  //     type: type,
  //     data: data
  //   };

  //   const buffer = new Buffer.from(JSON.stringify(event));

  //   // Create a new payload
  //   const record = [
  //     {
  //       topic: "webevents.dev",
  //       messages: buffer,
  //       attributes: 1 /* Use GZip compression for the payload */
  //     }
  //   ];

  //   //Send record to Kafka and log result/error
  //   producer.send(record, callback);
  // }
};

export default KafkaService;