#!/bin/bash
secrets=$PG_SECRETS
DB_URL = $(echo $secrets | jq -r .DB_URL)
DB_PASSWORD = $(echo $secrets | jq -r .DB_PASSWORD)
/deployments/run_java.sh