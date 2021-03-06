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

#
def cmd_get_pkg_activity(filepath):
    text = os.popen(AAPT + filepath + " | grep 'activity name'").read()
    subText = text.split("\n")
    try:
        subText.remove("")
    except ValueError, ve:
        pass
    if subText.__len__() == 0:
        return ""
    activity_name = activity_ptn.search(subText[0]).group(1).replace("'", "")
    return activity_name

def cmd_get_current_page():
    text = os.popen("adb shell uiautomator dump /dev/tty").read()
    subText = text.split("index")
    for strText in subText:
        if "android.widget.ImageView" in strText:
            bounds = strText.split("bounds=\"")[1].split("\"")[0]
            # print bounds
            temp = bounds.replace("][", ",").lstrip("[").rstrip("]").split(",")
            if int(temp[3]) - int(temp[1]) > 1000:
                return True
    return False

def cmd_drap_current_page():
    for i in range(0, 7):
        os.popen("adb shell input swipe 1000 1000 10 1000 300")
    for i in range(0, 7):
        os.popen("adb shell input swipe 500 1200 500 100 300")

def get_package(filepath):
    package_info = os.popen(AAPT + filepath + " | grep 'package'").read()
    activity_info = os.popen(AAPT + filepath + " | grep 'activity'").read()
    print "activity:%s" % activity_info
    package_name = package_ptn.search(package_info)
    activity_name = activity_ptn.search(activity_info)
    print "package:%s" % package_name.group("package")
    print "activity:%s" % activity_name.group(1)
    # print "======package_info:%s" % package_info
    #======package_info:package: name='com.levin.wiresharkmaster' versionCode='2' versionName='1.2'
    if package_name:
        return package_name.group("package")
    return None
    
def read_file_concent():
    doneapk = []
    try:
        fop = open("/home/francis/share/hello", "r")
        lines = fop.readlines()
        for line in lines:
            doneapk.append(line.split(":")[1].split("\n")[0])
    except IOError, io:
        pass
    return doneapk
        
def scan_files(directory, prefix=None, postfix=None):
    file_list = []
    for root, sub_dirs, files in os.walk(directory):
        for special_file in files:
            if postfix:
                if special_file.endswith(postfix):
                    file_list.append(os.path.join(root, special_file))
            elif prefix:
                if special_file.startswith(prefix):
                    file_list.append(os.path.join(root, special_file))
            else:
                file_list.append(os.path.join(root, special_file))
    print "file_list:%d" % (file_list.__len__())
    file_list.sort(cmp=lambda s1, s2:cmp(s1[0], s2[1]))
#     for file in range(0, 2):
#         print "apkname:%s" % file_list[file]
    return file_list;

def get_non_execution_apk():
    doneapk = read_file_concent()
    allapk = scan_files("/home/francis/shareiof")
    for apk in doneapk:
        try:
            allapk.remove(apk)
        except ValueError, ve:
            print "not in"
    return allapk

def remove_duplicates(lis):
    n = [];
    n.append(lis[0])
    for i in range(len(lis)):
        j =0
        while j<range(len(n)):
            if lis[i]==n[j]:
                break
            else:
                j+=1
                n.append(lis[i])
    return n
            

if __name__ == '__main__':
    remove_duplicates([4,5,5,4]);
# 	os.popen("adb shell cat /proc/version >> temp.txt")
    #cmd_get_pkg_activity("/home/francis/flyme4_M86_iof/10439_co.lvdou.bobstar.apk")
#     read_file_concent()
#     print cmd_get_pkg_activity("/home/francis/shareiof/3725_com.gionee.livewallpaper.apk");
    # cmd_get_current_page()
#      get_package("~/share/temp_apk/7527_com.levin.wiresharkmaster.apk")
#     handle()
#     read_file_concent()

