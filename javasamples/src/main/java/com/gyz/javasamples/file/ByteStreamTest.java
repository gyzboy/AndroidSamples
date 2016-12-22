package com.gyz.javasamples.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by guoyizhe on 16/9/5.
 * 邮箱:gyzboy@126.com
 */
public class ByteStreamTest {
    private static final int LEN = 5;
    // 对应英文字母“abcddefghijklmnopqrsttuvwxyz”
    private static final byte[] ArrayLetters = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
    };

    public static void main(String[] args) throws IOException {
//        String tmp = new String(ArrayLetters);
//        System.out.println("ArrayLetters=" + tmp);
//        byteArrayOutput();
//        byteArrayInput();


//        fileOutput();
//        fileInput();
/*
        SenderStream t1 = new SenderStream();
        ReceiverStream t2 = new ReceiverStream();
        PipedOutputStream out = t1.getOutputStream();
        PipedInputStream in = t2.getInputStream();
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

//        objectOutput();
//        objectInput();

//        bufferOutput();
//        bufferInput();

        dataOutput();
        dataInput();
    }


    static void byteArrayOutput() {
        // 创建ByteArrayOutputStream字节流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 依次写入“A”、“B”、“C”三个字母。0x41对应A，0x42对应B，0x43对应C。
        baos.write(0x41);
        baos.write(0x42);
        baos.write(0x43);
        System.out.printf("baos=%s\n", baos);

        // 将ArrayLetters数组中从“3”开始的后5个字节写入到baos中。
        // 即对应写入“0x64, 0x65, 0x66, 0x67, 0x68”，即“defgh”
        baos.write(ArrayLetters, 3, 5);
        System.out.printf("baos=%s\n", baos);

        // 计算长度
        int size = baos.size();
        System.out.printf("size=%s\n", size);

        // 转换成byte[]数组
        byte[] buf = baos.toByteArray();
        String str = new String(buf);
        System.out.printf("str=%s\n", str);

        // 将baos写入到另一个输出流中
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            baos.writeTo((OutputStream) baos2);
            System.out.printf("baos2=%s\n", baos2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void byteArrayInput() throws IOException {
        // 创建ByteArrayInputStream字节流，内容是ArrayLetters数组
        ByteArrayInputStream bais = new ByteArrayInputStream(ArrayLetters);

        // 从字节流中读取5个字节
        for (int i = 0; i < LEN; i++) {
            // 若能继续读取下一个字节，则读取下一个字节
            if (bais.available() >= 0) {
                // 读取“字节流的下一个字节”
                int tmp = bais.read();
                System.out.printf("%d : 0x%s\n", i, Integer.toHexString(tmp));
            }
        }

        // 若“该字节流”不支持标记功能，则直接退出
        if (!bais.markSupported()) {
            System.out.println("make not supported!");
            return;
        }

        // 标记“字节流中下一个被读取的位置”。即--标记“0x66”，因为因为前面已经读取了5个字节，所以下一个被读取的位置是第6个字节”
        // (01), ByteArrayInputStream类的mark(0)函数中的“参数0”是没有实际意义的。
        // (02), mark()与reset()是配套的，reset()会将“字节流中下一个被读取的位置”重置为“mark()中所保存的位置”
        bais.mark(0);

        // 跳过5个字节。跳过5个字节后，字节流中下一个被读取的值应该是“0x6B”。
        bais.skip(5);

        // 从字节流中读取5个数据。即读取“0x6B, 0x6C, 0x6D, 0x6E, 0x6F”
        byte[] buf = new byte[LEN];
        bais.read(buf, 0, LEN);
        // 将buf转换为String字符串。“0x6B, 0x6C, 0x6D, 0x6E, 0x6F”对应字符是“klmno”
        String str1 = new String(buf);
        System.out.printf("str1=%s\n", str1);

        // 重置“字节流”：即，将“字节流中下一个被读取的位置”重置到“mark()所标记的位置”，即0x66。
        bais.reset();
        // 从“重置后的字节流”中读取5个字节到buf中。即读取“0x66, 0x67, 0x68, 0x69, 0x6A”
        bais.read(buf, 0, LEN);
        // 将buf转换为String字符串。“0x66, 0x67, 0x68, 0x69, 0x6A”对应字符是“fghij”
        String str2 = new String(buf);
        System.out.printf("str2=%s\n", str2);
    }

    static void fileOutput() {
        try {
            // 创建文件“file.txt”对应File对象
            File file = new File("file.txt");
            // 创建文件“file.txt”对应的FileOutputStream对象，默认是关闭“追加模式”
            FileOutputStream fileOut1 = new FileOutputStream(file);
            // 创建FileOutputStream对应的PrintStream，方便操作。PrintStream的写入接口更便利
            PrintStream out1 = new PrintStream(fileOut1);
            // 向“文件中”写入26个字母
            out1.print("abcdefghijklmnopqrstuvwxyz");
            out1.close();

            // 创建文件“file.txt”对应的FileOutputStream对象，打开“追加模式”
            FileOutputStream fileOut2 = new FileOutputStream(file, true);
            // 创建FileOutputStream对应的PrintStream，方便操作。PrintStream的写入接口更便利
            PrintStream out2 = new PrintStream(fileOut2);
            // 向“文件中”写入"0123456789"+换行符
            out2.println("0123456789");
            out2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void fileInput() {
        try {
            // 方法1：新建FileInputStream对象
            // 新建文件“file.txt”对应File对象
            File file = new File("file.txt");
            FileInputStream in1 = new FileInputStream(file);

            // 方法2：新建FileInputStream对象
            FileInputStream in2 = new FileInputStream("file.txt");

            // 方法3：新建FileInputStream对象
            // 获取文件“file.txt”对应的“文件描述符”
            FileDescriptor fdin = in2.getFD();
            // 根据“文件描述符”创建“FileInputStream”对象
            FileInputStream in3 = new FileInputStream(fdin);

            // 测试read()，从中读取一个字节
            char c1 = (char) in1.read();
            System.out.println("c1=" + c1);

            // 测试skip(long byteCount)，跳过25个字符
            in1.skip(25);

            // 测试read(byte[] buffer, int byteOffset, int byteCount)
            byte[] buf = new byte[10];
            in1.read(buf, 0, buf.length);
            System.out.println("buf=" + (new String(buf)));


            // 创建“FileInputStream”对象对应的BufferedInputStream
            BufferedInputStream bufIn = new BufferedInputStream(in3);
            // 读取一个字节
            char c2 = (char) bufIn.read();
            System.out.println("c2=" + c2);

            in1.close();
            in2.close();
            in3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void objectOutput() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("box.tmp"));
            out.writeBoolean(true);
            out.writeByte((byte) 65);
            out.writeChar('a');
            out.writeInt(20131015);
            out.writeFloat(3.14F);
            out.writeDouble(1.414D);
            // 写入HashMap对象
            HashMap map = new HashMap();
            map.put("one", "red");
            map.put("two", "green");
            map.put("three", "blue");
            out.writeObject(map);
            // 写入自定义的Box对象，Box实现了Serializable接口
            Box box = new Box("desk", 80, 48);
            out.writeObject(box);
            System.out.println("box: " + box);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void objectInput() {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("box.tmp"));
            System.out.printf("boolean:%b\n", in.readBoolean());
            System.out.printf("byte:%d\n", (in.readByte() & 0xff));
            System.out.printf("char:%c\n", in.readChar());
            System.out.printf("int:%d\n", in.readInt());
            System.out.printf("float:%f\n", in.readFloat());
            System.out.printf("double:%f\n", in.readDouble());
            // 读取HashMap对象
            HashMap map = (HashMap) in.readObject();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                System.out.printf("%-6s -- %s\n", entry.getKey(), entry.getValue());
            }
            // 读取Box对象，Box实现了Serializable接口
            Box box = (Box) in.readObject();
            System.out.println("box: " + box);//因为width是静态的,所以这里取的还是内存中的80

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void bufferOutput(){
        // 创建“文件输出流”对应的BufferedOutputStream
        // 它对应缓冲区的大小是16，即缓冲区的数据>=16时，会自动将缓冲区的内容写入到输出流。
        try {
            File file = new File("bufferedinputstream.txt");
            OutputStream out =
                    new BufferedOutputStream(
                            new FileOutputStream(file), 16);

            // 将ArrayLetters数组的前10个字节写入到输出流中
            out.write(ArrayLetters, 0, 10);
            // 将“换行符\n”写入到输出流中
            out.write('\n');

            // TODO!
            //out.flush();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void bufferInput() {
        // 创建BufferedInputStream字节流，内容是ArrayLetters数组
        try {
            File file = new File("bufferedinputstream.txt");
            InputStream in =
                    new BufferedInputStream(
                            new FileInputStream(file), 512);

            // 从字节流中读取5个字节。“abcde”，a对应0x61，b对应0x62，依次类推...
            for (int i = 0; i < LEN; i++) {
                // 若能继续读取下一个字节，则读取下一个字节
                if (in.available() >= 0) {
                    // 读取“字节流的下一个字节”
                    int tmp = in.read();
                    System.out.printf("%d : 0x%s\n", i, Integer.toHexString(tmp));
                }
            }

            // 若“该字节流”不支持标记功能，则直接退出
            if (!in.markSupported()) {
                System.out.println("make not supported!");
                return;
            }

            // 标记“当前索引位置”，即标记第6个位置的元素--“f”
            // 1024对应marklimit
            in.mark(1024);

            // 跳过22个字节。
            in.skip(22);

            // 读取5个字节
            byte[] buf = new byte[LEN];
            in.read(buf, 0, LEN);
            // 将buf转换为String字符串。
            String str1 = new String(buf);
            System.out.printf("str1=%s\n", str1);

            // 重置“输入流的索引”为mark()所标记的位置，即重置到“f”处。
            in.reset();
            // 从“重置后的字节流”中读取5个字节到buf中。即读取“fghij”
            in.read(buf, 0, LEN);
            // 将buf转换为String字符串。
            String str2 = new String(buf);
            System.out.printf("str2=%s\n", str2);

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void dataOutput() {

        try {
            File file = new File("file.txt");
            DataOutputStream out =
                    new DataOutputStream(
                            new FileOutputStream(file));

            out.writeBoolean(true);
            out.writeByte((byte)0x41);
            out.writeChar((char)0x4243);
            out.writeShort((short)0x4445);
            out.writeInt(0x12345678);
            out.writeLong(0x0FEDCBA987654321L);

            out.writeUTF("abcdefghijklmnopqrstuvwxyz严12");

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void dataInput() {

        try {
            File file = new File("file.txt");
            DataInputStream in =
                    new DataInputStream(
                            new FileInputStream(file));

            System.out.printf("byteToHexString(0x8F):0x%s\n", byteToHexString((byte)0x8F));
            System.out.printf("charToHexString(0x8FCF):0x%s\n", charToHexString((char)0x8FCF));

            System.out.printf("readBoolean():%s\n", in.readBoolean());
            System.out.printf("readByte():0x%s\n", byteToHexString(in.readByte()));
            System.out.printf("readChar():0x%s\n", charToHexString(in.readChar()));
            System.out.printf("readShort():0x%s\n", shortToHexString(in.readShort()));
            System.out.printf("readInt():0x%s\n", Integer.toHexString(in.readInt()));
            System.out.printf("readLong():0x%s\n", Long.toHexString(in.readLong()));
            System.out.printf("readUTF():%s\n", in.readUTF());

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 打印byte对应的16进制的字符串
    private static String byteToHexString(byte val) {
        return Integer.toHexString(val & 0xff);
    }

    // 打印char对应的16进制的字符串
    private static String charToHexString(char val) {
        return Integer.toHexString(val);
    }

    // 打印short对应的16进制的字符串
    private static String shortToHexString(short val) {
        return Integer.toHexString(val & 0xffff);
    }
}

class Box implements Serializable {

//    序列化对static和transient变量，是不会自动进行状态保存的。
//    用transient声明的变量，不会被自动序列化

    private static int width;
    private transient int height;
    private String name;

    public Box(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "[" + name + ": (" + width + ", " + height + ") ]";
    }
}

/**
 * 接收者线程
 */
class ReceiverStream extends Thread {

    // 管道输入流对象。
    // 它和“管道输出流(PipedOutputStream)”对象绑定，
    // 从而可以接收“管道输出流”的数据，再让用户读取。
    private PipedInputStream in = new PipedInputStream();

    // 获得“管道输入流”对象
    public PipedInputStream getInputStream() {
        return in;
    }

    @Override
    public void run() {
        readMessageOnce();
        //readMessageContinued() ;
    }

    // 从“管道输入流”中读取1次数据
    public void readMessageOnce() {
        // 虽然buf的大小是2048个字节，但最多只会从“管道输入流”中读取1024个字节。
        // 因为，“管道输入流”的缓冲区大小默认只有1024个字节。
        byte[] buf = new byte[2048];
        try {
            int len = in.read(buf);
            System.out.println(new String(buf, 0, len));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从“管道输入流”读取>1024个字节时，就停止读取
    public void readMessageContinued() {
        int total = 0;
        while (true) {
            byte[] buf = new byte[1024];
            try {
                int len = in.read(buf);
                total += len;
                System.out.println(new String(buf, 0, len));
                // 若读取的字节总数>1024，则退出循环。
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
class SenderStream extends Thread {

    // 管道输出流对象。
    // 它和“管道输入流(PipedInputStream)”对象绑定，
    // 从而可以将数据发送给“管道输入流”的数据，然后用户可以从“管道输入流”读取数据。
    private PipedOutputStream out = new PipedOutputStream();

    // 获得“管道输出流”对象
    public PipedOutputStream getOutputStream() {
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
            out.write(strInfo.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 向“管道输出流”中写入一则较长的消息
    private void writeLongMessage() {
        StringBuilder sb = new StringBuilder();
        // 通过for循环写入1020个字节
        for (int i = 0; i < 102; i++)
            sb.append("0123456789");
        // 再写入26个字节。
        sb.append("abcdefghijklmnopqrstuvwxyz");
        // str的总长度是1020+26=1046个字节
        String str = sb.toString();
        try {
            // 将1046个字节写入到“管道输出流”中
            out.write(str.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
