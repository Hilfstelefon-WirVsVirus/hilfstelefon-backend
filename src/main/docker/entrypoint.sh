#!/bin/bash
set -x
secrets=$PG_SECRETS
export DB_URL=$(echo $secrets | jq -r .DB_URL)
export DB_PASSWORD=$(echo $secrets | jq -r .DB_PASSWORD)
/deployments/run-java.sh