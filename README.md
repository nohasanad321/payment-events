# Payment Events Processing Service

A Java 25 / Spring Boot 4 demonstration of asynchronous payment event processing using Apache Kafka and Docker.

## Architecture

```text
REST Client
    |
    | POST /v1/payments/create
    v
Spring Boot REST Controller
    |
    v
Kafka Event Producer
    |
    v
Kafka Broker
Topic: payment.created
    |
    v
Kafka Event Consumer
    |
    v
Payment Processing Log
```

## Technology Stack

- Java 25
- Spring Boot 4
- Apache Kafka 4.2.2
- Docker and Docker Compose
- Maven
- Kubernetes (deployment work in progress)

## Features

- REST API for submitting payment requests
- Unique payment identifier generation
- Asynchronous payment event publishing
- Kafka consumer group processing
- JSON event serialization
- Containerized application and Kafka broker
- Local end-to-end validation

## Run Locally

Prerequisites: Docker Desktop and Java 25 with Maven Wrapper support.

Build the application:

```powershell
.\mvnw.cmd clean package
```

Start the containers:

```powershell
docker compose up --build -d
```

Check the services:

```powershell
docker compose ps
```

## Example Payment Request

Endpoint:

`POST http://localhost:8080/v1/payments/create`

Request body:

```json
{
  "amount": 125.50,
  "currency": "KWD"
}
```

Example response (HTTP 202 Accepted):

```json
{
  "paymentId": "dc34dc56-8358-47e0-baa1-61689c18012d",
  "Amount": "125.5 KWD",
  "status": "success"
}
```

The response indicates that the request was accepted by the API. It does not represent payment settlement.

## Verification

Inspect the consumer logs:

```powershell
docker compose logs payment-events
```

An end-to-end test confirmed that the Kafka consumer received the event containing the same payment identifier returned by the REST API.

## Testing

```powershell
.\mvnw.cmd test
```

The current test suite verifies Spring application context startup. Automated Kafka integration testing is a future enhancement.

## Project Scope

This is a technical portfolio demonstration, not a production payment gateway. It does not process real funds or implement settlement, transaction persistence, or financial reconciliation.

## Kubernetes

Kubernetes deployment manifests and validation are in progress.