#!/bin/bash

for i in {1..10}
do
  status=$(echo "ruok" | nc localhost 2181)
  echo -e "try ${i}: ${status}"
done