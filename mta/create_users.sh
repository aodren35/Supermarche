#!/usr/bin/env bash

for i in {1..10}
do 
  (curl -X POST localhost:5001/users -d '{"name":"peter", "age":30}' -H 'Accept: application/json' -H 'Content-type: application/json' ; echo) &
done
