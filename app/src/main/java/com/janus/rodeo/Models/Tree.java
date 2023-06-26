package com.janus.rodeo.Models;

public class Tree {
    private int id;
    private String parent;
    private String text;
    private String icon;
    private boolean children;
    private String key;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public String toStringValues() {
        return "Tree{" +
                "id=" + id +
                ", parent=" + parent +
                ", text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }

}