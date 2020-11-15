<br/>
<p align="center">
    <img src="assets/logo.png" alt="Logo" width="200" height="auto">
</p>
<h1 align="center">Binidro</h1>

Please see `Project Configuration` part to see how to use/run the project.

# Contents

- Contributors
- Problem Identification and Risk Analysis
- Idea Formulation
- Implementation Plan and Work Progress
- Documentation
    - Use Case
    - Activity Diagram
    - Data Flow Diagram
    - Architecture Diagram
- Project Configuration

<br/>

# Contributors

Team: DU_Chakkhush<br />

Team Leader: 
- Raheeb Hassan<br />

Team Members:<br />
- S.M.Mehrabul Islam<br />
- Shadman Wadith<br />
- Samin Yaser<br />

Slack Channel: _h14_du_chakkhush

<br/>

# Problem Identification and Risk Analysis

## Problem

<br/>

Efficient Monitoring of patients when the amount of patients is high using limited medical staff by proper processing and distribution of medical sensor data. 

<br/>

## Risks

<br/>

- False negative results from data classifiers might lead a patient to a critical condition.
- All hospital staff might not have Android devices.
- Hospital staff might require extra training for using the application.
- We might not be able to obtain a reliable stream of sensor data.
- Load balancing is ultimately dependent on competency of Hospital Administrative Staff.

<br/>

# Idea Formulation

In this pandemic situation the world has faced more surge of patients than ever. We are short on medical supplies as well as man power. In this situation, we can leverage the power of data processing to our advantage.<br/>

If we assume for the sake of this project the hospital emits it’s sensor data by using Apache Kafka. By using algorithmic/ML classifiers on the data points, we can detect when the data points indicate a patient is in critical condition or is in need of urgent attention (for example, when a COVID 19 patients oxygen saturation goes below normal levels or a patient’s pulse reduces below the safe threshold, patients ECG indicates heart attack, etc.). Then the server will send a notification/alert to our Android application. Certain doctors/health workers will be assigned to certain wards/zones (this will be controlled by admin). So they will only receive alerts about patients they are responsible for. This can also be load balanced, to reduce the load on health workers so that only urgent needs are responded to.<br/>

Moreover, doctors can at a glance view the condition of all his patients. Hospital staff can also use statistical data to redistribute their staff based on need. 

<br/>
<br/>

# Implementation Plan and Work Progress

The sensor data will be in the form of an Apache Kafka stream. Our backend will be a NodeJS server which consumes this stream, and emits an API for our mobile app to consume. The mobile app will be a simple Android app to send notifications and view recent sensor data for patients.

Android application is 80% complete. Some of the data visualization aspects are remaining. The server side authentication is complete on the backend side. The data processing of the Kafka stream on the server side needs more work.

<br/>

# Documentation

## Use Case

<img  src="assets/diagrams/UseCase.jpg"/>

<br/>
<br/>

## Activity Diagram

<img  src="assets/diagrams/ActivityDiagram.jpg"/>

<br/>
<br/>

## Data Flow Diagram

<img  src="assets/diagrams/DataFlowDiagram.jpg"/>

<br/>
<br/>

## Architecture Diagram

<img  src="assets/diagrams/ArchitectureDiagram.jpg"/>


<br/>
<br/>
<br/>

# Project Configuration

The Project consists of 2 main parts. The `backend` folder contains the `nodejs` server backend for the application. This can be run using `npm start`. On the other hand the `frontend` folder contains the Android project for the frontend application.
The `data-producer` contains a server which generates dummy data for our backend to consume. It is also a `nodejs` server and can be run using `npm start`. Note Apacke Kafka has to be running and the environment variables `KAFKA_SERVER` and `KAFKA_TOPIC` has to be set. The env variables `KAFKA_SERVER` and `KAFKA_TOPIC` are also needed by the `backend` server for the kafka consumer.

**.env** (for data-producer)
```
HOST=
PORT=
KAFKA_SERVER=
KAFKA_TOPIC=
```

**.env** (for node backend)

```
DB_CONNECT=
# node
# require('crypto').randomBytes(64).toString('hex')
ACCESS_TOKEN_SECRET = 
REFRESH_TOKEN_SECRET = 
EMAIL_TOKEN_SECRET = 
ADMIN_TOKEN_SECRET =
EMAIL =
PASS = 
KAFKA_SERVER=
KAFKA_TOPIC=
```

**Setting Up Kafka**

[Download Kafka](https://archive.apache.org/dist/kafka/1.0.0/kafka_2.11-1.0.0.tgz)

Run Kafka:

1. Run 
```bin/zookeeper-server-start.sh config/zookeper.properties``` or  
```bin/windows/zookeeper-server-start.bat config/zookeper.properties``` (windows)
2. Run ```bin/kafka-server-start.sh config/server.properties``` or ```bin/windows/kafka-server-start.bat config/server.properties``` (windows)

