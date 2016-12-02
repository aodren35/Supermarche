#!/usr/bin/env bash

curl -X POST localhost:5001/users/$1/tweets -d '{"content":"I am a tweet"}' -H 'Accept: application/json' -H 'Content-type: application/json'
