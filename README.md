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


## Kubernetes Deployment

The application has been deployed and tested on a local
Docker Desktop Kubernetes v1.36.1 cluster.

### Deploy

Build the application image first:

```powershell
.\mvnw.cmd clean package
docker compose build payment-events
```

Create the namespace and deploy Kafka:

```powershell
kubectl create namespace payment-lab
kubectl apply -f k8s/kafka.yaml
kubectl rollout status deployment/kafka -n payment-lab
```

Deploy the payment service:

```powershell
kubectl apply -f k8s/payment-events.yaml
kubectl rollout status deployment/payment-events -n payment-lab
```

The application manifest uses a locally built Docker image
with `imagePullPolicy: Never`.

### Verify

```powershell
kubectl get pods -n payment-lab
```

Expose the payment API locally:

```powershell
kubectl port-forward -n payment-lab svc/payment-events 8081:8080
```

Send a POST request to:

`http://localhost:8081/v1/payments/create`

Verify that the Kafka consumer processed the event:

```powershell
kubectl logs -n payment-lab deployment/payment-events
```

### Verified Result

An end-to-end test processed a sample payment of
250.75 KWD through the Kubernetes deployment.

The REST API response and Kafka consumer log contained
the same payment identifier, confirming successful
event delivery and consumption.

This is a single-node local demonstration, not a
production-ready Kubernetes or Kafka deployment.
