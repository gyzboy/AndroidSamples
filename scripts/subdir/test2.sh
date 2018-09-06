#!/bin/bash

test2_global_val="this is test2 global val"
#解决类似于C语言重复引用问题
if [ "$log" ];then
    return
fi
export log="test2.sh"
