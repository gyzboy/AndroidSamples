#!/usr/bin/env bash
#这是一个adb shell getevent sendevent实现模拟自动点击的方法,可以用作自动化测试或者还原用户操作等功能
#首先需要调用 adb shell getevent方法获取需要捕获的event事件,比如点击音量键 命令台输出为
# 0001 0073 00000001    type  code  value  这里为16进制,使用sendevent时需要转换为10进制
# 0000 0000 00000000
# 0001 0073 00000000
# 0000 0000 00000000

#adb shell getevent /dev/input/xxxx 只捕获指定event


adb shell sendevent /dev/input/event1 0001 0114 00000001
adb shell sendevent /dev/input/event1 0000 0000 00000000
adb shell sendevent /dev/input/event1 0001 0114 00000000
adb shell sendevent /dev/input/event1 0000 0000 00000000

