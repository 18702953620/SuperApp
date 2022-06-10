package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/5 11:09
 * desc
 */
public class FileBean {
    /**
     * createTime : 2021-03-05 11:01:23
     * updateTime : 2021-03-05 11:01:23
     * id : 29
     * name : timg.jpg
     * isImg : true
     * contentType : image/jpeg
     * size : 8407
     * path : null
     * url : http://192.168.30.139:9000/jysyfile/timg.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210305%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210305T030123Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=39e12d0ad77b796dc0219433c290a666115a715402bae4a84254b953eed830e3
     * source : minio
     */

    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "updateTime")
    private String updateTime;
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "isImg")
    private Boolean isImg;
    @JSONField(name = "contentType")
    private String contentType;
    @JSONField(name = "size")
    private Integer size;
    @JSONField(name = "path")
    private String path;
    @JSONField(name = "url")
    private String url;
    @JSONField(name = "source")
    private String source;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsImg() {
        return isImg;
    }

    public void setIsImg(Boolean isImg) {
        this.isImg = isImg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
