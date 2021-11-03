package com.test.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 这种方法就是把文件的内容被映像到计算机虚拟内存的一块区域，从而可以直接操作内存当中的数据而无需每次都通过 I/O 去物理硬盘读取文件。
 * 这是由当前 java 态进入到操作系统内核态，由操作系统读取文件，再返回数据到当前 java 态的过程。这样就能大幅提高我们操作大文件的速度。
 * 一个内存文件映射不够用，那么试一试用多个就可以了
 * */
public class MappedBiggerFileReader {
    private MappedByteBuffer[] mappedByteBuffers;
    private int count, number, arraySize;
    private long fileLength;
    private byte[] array;
    private FileInputStream fileInputStream;
    public MappedBiggerFileReader(String fileName, int arraySize) throws IOException {
        this.fileInputStream = new FileInputStream(fileName);
        FileChannel fileChannel = fileInputStream.getChannel();
        this.fileLength = fileChannel.size();
        this.number = (int) Math.ceil((double) fileLength / (double) Integer.MAX_VALUE);
        // 内存文件映射数组
        this.mappedByteBuffers = new MappedByteBuffer[number];
        long preLength = 0;
        // 映射区域的大小
        long regionSize = Integer.MAX_VALUE;
        // 将文件的连续区域映射到内存文件映射数组中
        for (int i = 0; i < number; i++) {
            if (fileLength - preLength < Integer.MAX_VALUE) {
                //最后一片区域的大小
                regionSize = fileLength - preLength;
            }
            mappedByteBuffers[i] = fileChannel.map(FileChannel.MapMode.READ_ONLY, preLength, regionSize);
            // 下一片区域的开始
            preLength += regionSize;
        }
        this.arraySize = arraySize;
    }
    public int read(){
        if (count >= number) {
            return -1;
        }
        int limit = mappedByteBuffers[count].limit();
        int position = mappedByteBuffers[count].position();
        if (limit - position > arraySize) {
            array = new byte[arraySize];
            mappedByteBuffers[count].get(array);
            return arraySize;
        } else {
            // 本内存文件映射最后一次读取数据
            array = new byte[limit - position];
            mappedByteBuffers[count].get(array);
            if (count < number) {
                // 转换到下一个内存文件映射
                count++;
            }
            return limit - position;
        }
    }

    public void close() throws IOException {
        fileInputStream.close();
        array = null;
    }

    public byte[] getArray() {
        return array;
    }

    public long getFileLength() {
        return fileLength;
    }

    public static void main(String[] args) throws IOException {
        MappedBiggerFileReader reader = new MappedBiggerFileReader("D:\\15-4月-21_15-38-39_shell (1).log", 10000);
        long start = System.nanoTime();
        while (reader.read() != -1) {
            System.out.println("content : " + new String(reader.getArray(), StandardCharsets.UTF_8));
        }
        long end = System.nanoTime();
        reader.close();
        System.out.println("MappedBiggerFileReader: " + (end - start));
    }
}
