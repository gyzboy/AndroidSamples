#!/bin/bash
#source 和 。 是等效的,都是读入脚本并执行其内容,必须写路径
source ./test.sh
source ./subdir/test2.sh
#. ./test.sh
#realpath=$(readlink -f "$0")
basedir=$(dirname $0)
export PATH=$PATH:$basedir/subdir
echo $test2_global_val

#``里面的内容代表执行程序
test(){
for file in `ls`;do
    echo $file
done
}
#""中可以有转移字符,变量
#‘’中变量不解析,任何字符都会原样输出
#变量名和等号之间不能有空格
your_name='gyz'
str="hello, i know you are \"$your_name\"!\n"
echo $str $str
echo ${#str}
echo ${str:1:4}
for ((i=0;i<2;i++));do
    echo for like c
done
path=1
#方括号在这里是一个可执行程序，方括号后面必须加空格
while [ $path -eq 1 ];do
    echo while test
    echo $path
    path=10
done
until [ $path -eq 1 ];do
    echo $path
    echo until test success
    path=1
done
#case的语法和C family语言差别很大，它需要一个esac（就是case反过来）作为结束标记，
#每个case分支用右圆括号，用两个分号表示break
opt="213"
case "$opt" in
    "213" )
        echo 213
        exit
    ;;
    "231" )
        echo 231
        exit
    ;;
    "exit" )
        exit
    ;;
    * ) echo "error"
 esac

funcImport
echo "global_var = " $global_var
