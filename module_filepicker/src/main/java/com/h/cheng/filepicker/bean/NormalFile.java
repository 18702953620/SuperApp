package com.h.cheng.filepicker.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ch
 * @date 2020/8/21-15:05
 * @desc
 */
public class NormalFile implements Parcelable {
    private String mimeType;
    private long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * byte
     */
    private long size;
    /**
     * Directory ID
     */
    private String bucketId;
    /**
     * Directory Name
     */
    private String bucketName;
    /**
     * Added Date
     */
    private long date;
    /**
     * 是否选中
     */
    private boolean isSelected;

    public NormalFile() {
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected NormalFile(Parcel in) {
        mimeType = in.readString();
        id = in.readLong();
        name = in.readString();
        path = in.readString();
        size = in.readLong();
        bucketId = in.readString();
        bucketName = in.readString();
        date = in.readLong();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<NormalFile> CREATOR = new Creator<NormalFile>() {
        @Override
        public NormalFile createFromParcel(Parcel in) {
            return new NormalFile(in);
        }

        @Override
        public NormalFile[] newArray(int size) {
            return new NormalFile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mimeType);
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(path);
        parcel.writeLong(size);
        parcel.writeString(bucketId);
        parcel.writeString(bucketName);
        parcel.writeLong(date);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        NormalFile file = (NormalFile) o;
        return this.path.equals(file.path);
    }
}
