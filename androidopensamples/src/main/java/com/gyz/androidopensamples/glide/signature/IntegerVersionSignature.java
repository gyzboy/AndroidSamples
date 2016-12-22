package com.gyz.androidopensamples.glide.signature;

import com.bumptech.glide.load.Key;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Created by guoyizhe on 2016/11/17.
 * 邮箱:gyzboy@126.com
 */

public class IntegerVersionSignature implements Key {

    private int currentVersion;

    public IntegerVersionSignature(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntegerVersionSignature) {
            IntegerVersionSignature other = (IntegerVersionSignature) obj;
            return currentVersion == other.currentVersion;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return currentVersion;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        messageDigest.update(ByteBuffer.allocate(Integer.SIZE).putInt(currentVersion).array());
    }
}
