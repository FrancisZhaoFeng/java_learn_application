#If windows, MV_CMD should be "MV_CMD = move"
#			and shell=True should be "shell=False"

# "BootClassPath.java" will be deleted.

## only use these command:
# adb; dmtracedump;
# mkdir; cp; mv; rm;


###Pseudo code#####
##Send start/stop signal to phone:
# input: pid

####End the Trace.##
##Pull the two tracefile out:
# input: pid
# output: two tracefile

##Handle these two tracefile:
# union it.
# dmtracedump it to html form

##Sort it:
#   output: hot.txt

##Rename it.
# use default or a fixed form.

##Rm useless files

import subprocess
import os
import sys
# mv cmd should change in windows.
ISLINUX = False
SHELL = True
NEED_MERGE = False
KEY_METHOD_UP = False
if ISLINUX:
	MV_CMD = "mv "
	DIR_CONNECT = "/"
else:
	MV_CMD = "MOVE "
	DIR_CONNECT = "\\"

## SORT ALGORITHM ##
def sortTraceFile(htmlFileName):
	"""
	1). Sort it:  
	Return sorted filename.

	"""
	#find the begin line:
	#use list to store tuple(line, call_numbers): list = [ ... (line, call_numbers) ...]
	#	for every line:
	#	tuple = (line, call_numbers)
	#	add tuple to list.
	#find the end.
	#sort list by every element(tuple)'s call_numbers.'
	#print sorted list's line to result file.
	inFile = open(htmlFileName, 'r')
	findBegin = False
	toSortList = []
	beginLine = ""
	for line in inFile:
		#find the begin line
		wordList = line.split()
		if findBegin == False:
			if len(wordList) != 0 and wordList[0] == 'index':
				findBegin = True
				beginLine = line
			continue
		#find the end line
		if line == "Exclusive elapsed time":
			break

		#	for every valid line:
		#	tuple = (line, call_numbers)
		#	add tuple to list.

		#find the valid line (begin with [$digits])
		if len(wordList) == 0 or wordList[0][0] != '[':
			continue
		callNumString = wordList[2]
		callNumList = callNumString.split('+')
		callNum = int(callNumList[0]) + int(callNumList[1])
		lineTuple = (line, callNum)
		toSortList.append(lineTuple)

	#sort list by every element(tuple)'s call_numbers.'
	sortedList = sorted(toSortList, key=lambda lineTuple: lineTuple[1], reverse=True)
	#print sorted list's line to result file.
	sortedFileName = htmlFileName + ".sorted"
	with open(sortedFileName, 'w') as outFile:
		outFile.write(beginLine)
		for lineTuple in sortedList:
			outFile.write("%s" % lineTuple[0])
	inFile.close()
	return sortedFileName



app_proc_name=(
"com.meizu.safe",
"com.aliyun.SecurityCenter",
"com.yunos.alicontacts",
"com.meizu.media.gallery",
"com.meizu.mstore",
"com.aliyun.homeshell",
"com.android.settings",
"com.meizu.net.search",
"com.yunos.gamestore",
"com.android.systemui",
"com.meizu.media.camera",
"com.meizu.media.video",
"com.android.email",
"com.android.mms",
"com.android.bluetooth",
"com.android.calendar",
"com.android.incallui",
"com.aliyun.mobile.ime",
"com.android.deskclock",
"com.aliyunos.filemanager",
"com.meizu.flyme.toolbox",
"com.meizu.media.reader",
"com.meizu.account",
"com.meizu.voiceassistant",
"com.meizu.media.music"
)

app_name=(
"MzSecurity",
"SecurityCenter",
"Contacts",
"Gallery",
"AppCenter",
"AliHomeShell",
"Settings",
"Search",
"GameStore",
"SystemUI",
"Camera",
"Video",
"Email",
"Mms",
"Bluetooth",
"Calendar",
"InCallUI",
"AliIME",
"DeskClock",
"AliFileBrowser",
"ToolBox",
"Reader",
"MzAccount",
"VoiceAssistant",
"Music"
)

def renameTraceFile(sortedFileName, proc_name):
	"""
	2). rename it. 
	input: sortedFileName, proc_name
	return: correct finalFileName
	"""
	for x in range(0, len(app_proc_name)):
		if proc_name in app_proc_name[x]:
			finalFileName = app_name[x] + "@" + app_name[x] + ".apk.aocprofile.merge"
			renameCmd = MV_CMD + sortedFileName + " " + finalFileName
			subprocess.call(renameCmd, shell=SHELL)
			return finalFileName

	finalFileName = proc_name + ".apk.aocprofile.merge"
	renameCmd = MV_CMD + sortedFileName + " " + finalFileName
	subprocess.call(renameCmd, shell=SHELL)
	return finalFileName

