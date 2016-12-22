package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Puzzles19 {
    public static void main(String[] args) {

    }

    static void copy(String src, String dest) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0, n);
            }
        } finally {

            //这种写法如果close也抛出IO异常就会导致流关闭失败
//            if (in != null) {
//                in.close();
//            }
//            if (out != null) {
//                out.close();
//            }


            if (in != null) {
                try {
                    in.close();
                }catch (IOException ex){
                    //
                }
            }
            if (out != null) {
                try {
                    out.close();
                }catch (IOException ex){
                    //
                }
            }
        }

    }

}
