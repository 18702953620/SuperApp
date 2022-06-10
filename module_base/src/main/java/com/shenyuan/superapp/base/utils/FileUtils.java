package com.shenyuan.superapp.base.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * @author ch
 * @date 2021/3/10 13:30
 * desc
 */
public class FileUtils {

    /**
     * 格式化单位
     *
     * @param size size
     * @return String
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = BigDecimal.valueOf(kiloByte);
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = BigDecimal.valueOf(megaByte);
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = BigDecimal.valueOf(gigaByte);
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = BigDecimal.valueOf(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取缓存目录大小
     *
     * @param file file
     * @return String
     */
    public static String getCacheSize(File file) {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 获取 文件夹大小
     *
     * @param file file
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 将文件写入本地
     *
     * @param destFileDir  目标文件夹
     * @param destFileName 目标文件名
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public static File saveFile(InputStream is, String destFileDir, String destFileName) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return file;

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param is           is
     * @param start        起始位置
     * @param destFileDir  destFileDir
     * @param destFileName destFileName
     * @return File
     */
    public static File saveFile(InputStream is, long start, String destFileDir, String destFileName) {
        RandomAccessFile raf = null;
        File file = null;
        int len;
        try {
            file = new File(destFileDir, destFileName);

            raf = new RandomAccessFile(file, "rw");
            byte[] fileReader = new byte[4096];

            raf.seek(start);

            while ((len = is.read(fileReader)) != -1) {
                raf.write(fileReader, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }

    /**
     * Delete the directory.
     *
     * @param dir The directory.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) {
            return false;
        }
        // dir doesn't exist then return true
        if (!dir.exists()) {
            return true;
        }
        // dir isn't a directory then return false
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists()) {
            for (File item : directory.listFiles()) {
                if (item.isDirectory()) {
                    deleteFilesByDirectory(item);
                } else {
                    item.delete();
                }
            }
        }
    }

    /**
     * 读取assets 下文件
     *
     * @param context  context
     * @param fileName fileName
     * @return String
     */
    public static String readAssetsFile(Context context, String fileName) {
        StringBuilder reslut = new StringBuilder();
        InputStream in;
        try {
            in = context.getAssets().open(fileName);
            //将字节转换为字符
            InputStreamReader isReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            //使用bufferReader去读取内容
            BufferedReader reader = new BufferedReader(isReader);
            String out = "";
            while ((out = reader.readLine()) != null) {
                reslut.append(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reslut.toString();
    }

    /**
     * 是否图片
     *
     * @param fileName fileName
     * @return boolean
     */
    public static boolean isImage(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            if (fileName.contains(".")) {
                String[] na = fileName.split("[.]");
                if (na == null || na.length == 0) {
                    return false;
                }
                String type = na[na.length - 1];
                return type.equalsIgnoreCase("png") ||
                        type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("jpeg");
            }
        }
        return false;
    }

    /**
     * 是否视频
     *
     * @param fileName fileName
     * @return boolean
     */
    public static boolean isVideo(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            if (fileName.contains(".")) {
                String[] na = fileName.split("[.]");
                if (na == null || na.length == 0) {
                    return false;
                }
                String type = na[na.length - 1];
                return type.equalsIgnoreCase("mp4") ||
                        type.equalsIgnoreCase("avi") || type.equalsIgnoreCase("rmvb")
                        || type.equalsIgnoreCase("wmv") || type.equalsIgnoreCase("mov")
                        || type.equalsIgnoreCase("flv")
                        || type.equalsIgnoreCase("mkv");
            }
        }
        return false;
    }

}
