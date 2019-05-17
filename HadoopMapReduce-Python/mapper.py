#!/usr/bin/env python
"""mapper.py"""

import sys
import csv

reader=csv.reader(sys.stdin,delimiter=',')
# input comes from STDIN (standard input)
for line in reader:
	#if no of columns values are not 29 then ignore that row
    if len(line)!=29:
        continue
    for word in line[24:29]:
        # write the results to STDOUT (standard output);
        # what we output here will be the input for the
        # Reduce step, i.e. the input for reducer.py
        #
        # tab-delimited; the trivial word count is 1
	# Ignoring headers and empty values.
	if word=='' or word=='VEHICLE TYPE CODE 1' or word=='VEHICLE TYPE CODE 2' or word=='VEHICLE TYPE CODE 3' or word=='VEHICLE TYPE CODE 4' or word=='VEHICLE TYPE CODE 5':
		continue
	print '%s\t%s' % (word,1)
