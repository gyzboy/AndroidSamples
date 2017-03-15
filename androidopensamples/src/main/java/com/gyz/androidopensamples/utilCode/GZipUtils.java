package com.gyz.androidopensamples.utilCode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by guoyizhe on 2017/3/15.
 * 邮箱:gyzboy@126.com
 */

public class GZipUtils {
    public static final int BUFFER = 1024;

    public GZipUtils() {
    }

    public static byte[] compress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compress(bais, baos);
        byte[] output = baos.toByteArray();

        try {
            baos.flush();
        } catch (Exception var5) {
            ;
        }

        baos.close();
        bais.close();
        return output;
    }

    public static void compress(InputStream is, OutputStream os) throws Exception {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        byte[] data = new byte[1024];

        int count;
        while((count = is.read(data, 0, 1024)) != -1) {
            gos.write(data, 0, count);
        }

        gos.finish();

        try {
            gos.flush();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        gos.close();
    }

    public static byte[] decompress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        decompress(bais, baos);
        data = baos.toByteArray();

        try {
            baos.flush();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        baos.close();
        bais.close();
        return data;
    }

    public static void decompress(InputStream is, OutputStream os) throws Exception {
        GZIPInputStream gis = new GZIPInputStream(is);
        byte[] data = new byte[1024];

        int count;
        while((count = gis.read(data, 0, 1024)) != -1) {
            os.write(data, 0, count);
        }

        try {
            gis.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}