def mergeAocProfile(finalFileNameMerge, finalFileName):
	"""
	4). Do merge oprator. It contains 5 steps: 
	input: finalFileNameMerge, finalFileName
	for: (Settings@Settings.app.aocprofile.merge, Settings@Settings.apk.aocprofile)

	4.1). Mark KEY_METHOD at the end of each line if we choose 'key' mode
	"""
	if KEY_METHOD_UP:
		keyMergeFile = []
		file_to_read = file(finalFileNameMerge, 'r')
		while True:
			line = file_to_read.readline()
			if len(line) == 0:
				break
			else:
				wordList = line.split()
				if len(wordList) == 7 and wordList[0] != "index":
					line = line.replace(wordList[6],  wordList[6] + " KEY_METHOD")
				keyMergeFile.append(line)
		file_to_read.close()
		with open(finalFileNameMerge, 'w') as outFile:
			for line in keyMergeFile:
				outFile.write("%s" % line)
	"""
	4.2). Merge twe aocprofiles into one simperly, with any other operator
	"""
	file_to_write = file(finalFileNameMerge,'a')
	finalFileIsNotExist = False
	if os.path.isfile(finalFileName):
		file_to_read = file(finalFileName, 'r')
		beginLine = file_to_read.readline()
	else:
		finalFileIsNotExist = True
	if finalFileIsNotExist or len(beginLine) == 0:
		if not(finalFileIsNotExist):
			file_to_read.close()
		file_to_write.close()
		renameCmd = MV_CMD + finalFileNameMerge + " " + finalFileName
		subprocess.call(renameCmd, shell=SHELL)
		return

	# fetch beginLine
	if "threshold" in beginLine:
		beginLine = file_to_read.readline()

	while True:
		line = file_to_read.readline()
		if len(line) == 0:
			break
		else:
			file_to_write.write(line)

	file_to_read.close()
	file_to_write.close()

	"""
	4.3). Sort the combined aocprofile with methodName description and sourcefile.
		 So, the same method in origin two files will be close in combined aocprofile.
	"""
	inFile = file(finalFileNameMerge, 'r')
	toSortList = []
	for line in inFile:
		wordList = line.split()
		if len(wordList) != 0 and wordList[0] == 'index' or "(toplevel)" in wordList[4]:
			continue
		methodName = wordList[4] + wordList[5] + wordList[6]
		lineTuple = (line, methodName)
		toSortList.append(lineTuple)
	inFile.close()

	sortedList = sorted(toSortList, key=lambda lineTuple: lineTuple[1], reverse=True)
	with open(finalFileNameMerge, 'w') as outFile:
		for lineTuple in sortedList:
			outFile.write("%s" % lineTuple[0])

	"""
	4.4). Merge the same method. There are three options have to merge:
		 1 total%; 2 calls; 3 usecs.
		 And then resort it with 'calls'
	"""
	inFile = file(finalFileNameMerge, 'r')
	toSortList = []
	lastLine = ''
	for line in inFile:
		wordList = line.split()
		if len(lastLine) == 0:
			lastLine = line
			callNumString = wordList[2]
			callNumList = callNumString.split('+')
			callNum = int(callNumList[0]) + int(callNumList[1])
			lineTuple = (line, callNum)
			toSortList.append(lineTuple)
			continue
		methodName = wordList[4] + wordList[5] + wordList[6]
		lastWordList = lastLine.split()
		lastLineMethodName = lastWordList[4] + lastWordList[5] + lastWordList[6]
		if cmp(methodName, lastLineMethodName) == 0:
			# combin %total
			lastWordList1 = lastWordList[1].split('%')
			wordList1 = wordList[1].split('%')
			merge1 = (float(lastWordList1[0]) + float(wordList1[0])) / 2
			lastWordList[1] = '%.1f'%merge1 + '%'

			# combin calls
			lastWordList2 = lastWordList[2].split('+')
			wordList2 = wordList[2].split('+')
			merge21 = int(lastWordList2[0]) + int(wordList2[0])
			merge22 = int(lastWordList2[1]) + int(wordList2[1])
			merge2 = merge21 + merge22
			lastWordList[2] = '%d'%merge21 + "+" + '%d'%merge22

			# combin usecs
			merge3 = int(lastWordList[3]) + int(wordList[3])
			lastWordList[3] =  '%d'%merge3

			# updata the lastLine
			line = line.replace(wordList[1], lastWordList[1])
			line = line.replace(wordList[2], lastWordList[2])

			# support.v4 may replace to support.v8, so we add " " to avoid this sutiation
			line = line.replace(wordList[3]+" ", lastWordList[3]+" ")
			if KEY_METHOD_UP:
				if len(wordList) == 7:
					line = line.replace(wordList[6],  wordList[6] + " KEY_METHOD")
			lineTuple = (line, merge2)
			toSortList[-1] = lineTuple
			continue
		else:
			lastLine = line
			callNumString = wordList[2]
			callNumList = callNumString.split('+')
			callNum = int(callNumList[0]) + int(callNumList[1])
			lineTuple = (line, callNum)
			toSortList.append(lineTuple)
	inFile.close()

	sortedList = sorted(toSortList, key=lambda lineTuple: lineTuple[1], reverse=True)
	with open(finalFileNameMerge, 'w') as outFile:
		for lineTuple in sortedList:
			outFile.write("%s" % lineTuple[0])

	"""
	4.5). If the method marked with 'KEY_METHOD', raise it up.
	"""
	keyMethodList = []
	hotMethodList = []
	with open(finalFileNameMerge, 'r') as input:
		for line in input:
			if "KEY_METHOD" in line:
				keyMethodList.append(line)
			else:
				hotMethodList.append(line)
	keyMethodLens = len(keyMethodList)
	hotMethodLens = len(hotMethodList)
	if keyMethodLens != 0:
		keyMethodProportion = (keyMethodLens * 100) / (keyMethodLens + hotMethodLens)
		threshold = (keyMethodLens + hotMethodLens / 2) * 100 / (keyMethodLens + hotMethodLens)
		thresholdLine = "threshold/" + str(threshold) + " (threshold = keyMethod(" + str(keyMethodProportion)
		thresholdLine += ") * 100% + hotMethod(" + str(100 - keyMethodProportion)  + ") * 50%)" + "\n"

	with open(finalFileNameMerge, 'w') as outFile:
		if keyMethodLens != 0:
			outFile.write(thresholdLine)
		outFile.write(beginLine)
		for line in keyMethodList:
			outFile.write("%s" % line)
		for line in hotMethodList:
			outFile.write("%s" % line)

	renameCmd = MV_CMD + finalFileNameMerge + " " + finalFileName
	subprocess.call(renameCmd, shell=SHELL)

