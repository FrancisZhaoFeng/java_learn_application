#!/usr/bin/python
# -*- coding: utf-8 -*-
# from distutils.file_util import write_file
import os
import re
import time
# from com.meizu.tools.tool import cmd_get_current_page, cmd_drap_current_page


__author__ = 'ef-tanshucheng'
__author__ = 'zhaoguofeng'

PACKAGE_REG = "package:\s*name='(?P<package>\S+)'"
ACTIVITY_REG = "('[^']*')"
package_ptn = re.compile(PACKAGE_REG)
activity_ptn = re.compile(ACTIVITY_REG)
# package_info:package: name='com.levin.wiresharkmaster' versionCode='2' versionName='1.2'

# 请配置下面的3个全局变量
BASE_PATH = "/home/francis_virtual/"
BASE_READ = "flyme_app/flyme5_MA01_iof"
LOG_PATH = BASE_PATH + "share/log_flyme5_MA01_MA01_iof2/"
INIT_PATH = BASE_PATH + "apptest/com/meizu/tools/"
APK_PATH = BASE_PATH + BASE_READ
INFO_PATH = BASE_PATH + "share/flyme5_MA01_MA01_iof2"

AAPT = "aapt dump badging "

# 判断手机型号
PHONE_TYPE = "M"
try:
    PHONE_CMD = os.popen("adb shell getprop | grep 'ro.product.mobile.name'").read().rstrip('\r\n')
    if PHONE_CMD:
        PHONE_TYPE = PHONE_CMD.split(':')[1][2:5]
    else:
        product_name = os.popen("adb shell getprop | grep 'ro.build.product'").read().rstrip('\r\n').split(':')[1].lstrip()
        phone_alia_name = re.match(r'\[(.*?)\]', product_name).groups()[0]
        if phone_alia_name == "m1note":
            PHONE_TYPE = "M71"
        elif phone_alia_name == "m2":
            PHONE_TYPE = "M88"
        elif phone_alia_name == "k52v2":
            PHONE_TYPE = "M71"
        elif phone_alia_name == "mx4pro":
            PHONE_TYPE = "M76"
        elif phone_alia_name == "niux":
            PHONE_TYPE = "M86"
        else:
            PHONE_TYPE = "0M"
except:
    print "查找手机型号失败"



class Monkey:
    def __init__(self):
        self.package = ''
        self.cmd_list = []
        pass

    def get_package(self, filepath):
        package_info = os.popen(AAPT + filepath + " | grep 'package'").read()
        package_name = package_ptn.search(package_info)
        print "package:%s" % package_name
        # print "======package_info:%s" % package_info
        #======package_info:package: name='com.levin.wiresharkmaster' versionCode='2' versionName='1.2'
        if package_name:
            return package_name.group("package")
        return None

    def get_cmd_from_file(self):
        cmd_list = []
        try:
            cmd_file = open(INIT_PATH + "init", "r")
            cmd = cmd_file.readline().rstrip('\n')
            while cmd:
                cmd_list.append(cmd)
                cmd = cmd_file.readline().rstrip('\n')
        except IOError:
            print "打开命令文件失败"
        else:
            cmd_file.close()
            return cmd_list
        
    def write_info_to_file(self, strNote):
        f = file(INFO_PATH, "a+")
        f.writelines(strNote + "\n")
        f.close()

    def run_cmd_for_buffer(self, cmd_list, num):
        file_name = self.create_file_name(num)
        if  cmd_list != None:
            for cmd in cmd_list:
		print "adb shell %s >> %s" % (cmd, file_name)
                #os.popen("adb shell %s >> %s" % (cmd, file_name))

    def create_file_name(self, num):
        tt = time.strftime('%Y%m%d%H%M%S', time.gmtime())
        return LOG_PATH + str(num) + self.fileprefix + "_" + self.package + "_" + PHONE_TYPE.upper() + "_" + tt + ".txt"

    def read_file_concent(self):
        doneapk = []
        try:
            fop = open(INFO_PATH, "r")
            lines = fop.readlines()
            for line in lines:
                doneapk.append(line.split(":")[1].split("\n")[0])
        except IOError, io:
            pass
        return doneapk
    
    def scan_files(self, directory, prefix=None, postfix=None):
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
#         file_list.sort(cmp=lambda s1, s2:cmp(s1[0], s2[1]))
        return file_list;
    
    def get_non_execution_apk(self):
        doneapk = self.read_file_concent()
        allapk = self.scan_files(APK_PATH)
        for apk in doneapk:
            try:
                allapk.remove(apk)
            except ValueError, ve:
                print "not in"
        return allapk
    
    def get_cmd_pkg_activity(self, filepath):
        text = os.popen(AAPT + filepath + " | grep 'name'").read()
        subText = text.split("\n")
    #     print "subText size:%d" % subText.__len__()
        try:
            subText.remove("")
        except ValueError, ve:
            pass
    #     print "subText:%s" % subText
        package_name = package_ptn.search(subText[0]).group("package")
        if subText.__len__() != 2:
            return package_name
        activity_name = activity_ptn.search(subText[1]).group(1).replace("'", "")
    #     print "package:%s" % package_name
    #     print "activity:%s" % activity_name
        return package_name + "/" + activity_name
    
    def cmd_get_current_page(self):
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
    
    def cmd_drap_current_page(self):
        for i in range(0, 7):
            os.popen("adb shell input swipe 1000 1000 10 1000 300")
        for i in range(0, 7):
            os.popen("adb shell input swipe 500 1200 500 100 300")

    def screenshot(self, num):
        # os.popen("adb shell /system/bin/screencap -p | sed 's/\r$//' > %s.png" % (LOG_PATH+str(num)+"_"+self.package))
        os.popen("adb shell /system/bin/screencap -p /mnt/sdcard/screenshot.png")
        os.popen("adb pull /mnt/sdcard/screenshot.png " + (LOG_PATH + str(num) + "_" + self.package))

    def start(self, dirpath):
