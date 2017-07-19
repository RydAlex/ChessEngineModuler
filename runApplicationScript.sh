#!/usr/bin/env bash

nohup sbt -Djline.terminal=jline.UnsupportedTerminal run </dev/null >output.txt &