  #!/bin/bash
# $> bash icon-gen.sh <version label> <project dir> <script sub-dir>
#
#ImageMagick一个图片处理命令
# process_icon version_num res_sub_dir current_work_dir  target_dir
process_icon(){
    image_width=`identify -format %[fx:w] $2/app/src/main/res/mipmap-$2/ic_launcher.png` && let "image_width-=4"
    image_height=`identify -format %[fx:h] $2/app/src/main/res/mipmap-$2/ic_launcher.png` && let "image_height-=4"
    convert $3$4/marker.png -background '#0000' -fill white -gravity south -size 137x16 caption:$1 -composite -resize $image_widthx$image_height $3$4/intermediate.png
    convert -composite -gravity center $2/app/src/main/res/mipmap-$2/ic_launcher.png $3$4/intermediate.png $3$4/ic_launcher.png
    cp $3$4/ic_launcher.png $3/app/src/internal/res/mipmap-$2/
}

echo "res_sub_dir is "$1
echo "work_dir is "$2
echo "target_dir is  "$3

process_icon $1 xhdpi $2 $3
process_icon $1 xxhdpi $2 $3
process_icon $1 hdpi $2 $3
process_icon $1 mdpi $2 $3
rm $2$3/ic_launcher.png
rm $2$3/intermediate.png