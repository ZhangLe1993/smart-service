#!/usr/bin/env bash
thrift.exe --gen py -out ../ message.thrift

thrift.exe --gen java -out ../../message-thrift-service-api/src/main/java message.thrift