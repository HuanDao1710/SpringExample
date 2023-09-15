#!/bin/sh

echo "Start deploy"

export KAFKA_HOST=${KAFKA_HOST}

export DB_URL=${DB_URL}
export DB_USERNAME=${DB_USERNAME}
export DB_PASSWORD=${DB_PASSWORD}

export ADMIN_USER=${ADMIN_USER}
export ADMIN_PASSWORD=${ADMIN_PASSWORD}

export SECRET_TOKEN=${SECRET_TOKEN}

docker network create --driver bridge viewboomer || true
cd "${WORK_DIR}" || exit
git pull
echo "Pull done"
docker-compose build
docker-compose up -d
echo "Deploy success"