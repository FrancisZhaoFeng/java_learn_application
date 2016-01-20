v1.1
此版本修复两个问题
	1、修改aoctrace.sh中html文件名的格式，文件名中带":"号无法adb pull
	2、修复target版本dmtracedump "Segmentation fault"

v1.2
此版本修复一个问题
	1、将setprop dalvik.vm.aoctrace修改为setprop lemur.vm.aoctrace，否
		则无法开启aoctrace功能。此bug出现的原因是在使用过aoctrace.py后
		lemur.vm.aoctrace会被设置为true，掩盖了此bug。

v1.3
此版本新增一个脚本，修复一个问题
	1、此版本新增 mergehtml.py，此脚本用于合并 aoctrace.sh收集到的 *.html文件
	2、修复awk命令为全路径使用：/data/busybox awk
=====================================================================
aoctrace.sh 需要如下两个工具：

1 dmtracedump
	此工具需要放到/data分区

2 busybox
	此工具需要放到/data分区，aoctrace.sh需要用到busybox的awk功能

aoctrace.sh/dmtracedump/busybox均需要chmod +x

======================================================================
mergehtml.py 使用方法：
python mergehtml.py proc_name

proc_name为指定应用cmdline的关键字。
测试git
