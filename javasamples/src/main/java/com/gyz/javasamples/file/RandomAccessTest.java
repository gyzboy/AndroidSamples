package com.gyz.javasamples.file;


import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;;
import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class RandomAccessTest {

    private static final String FileName = "file.txt";

    public static void main(String[] args) {
        // 若文件“file.txt”存在，则删除该文件。
        File file = new File(FileName);
        if (file.exists())
            file.delete();

        testCreateWrite();
        testAppendWrite();
        testRead();
    }

    /**
     * 若“file.txt”不存在的话，则新建文件，并向文件中写入内容
     */
    private static void testCreateWrite() {
        try {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建文件“file.txt”对应的RandomAccessFile对象
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            // 向“文件中”写入26个字母+回车
            raf.writeChars("abcdefghijklmnopqrstuvwxyz\n");
            // 向“文件中”写入"9876543210"+回车
            raf.writeChars("9876543210\n");

            raf.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向文件末尾追加内容
     */
    private static void testAppendWrite() {
        try {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建文件“file.txt”对应的RandomAccessFile对象
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            // 获取文件长度
            long fileLen = raf.length();
            // 将位置定位到“文件末尾”
            raf.seek(fileLen);

            // 以下向raf文件中写数据
            raf.writeBoolean(true); // 占1个字节
            raf.writeByte(0x41);    // 占1个字节
            raf.writeChar('a');     // 占2个字节
            raf.writeShort(0x3c3c); // 占2个字节
            raf.writeInt(0x75);     // 占4个字节
            raf.writeLong(0x1234567890123456L); // 占8个字节
            raf.writeFloat(4.7f);  // 占4个字节
            raf.writeDouble(8.256);// 占8个字节
            raf.writeUTF("UTF严"); // UTF-8格式写入
            raf.writeChar('\n');   // 占2个字符。“换行符”

            raf.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过RandomAccessFile读取文件
     */
    private static void testRead() {
        try {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建文件“file.txt”对应的RandomAccessFile对象，以只读方式打开
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            // 读取一个字符
            char c1 = raf.readChar();
            System.out.println("c1="+c1);
            // 读取一个字符
            char c2 = raf.readChar();
            System.out.println("c2="+c2);

            // 跳过54个字节。
            raf.seek(54);

            // 测试read(byte[] buffer, int byteOffset, int byteCount)
            byte[] buf = new byte[20];
            raf.read(buf, 0, buf.length);
            System.out.println("buf="+(new String(buf)));

            raf.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
