#!/usr/bin/env bash

curl -X DELETE localhost:5001/users/$1 -H 'Accept: application/json' 
