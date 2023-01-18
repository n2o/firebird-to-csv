#!/bin/bash

docker run --rm -v $(pwd)/db/data:/firebird/data -e FIREBIRD_DATABASE=test -e FIREBIRD_USER=test -e FIREBIRD_PASSWORD=test -e ISC_PASSWORD=masterkey -p 3050:3050 --name firebird jacobalberty/firebird:v2.5.9-sc
