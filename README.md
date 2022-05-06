# Report API

Report API is a rest api to generate reports in the project scope . It was developed during IT Bootcamp - Wave 5 as a part of the last group project.

## Endpoints

reports/v1/vendor?sellerId={id}
reports/v1/sales

- Base API Port: 8088


| Method   | URI       | Description    |
| :---------- | :--------- | :----------------------- |
| `GET` | `reports/v1/vendor?sellerId={id}` | Get Seller report passing an ID|
| `GET` | `reports/v1/sales` | Get all sales ordered by most sold in quantity |

## Requirements

- Java 11 and later

## Installation and Usage

Use the given Maven Wrapper and Docker to build a new service container

```bash
## 1. Clone project to local 
git clone https://github.com/andrevezi/pi-ml-reports.git

## 2. Use Maven Wrapper to generate a new build  
./mvnw clean package

## 2.1. Optionally, build without tests 
./mvnw clean package -DskipTests


```

For full operation, it is necessary to have the [Cart](https://github.com/andrevezi/pi-ml-cart-individual)[Gandalf](https://github.com/andrevezi/pi-ml-gandalf-individual), [Products](https://github.com/andrevezi/pi-ml-products-individual) and [Warehouse](https://github.com/andrevezi/pi-mi-warehouse-individual) services running with their default ports, hostnames in the same network.

## Authors

- [André Veziane](https://github.com/andrevezi)

