#!/bin/bash

echo "Subindo aplicação em DEV..."
cp .env.dev .env
docker-compose up --build