def replaceToGood(finalFileName):
	"""
	3). replace some false html formats to good.
	return: tmp filename
	"""
	# Unimplement.
	tmpFileName = finalFileName + ".tmp"
	output = open(tmpFileName, 'w')
	with open(finalFileName, 'r') as input:
		for line in input:
			line = line.replace("&lt;", "<")
			line = line.replace("&gt;", ">")
			if "BootClassPath.java" in line:
				line = ""
			output.write(line)
	output.close()
	replaceCmd = MV_CMD + tmpFileName + " " + finalFileName
	subprocess.call(replaceCmd, shell=SHELL)
	return tmpFileName


def handleHtmlFile(htmlFileName, proc_name):
	""" handle the trace file
	input: htmlFile
	"""
	# Sort it
	sortedFileName = sortTraceFile(htmlFileName)
	# Rename it
	finalFileName = renameTraceFile(sortedFileName, proc_name)
	# replace html formats
	replaceToGood(finalFileName)
	# merge aocprofile if needed
	if NEED_MERGE or KEY_METHOD_UP:
		finalFileNameMerge = finalFileName
		finalFileName = finalFileName[:-6]
		mergeAocProfile(finalFileNameMerge, finalFileName)


###b to begin, e to end; x to quit.
def handleHtmlFiles():
	global NEED_MERGE
	global KEY_METHOD_UP

	if len(sys.argv) > 1:
		proc_name = sys.argv[1]
	filelist = []
	proc_name = proc_name.replace('./', '')
	proc_name = proc_name.replace('/', '')
	print proc_name
	htmldir = os.getcwd() + DIR_CONNECT + proc_name
	filelist = os.listdir(htmldir)
	if (len(filelist) > 0):
		for filename in filelist:
			if ".html" in filename:
				NEED_MERGE = True
				if "keymethods" in filename:
					KEY_METHOD_UP = True
				# Handle trace files.
				handleHtmlFile(proc_name + DIR_CONNECT + filename, proc_name)
				NEED_MERGE = False
				KEY_METHOD_UP = False

if __name__ == '__main__':
	handleHtmlFiles()
