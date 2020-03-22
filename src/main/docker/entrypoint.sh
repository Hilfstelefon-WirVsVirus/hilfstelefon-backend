#!/bin/bash
secretsPG=$PG_SECRETS
export DB_URL=$(echo $secretsPG | jq -r .DB_URL)
export DB_PASSWORD=$(echo $secretsPG | jq -r .DB_PASSWORD)

secretsTWILIO=$TWILIO_SECRETS
export TWILIO_PASSWORD=$(echo $secretsTWILIO | jq -r .TWILIO_PASSWORD)

/deployments/run-java.sh