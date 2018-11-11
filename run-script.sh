#!/bin/sh

~/sbt/bin/sbt -mem 3000 -Djline.terminal=jline.UnsupportedTerminal run </dev/null >output.txt &
pid=$!
echo $pid

while :
do
   sleep 3
   eval pid_exist=$(ps -o pid= -p $pid)
   echo $pid_exist
   if [ -z $pid_exist ]: 
   then
      echo "Server 'chessClient' crashed with exit code $?. Respawning..." >&2
      ~/sbt/bin/sbt -mem 3000 -Djline.terminal=jline.UnsupportedTerminal run </dev/null >output.txt &
      pid=$!
      echo $pid
      sleep 10
   else
      date +%D/%T
      echo "Nothing break"
      sleep 60
   fi
done
