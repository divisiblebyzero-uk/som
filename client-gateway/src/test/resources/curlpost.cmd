rem curl -X POST -d receiveagainstpayment.json -H "Content-Type: application/json" http://localhost:8080/api/requestARAP
curl -i -v -X POST --data "@receiveagainstpayment.json" -H "Content-Type: application/json" http://localhost:8080/api/receive-against-payments
