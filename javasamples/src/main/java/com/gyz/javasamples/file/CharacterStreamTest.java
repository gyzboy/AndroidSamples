package com.gyz.javasamples.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by guoyizhe on 16/9/5.
 * 邮箱:gyzboy@126.com
 */
public class CharacterStreamTest {

    private static final int LEN = 5;
    // 对应英文字母“abcdefghijklmnopqrstuvwxyz”
    private static final char[] ArrayLetters = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    private static final String FileName = "file.txt";
    private static final String CharsetName = "utf-8";
    //private static final String CharsetName = "gb2312";



    public static void main(String[] args) throws IOException {
//        charArrayWriter();
//        charArrayReader();


/*
        Sender t1 = new Sender();

        Receiver t2 = new Receiver();

        PipedWriter out = t1.getWriter();

        PipedReader in = t2.getReader();

        try {
            //管道连接。下面2句话的本质是一样。
            //out.connect(in);
            in.connect(out);

            t1.start();
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

//        inputWrite();
//        inputReader();

//        fileWrite();
//        fileReader();

        bufferedWriter();
        bufferedReader();

    }


    static void charArrayReader() throws IOException {
        try {
            // 创建CharArrayReader字符流，内容是ArrayLetters数组
            CharArrayReader car = new CharArrayReader(ArrayLetters);

            // 从字符数组流中读取5个字符
            for (int i=0; i<LEN; i++) {
                // 若能继续读取下一个字符，则读取下一个字符
                if (car.ready()) {
                    // 读取“字符流的下一个字符”
                    char tmp = (char)car.read();
                    System.out.printf("%d : %c\n", i, tmp);
                }
            }

            // 若“该字符流”不支持标记功能，则直接退出
            if (!car.markSupported()) {
                System.out.println("make not supported!");
                return ;
            }

            // 标记“字符流中下一个被读取的位置”。即--标记“f”，因为因为前面已经读取了5个字符，所以下一个被读取的位置是第6个字符”
            // (01), CharArrayReader类的mark(0)函数中的“参数0”是没有实际意义的。
            // (02), mark()与reset()是配套的，reset()会将“字符流中下一个被读取的位置”重置为“mark()中所保存的位置”
            car.mark(0);

            // 跳过5个字符。跳过5个字符后，字符流中下一个被读取的值应该是“k”。
            car.skip(5);

            // 从字符流中读取5个数据。即读取“klmno”
            char[] buf = new char[LEN];
            car.read(buf, 0, LEN);
            System.out.printf("buf=%s\n", String.valueOf(buf));

            // 重置“字符流”：即，将“字符流中下一个被读取的位置”重置到“mark()所标记的位置”，即f。
            car.reset();
            // 从“重置后的字符流”中读取5个字符到buf中。即读取“fghij”
            car.read(buf, 0, LEN);
            System.out.printf("buf=%s\n", String.valueOf(buf));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void charArrayWriter() {
        try {
            // 创建CharArrayWriter字符流
            CharArrayWriter caw = new CharArrayWriter();

            // 写入“A”个字符
            caw.write('A');
            // 写入字符串“BC”个字符
            caw.write("BC");
            //System.out.printf("caw=%s\n", caw);
            // 将ArrayLetters数组中从“3”开始的后5个字符(defgh)写入到caw中。
            caw.write(ArrayLetters, 3, 5);
            //System.out.printf("caw=%s\n", caw);

            // (01) 写入字符0
            // (02) 然后接着写入“123456789”
            // (03) 再接着写入ArrayLetters中第8-12个字符(ijkl)
            caw.append('0').append("123456789").append(String.valueOf(ArrayLetters), 8, 12);

            System.out.printf("caw=%s\n", caw);

            // 计算长度
            int size = caw.size();
            System.out.printf("size=%s\n", size);

            // 转换成byte[]数组
            char[] buf = caw.toCharArray();
            System.out.printf("buf=%s\n", String.valueOf(buf));

            // 将caw写入到另一个输出流中
            CharArrayWriter caw2 = new CharArrayWriter();
            caw.writeTo(caw2);
            System.out.printf("caw2=%s\n", caw2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inputWrite() {
        try {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建FileOutputStream对应OutputStreamWriter：将字节流转换为字符流，即写入out1的数据会自动由字节转换为字符。
            OutputStreamWriter out1 = new OutputStreamWriter(new FileOutputStream(file), CharsetName);
            // 写入10个汉字
            out1.write("字节流转为字符流示例");
            // 向“文件中”写入"0123456789"+换行符
            out1.write("0123456789\n");

            out1.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void inputReader() {
        try {
            // 方法1：新建FileInputStream对象
            // 新建文件“file.txt”对应File对象
            File file = new File(FileName);
            InputStreamReader in1 = new InputStreamReader(new FileInputStream(file), CharsetName);

            // 测试read()，从中读取一个字符
            char c1 = (char)in1.read();
            System.out.println("c1="+c1);

            // 测试skip(long byteCount)，跳过4个字符
            in1.skip(6);

            // 测试read(char[] cbuf, int off, int len)
            char[] buf = new char[10];
            in1.read(buf, 0, buf.length);
            System.out.println("buf="+(new String(buf)));

            in1.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileWrite() {
        try {
            // 创建文件“file.txt”对应File对象
            File file = new File(FileName);
            // 创建FileOutputStream对应FileWriter：将字节流转换为字符流，即写入out1的数据会自动由字节转换为字符。
            FileWriter out1 = new FileWriter(file);
            // 写入10个汉字
            out1.write("字节流转为字符流示例");
            // 向“文件中”写入"0123456789"+换行符
            out1.write("0123456789\n");

            out1.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileReader() {
        try {
            // 方法1：新建FileInputStream对象
            // 新建文件“file.txt”对应File对象
            File file = new File(FileName);
            FileReader in1 = new FileReader(file);

            // 测试read()，从中读取一个字符
            char c1 = (char)in1.read();
            System.out.println("c1="+c1);

            // 测试skip(long byteCount)，跳过4个字符
            in1.skip(6);

            // 测试read(char[] cbuf, int off, int len)
            char[] buf = new char[10];
            in1.read(buf, 0, buf.length);
            System.out.println("buf="+(new String(buf)));

            in1.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferedReader() {

        // 创建BufferedReader字符流，内容是ArrayLetters数组
        try {
            File file = new File("bufferwriter.txt");
            BufferedReader in =
                    new BufferedReader(
                            new FileReader(file));

            // 从字符流中读取5个字符。“abcde”
            for (int i=0; i<LEN; i++) {
                // 若能继续读取下一个字符，则读取下一个字符
                if (in.ready()) {
                    // 读取“字符流的下一个字符”
                    int tmp = in.read();
                    System.out.printf("%d : %c\n", i, tmp);
                }
            }

            // 若“该字符流”不支持标记功能，则直接退出
            if (!in.markSupported()) {
                System.out.println("make not supported!");
                return ;
            }

            // 标记“当前索引位置”，即标记第6个位置的元素--“f”
            // 1024对应marklimit
            in.mark(1024);

            // 跳过22个字符。
            in.skip(22);

            // 读取5个字符
            char[] buf = new char[LEN];
            in.read(buf, 0, LEN);
            System.out.printf("buf=%s\n", String.valueOf(buf));
            // 读取该行剩余的数据
            System.out.printf("readLine=%s\n", in.readLine());

            // 重置“输入流的索引”为mark()所标记的位置，即重置到“f”处。
            in.reset();
            // 从“重置后的字符流”中读取5个字符到buf中。即读取“fghij”
            in.read(buf, 0, LEN);
            System.out.printf("buf=%s\n", String.valueOf(buf));

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferedWriter() {

        // 创建“文件输出流”对应的BufferedWriter
        // 它对应缓冲区的大小是16，即缓冲区的数据>=16时，会自动将缓冲区的内容写入到输出流。
        try {
            File file = new File("bufferwriter.txt");
            BufferedWriter out =
                    new BufferedWriter(
                            new FileWriter(file));

            // 将ArrayLetters数组的前10个字符写入到输出流中
            out.write(ArrayLetters, 0, 10);
            // 将“换行符\n”写入到输出流中
            out.write('\n');

            out.flush();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/**
 * 接收者线程
 */
class Receiver extends Thread {

    // 管道输入流对象。
    // 它和“管道输出流(PipedWriter)”对象绑定，
    // 从而可以接收“管道输出流”的数据，再让用户读取。
    private PipedReader in = new PipedReader();

    // 获得“管道输入流对象”
    public PipedReader getReader(){
        return in;
    }

    @Override
    public void run(){
        readMessageOnce() ;
        //readMessageContinued() ;
    }

    // 从“管道输入流”中读取1次数据
    public void readMessageOnce(){
        // 虽然buf的大小是2048个字符，但最多只会从“管道输入流”中读取1024个字符。
        // 因为，“管道输入流”的缓冲区大小默认只有1024个字符。
        char[] buf = new char[2048];
        try {
            int len = in.read(buf);
            System.out.println(new String(buf,0,len));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从“管道输入流”读取>1024个字符时，就停止读取
    public void readMessageContinued(){
        int total=0;
        while(true) {
            char[] buf = new char[1024];
            try {
                int len = in.read(buf);
                total += len;
                System.out.println(new String(buf,0,len));
                // 若读取的字符总数>1024，则退出循环。
                if (total > 1024)
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 发送者线程
 */
class Sender extends Thread {

    // 管道输出流对象。
    // 它和“管道输入流(PipedReader)”对象绑定，
    // 从而可以将数据发送给“管道输入流”的数据，然后用户可以从“管道输入流”读取数据。
    private PipedWriter out = new PipedWriter();

    // 获得“管道输出流”对象
    public PipedWriter getWriter() {
        return out;
    }

    @Override
    public void run() {
        writeShortMessage();
        //writeLongMessage();
    }

    // 向“管道输出流”中写入一则较简短的消息："this is a short message"
    private void writeShortMessage() {
        String strInfo = "this is a short message";
        try {
            out.write(strInfo.toCharArray());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 向“管道输出流”中写入一则较长的消息
    private void writeLongMessage() {
        StringBuilder sb = new StringBuilder();
        // 通过for循环写入1020个字符
        for (int i = 0; i < 102; i++)
            sb.append("0123456789");
        // 再写入26个字符。
        sb.append("abcdefghijklmnopqrstuvwxyz");
        // str的总长度是1020+26=1046个字符
        String str = sb.toString();
        try {
            // 将1046个字符写入到“管道输出流”中
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
