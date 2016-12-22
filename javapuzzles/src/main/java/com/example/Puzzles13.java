package com.example;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Puzzles13 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte b[] = new byte[256];
        for (int i = 0; i < 256; i++) {
            b[i] = (byte) i;
        }
        System.out.println("default charset is " + Charset.defaultCharset());//打印默认的字符集
        String str = new String(b,"ISO-8859-1");
        for (int i = 0, n = str.length(); i < n; i++) {
            System.out.println((int) str.charAt(i) + "");
        }
    }
}
//ps:
//每当要将一个byte序列转换成一个String时,你都在使用一个字符集,不管是否显式的指定了它