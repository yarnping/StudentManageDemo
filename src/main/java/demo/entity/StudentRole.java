package demo.entity;

/**
 * Created by wang on 16/7/27.
 */
public enum StudentRole {
    ROLE1(1,"role1"),
    ROLE2(2,"role2"),
    ROLE3(3,"role3");

    private int roleId;
    private String roleName;
    StudentRole(int id, String rolename) {
        this.roleId = id;
        this.roleName = rolename;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public static StudentRole valueOf(int roleId) {
        switch (roleId) {
            case 1 : return ROLE1;
            case 2 : return ROLE2;
            case 3 : return ROLE3;
            default: return ROLE1;
        }
    }
}
