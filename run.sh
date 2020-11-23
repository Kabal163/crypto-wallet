#!/usr/bin/env bash

set -e

echo "===Start to build the project...==="
./mvnw clean package
echo "===Project has been successfully built==="

java -jar target/crypto-wallet-1.0-SNAPSHOT.jar

