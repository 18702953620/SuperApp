package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 17:30
 * desc
 */
public class FileTreeBean extends BaseExpandNode {
    public FileTreeBean() {
        setExpanded(false);
    }

    /**
     * id : 20
     * name : title.jpg
     * parentId : 2
     * isFile : 1
     * filePath : http://192.168.30.139:9000/jysyfile/title.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210220%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210220T022005Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=1ea10fe174bfbdaa8db8de34c092fad5cca1df4912c9b34ef196c3d9472c5b53
     * suffix : .jpg
     * deleted : null
     * children : []
     */


    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "parentId")
    private Integer parentId;
    @JSONField(name = "isFile")
    private Integer isFile;
    @JSONField(name = "filePath")
    private String filePath;
    @JSONField(name = "suffix")
    private String suffix;
    @JSONField(name = "children")
    private List<FileTreeBean> children;

    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private List<BaseNode> childNode;

    public void setChildNode(List<BaseNode> childNode) {
        this.childNode = childNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsFile() {
        return isFile;
    }

    public void setIsFile(Integer isFile) {
        this.isFile = isFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<FileTreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<FileTreeBean> children) {
        this.children = children;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
