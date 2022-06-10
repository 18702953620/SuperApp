package com.shenyuan.superapp.common.api.down;

import com.shenyuan.superapp.common.bean.BaseChooseBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/10 13:22
 * desc
 */
public class DownModel extends BaseChooseBean {


    public static final int DOWN_DEFAULT = 0;
    public static final int DOWN_PAUSE = 1;
    public static final int DOWN_FAIL = 2;
    public static final int DOWN_FINISH = 3;

    public static final int UPLOAD_DEFAULT = 0;
    public static final int UPLOAD_PAUSE = 1;
    public static final int UPLOAD_FAIL = 2;
    public static final int UPLOAD_FINISH = 3;

    //id
    private String tag;
    private long downSize;
    private long totalSize;
    //链接
    private String url;
    //文件夹
    private String destDir;
    //文件名称
    private String name;
    //状态
    private int downState;

    private int uploadState;
    //文件大小
    private String fileSize;
    /**
     * 0-下载
     * 1-上传
     */
    private int type;
    /**
     * 是否文件夹
     */
    private boolean isDir;

    /**
     * 子文件
     */
    private List<ChildFile> files;

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public List<ChildFile> getFiles() {
        return files;
    }

    public void setFiles(List<ChildFile> files) {
        this.files = files;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getDownSize() {
        return downSize;
    }

    public int getUploadState() {
        return uploadState;
    }

    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }

    public void setDownSize(long downSize) {
        this.downSize = downSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDownState() {
        return downState;
    }

    public void setDownState(int downState) {
        this.downState = downState;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public static class ChildFile {
        private String url;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public void delete() {
        DownCommon.delete(getTag());
    }

    public void save() {
        DownCommon.add(this);
    }

    public void update() {
        DownCommon.update(this);
    }

    public void updateFinish() {
        DownCommon.updateFinish(getTag());
    }

    public void updateFail() {
        DownCommon.updateFail(getTag());
    }
}
