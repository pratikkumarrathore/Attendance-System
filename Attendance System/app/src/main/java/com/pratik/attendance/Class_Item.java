package com.pratik.attendance;

public class Class_Item {
    private String className;
    private String subjectName;
    private long cid;

    public Class_Item(String className, String subjectName) {
        this.className = className;
        this.subjectName = subjectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Class_Item(long cid, String className, String subjectName) {
        this.cid = cid;
        this.className = className;
        this.subjectName = subjectName;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
