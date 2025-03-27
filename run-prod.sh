#!/bin/bash

echo "Subindo aplicação em PROD..."
cp .env.dev .env
docker-compose up --build
