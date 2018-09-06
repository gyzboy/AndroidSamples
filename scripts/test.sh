#!/bin/bash
global_var="this is a global_var"
funcImport(){
    youname="guz"
    greeting="hello $youname"
    greeting_1="hello ${youname}"

    echo $greeting $greeting_1
}
echo "gloabl" $global_var
funcImport
