package com.shenyuan.superapp.common.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 17:08
 * desc
 */
public class Node<T> extends BaseExpandNode {

    private T id;

    private T pid;

    private List<BaseNode> child;

    public Node(T id, T pid, List<BaseNode> child) {
        this.id = id;
        this.pid = pid;
        this.child = child;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getPid() {
        return pid;
    }

    public void setPid(T pid) {
        this.pid = pid;
    }

    public List<BaseNode> getChild() {
        return child;
    }

    public void setChild(List<BaseNode> child) {
        this.child = child;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return child;
    }
}
