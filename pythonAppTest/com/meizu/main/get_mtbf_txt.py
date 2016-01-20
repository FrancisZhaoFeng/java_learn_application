import os
import re 
import time
from __builtin__ import Exception
AAPT = "aapt dump badging "

PACKAGE_REG = "package:\s*name='(?P<package>\S+)'"
ACTIVITY_REG = "('[^']*')"
package_ptn = re.compile(PACKAGE_REG)
activity_ptn = re.compile(ACTIVITY_REG)

BASE_PATH = "/home/francis"
BASE_READ = "flyme4_M86_iof"
LOG_PATH = BASE_PATH + "share/log/"
INIT_PATH = BASE_PATH + "apptest/com/meizu/tools/"
APK_PATH = BASE_PATH + BASE_READ
INFO_PATH = BASE_PATH + "share/" + BASE_READ

def get_current_devices_sn():
    text = os.popen("adb devices | grep -iv list |awk '{print $1}'").read()
    sns = text.split("\n")
    try:
        sns.remove("")
    except ValueError, ve:
        pass
    return sns

def into_current_devices_gettxt():
    sns = get_current_devices_sn()
    for sn in sns:
        os.popen("adb -s " + sn +" shell" )
        text = os.popen("ls /sdcard/autotest/image | grep txt")
        os.popen("exit")
        txts = text.split("\n")
        try:
            txts.remove("")
        except ValueError, ve:
            pass
        for txt in txts:
           os.popen("adb -s "+sn+" pull /sdcard/autotest/image/" +txt +" ~/") 

if __name__ == '__main__':
    into_current_devices_gettxt()