#         apk_path = self.scan_files(dirpath)
        cmd_list = self.get_cmd_from_file()
        apk_path = self.get_non_execution_apk()
        print "总执行apk个数：%s" % apk_path.__len__()
        num = 0
        for filepath in apk_path:
            print "===================\n正在测试:%s" % filepath
            # self.write_info_to_file("正在测试:%s" % filepath)
            num += 1
            self.package = self.get_package(filepath)
            print "包名：%s" % self.package
            if self.package:
                pkg = os.popen("adb install -r " + filepath).read()
                print "信息:%s" % pkg
                # install success
                if pkg.find("Success") >= 0:
                    print "安装成功：%s" % filepath
                    apkActivity = self.get_cmd_pkg_activity(filepath)
                    os.popen("adb shell am start " + apkActivity)
                    time.sleep(2)
                    os.popen("adb shell input swipe 500 1200 500 100 300")  # 防止锁屏
                    if self.package in os.popen("adb shell dumpsys window w | grep mCurrent").read():
                        print "打开成功：%s" % filepath
                        self.cmd_drap_current_page()
                        if self.cmd_get_current_page():
                            self.cmd_drap_current_page()
                        command = "adb shell monkey -p %s --pct-touch 50 --pct-motion 15 --pct-anyevent 5 --pct-majornav " \
                              "12 --pct-trackball 1 --pct-nav 0 --pct-syskeys 15 --pct-appswitch 2 --throttle 500 -s  0  -v  500" % self.package
                        text = os.popen(command).read()
                        # print text
                        # monkey error
                        if text.find("Monkey aborted due to error") >= 0:
                            if text.find("// CRASH: " + self.package) >= 0:
                                self.write_info_to_file("crash:%s" % filepath)
                                self.fileprefix = "_Crash"
                            elif text.find("ANR in " + self.package) >= 0:
                                self.write_info_to_file("NotRespond:%s" % filepath)
                                self.fileprefix = "_NotRespond"
                                self.screenshot(num)
                            else:
                                self.write_info_to_file("Error:%s" % filepath)
                                self.fileprefix = "_Error"
                            self.run_cmd_for_buffer(cmd_list, num)
                        else:
                            self.write_info_to_file("Success:%s" % filepath)
                            print "Monkey Success"
                    else:
                        print "打开失败：%s" % filepath
                        self.write_info_to_file("OpenFail:%s" % filepath)
                        self.fileprefix = "_OpenFail"
                        self.run_cmd_for_buffer(cmd_list, num)
                    os.popen("adb uninstall %s" % self.package)
                else:
                    print "安装失败：%s" % filepath
                    self.write_info_to_file("InstallFail:%s" % filepath)
                    self.fileprefix = "_InstallFail"
                    self.run_cmd_for_buffer(cmd_list, num)
            os.popen("adb shell logcat -c")

if __name__ == "__main__":
    monkey = Monkey()
    monkey.start(APK_PATH)
    
