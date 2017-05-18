package com.gyz.javasamples.classloader;

import java.io.File;

/**
 * Created by gyzboy on 2017/5/17.
 */

public class PathUtils {
    public static String localPath(String name) {
        String subPath = "javasamples.src.main.java.";
        String packageName = "com.gyz.javasamples.classloader";
        return new File(".").getAbsolutePath().replace(".", "") + subPath.replace('.',File.separatorChar) +packageName.replace('.', File.separatorChar) + "/"+name
            .replace('.', File.separatorChar) + ".class";
    }
}
