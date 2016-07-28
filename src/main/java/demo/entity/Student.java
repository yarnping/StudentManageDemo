package demo.entity;

/**
 * Created by wang on 16/7/26.
 */
public class Student {
    private int id;
    private String code;
    private String name;
    private String comment;
    private int status;
    private int studentRole;
    private long createTime;
    private long lastLoginTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStudentRole() {
        return studentRole;
    }

    public void setStudentRole(int studentRole) {
        this.studentRole = studentRole;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "Student{ id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", comment=" + comment +
                ", status=" + status +
                ", studentRole=" + studentRole +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime + " }";
    }
}
