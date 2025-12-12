#!/bin/bash

DIRECTORY=/root/service.accounting/log

cd $DIRECTORY
max_save=7
counter=0
echo "Process Deleting..."
for x in $(ls | grep ".txt" | sort -r)
do
        counter=$(($counter+1))
        if [ $counter -gt $max_save ]
        then
                echo $x" Deleted!"
                rm -Rf $x
        fi
done
echo "Deleting Done!"