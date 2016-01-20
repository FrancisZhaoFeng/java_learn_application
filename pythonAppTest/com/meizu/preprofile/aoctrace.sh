# $1 is cmd, which is 'start' or 'stop'
# $2 is keyword of cmdline: for settings
# $3 is key or null

shot=1
keycmd=$2
fullcmd=""
procID=""
key_suffix=""
dmtracedump="/data/dmtracedump"

# usage of this script
usage() {
cat <<EOF
usage : ./aoctrace.sh [command] [cmdline_proc] [key_method]
	[command]            commands of aoctrace.sh,  stop/start/-h
	[cmdline_proc]       keyword of the proc cmdline, for 'settings'
	[key_method]         this is an optional, it must used with 'stop' and it must be 'key'

example : ./aoctrace.sh start settings
          ./aoctrace.sh stop settings key
EOF
}

# wait $procID.trace wirte down
#
# $1 is the filename of /sdcard/$fullcmd/$procID.trace
wait_write_down () {
    filename=$1
    down=false
    while [[ "$down" = false ]]; do
#echo "check written down"
        #check the size
        size=`ls -l $filename |/data/busybox awk '{print $4}'`
        #echo $size
        #sleep for waiting of written down.
        sleep 1
        #check size stay the same.
        newsize=`ls -l $filename |/data/busybox awk '{print $4}'`
        #echo $newsize

        if [[ "$size" == "$newsize" && -n "$newsize" ]]; then
            down=true
#echo "writting is down."
            break
        fi
    done
}

if [ "$#" == "1" -a "$1" == "-h" -o "$#" == "0" ];then
	usage
	exit
fi

# If the expect proc is not started, wait it...
while [ $shot == "1" ]
do
    acturename=`ps|grep $keycmd`
#    echo ">>>>>$acturename<<<<"
    if [ "$acturename" == "" ];then
        shot=1
    else
        procID=`echo $acturename|/data/busybox awk '{print $2}'`
        fullcmd=`echo $acturename|/data/busybox awk '{print $9}'`
        echo ">>>> $procID <<<<<"
        echo ">>>> $fullcmd <<<<<"
        shot=2
    fi
done

# The path which the html files store in
mkdir -p /sdcard/$fullcmd

if [ $# == 3 ];then
	if [ $3 == "key" ];then
		key_suffix="_keymethods"
	else
		usage
		exit
	fi
fi

if [ $1 == "start" ];then
	setprop lemur.vm.aoctrace true
	kill -USR2 $procID
	setprop lemur.vm.aoctrace false
fi

# if cmd is stop, we should send 'kill -USR2 $procID' first
# then, we should merge two files and at last using dmtracedump
if [ $1 == "stop" ];then
	if [ ! -f "/sdcard/$procID.trace" ];then
		echo "/sdcard/$procID.trace is not exist, may be you have to send 'start' command"
		exit
	fi

	if [ ! -f "/sdcard/$procID.trace.record" ];then
		echo "/sdcard/$procID.trace.record is not exist, may be you have to send 'start' command"
		exit
	fi

	setprop lemur.vm.aoctrace true
	kill -USR2 $procID
	wait_write_down  /sdcard/$procID.trace
	htmlName="${procID}_`date +%H_%M_%S_%Y%m%d`${key_suffix}.html"
	cat /sdcard/$procID.trace.record >> /sdcard/$procID.trace
	$dmtracedump -h /sdcard/$procID.trace > /sdcard/$fullcmd/$htmlName
	rm -rf /sdcard/$procID.trace.record /sdcard/$procID.trace
	setprop lemur.vm.aoctrace false
fi
