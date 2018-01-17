package com.gyz.javasamples.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by guoyizhe on 2017/2/13.
 * 邮箱:gyzboy@126.com
 */

public class NioTest {
    //Java Nio的核心:
    // channel:有点像流,数据可以从channel读到Buffer中,也可以从buffer写到channel中
    // buffers:缓存区,通道中的数据总是要先读到一个buffer,或者总是要从一个buffer写入
    // selectors:Selector允许单线程处理多个 Channel。如果你的应用打开了多个连接（通道），但每个连接的流量都很低，使用Selector就会很方便。

//    缓冲区：
//    在javaNIO中负责数据的存取。底层就是数组，用于存储不同数据类型的数据。根据数据类型不同，提供了相应类型的缓冲区（Boolean除外），
//    ByteBuffer
//    ShortBuffer
//    IntBUffer
//    LongBuffer
//    FolatBuffer
//    DoubleBuffer
//    上述缓冲区的管理方式基本相同，通过allocate（）获取相应的缓冲区


//    缓冲区核心方法：
//    put:向缓冲区中存入数据
//    get:从缓冲区中取出数据
//    flip：切换模式（从写模式切换到读模式）
//    rewind:可重复读取数据，将position的值置为0
//    clear：清空缓冲区，回到最初的状态，其中的数据并没有被清空。数据处于被遗忘的状态。
//    hasRemaining：判断缓冲区中是否还有可以读取的数据
//    *remaining：如果有可以读取的数据，看看还有多少个。


//    缓冲区核心属性：
//    capacity：缓冲区中最大存储数据的容量，一旦声明不能改变。
//            *limit：表示缓冲区中可以操作数据的大小，limit后面的数据是不能够进行读写的。
//    position：表示换缓冲区中正在操作数据的位置。
//    mark:标记，可以记录position的位置，可以通过reset恢复position的状态。


//    直接缓冲区和非直接缓冲区:
//    非直接缓冲区：通过allocate（）方法获取缓冲区，将缓冲区建立在jvm的内存中。直接缓冲区只有ByteBuffer支持
//    直接缓冲区：通过allocateDirect（）分配直接缓冲区，将换缓冲区建立在操作系统的物理内存中。
    public static void main(String[] args) {

//        char[] buffer = {'1', '2', '3', '4','7',
//                '5', '6', '7', '8','8',
//                '9', 'a', 'b', 'c','8',
//                'd', 'e', 'f', 'g','1',
//                '9', 'a', 'b', 'c','8'};
//
//        int width = 5;
//        int height = 5;
//
//        for (int i = 0; i < height /2; i++) {
//            for (int j = 0; j < width; j++) {
//                char c = buffer[j + i * width];
//                buffer[j + i * width] = buffer[j + width * (height - 1 - i)];
//                buffer[j + width * (height - 1 - i)] = c;
//            }
//        }
//
//        for (int i = 0; i < buffer.length; i++) {
//            System.out.println(buffer[i]);
//        }

        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }

        int i = 1;
        for (i = 2 ; i < 10 ; i++){
            System.out.println(i);
        }
    }

    private static void testFileChannel() throws IOException {
//FileChannel 从文件中读写数据。
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {//read方法返回的int值表示了有多少字节被读入到了Buffer中,返回-1,表示到了文件末尾

            System.out.println("Read " + bytesRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        System.out.println("fileSize = " + inChannel.size());
        inChannel.truncate(1024);//截取文件前1024个字节,文件将指定长度后面的部分将被删除
        inChannel.force(true);//将通道中尚未写入磁盘的数据强制写入到磁盘上
        aFile.close();
    }

    private static void testBuffer() throws IOException {
//        1.写入数据到Buffer,buffer会记录写入了多少数据
//        2.调用flip()方法,通过flip方法将buffer从写模式切换到读模式
//        3.从Buffer中读取数据
//        4.调用clear()方法或者compact()方法,读完了所有数据需要清空缓冲区,让它可以再次被写入

// Buffer的三要素:
//        capacity
//        作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
//
//        position
//        当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
//        当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
//
//        limit
//        在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
//        当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）

        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf); //read into buffer.
        int bytesWrite = inChannel.write(buf);//write from buffer
        while (bytesRead != -1) {

            buf.flip();  //调用flip()方法会将position设回0，并将limit设置成之前position的值。

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get()); // read 1 byte at a time
            }
//        buf.rewind();可以重读buffer中的所有数据
            buf.clear(); //position将被设回0，limit被设置成 capacity的值。换句话说，Buffer 被清空了。Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
//            buf.compact();//将所有唯独的数据拷贝到buffer起始处,然后将position设到最后一个未读元素正后面
            bytesRead = inChannel.read(buf);//从channel写到buffer
//            buf.put()//通过put方法写buffer
        }
        aFile.close();
    }

    private static void testScatterAndGather() throws IOException {
//分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
//聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。

        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel channel = aFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = {header, body};
        channel.read(bufferArray);//在移动下一个buffer前,必须填满当前的buffer,所以不适用于动态消息(消息大小不固定)
        channel.write(bufferArray);//会按照buffer在数组中的顺序,将数据写入到channel

    }

    private static void testTransfer() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

//        toChannel.transferFrom();//transferfrom方法可以将数据从源通道传输到FileChannel中
//        toChannel.transferTo()//transferTo()方法将数据从FileChannel传输到其他的channel中
    }

    public static void testSocketChannel() throws IOException {
        //Selector能够检测到多个通道,这样一个单独的线程可以管理多个channel,从而管理多个网络连接
//与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);//设置为非阻塞模式后可以与selector一起使用
        socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));

        Selector selector = Selector.open();
        SelectionKey selectedKeys = socketChannel.register(selector, SelectionKey.OP_READ);
        Iterator keyIterator = selector.selectedKeys().iterator();

        while (keyIterator.hasNext()) {
            SelectionKey key = (SelectionKey) keyIterator.next();
            if (key.isAcceptable()) {
                // a connection was accepted by a ServerSocketChannel.
            } else if (key.isConnectable()) {
                // a connection was established with a remote server.
            } else if (key.isReadable()) {
                // a channel is ready for reading
            } else if (key.isWritable()) {
                // a channel is ready for writing
            }
            keyIterator.remove();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = socketChannel.read(buf);

            String newData = "New String to write to file..." + System.currentTimeMillis();

            ByteBuffer buffer = ByteBuffer.allocate(48);
            buffer.clear();
            buffer.put(newData.getBytes());

            buf.flip();

            while (buffer.hasRemaining()) {
                socketChannel.write(buf);
            }
        }
        socketChannel.close();
    }

    public static void testPipe() throws IOException {
//        Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();//写数据，打开sink通道
        Pipe.SourceChannel sourceChannel = pipe.source();//读数据,打开source通道
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while (buf.hasRemaining()) {
            sinkChannel.write(buf);
        }

    }
}
