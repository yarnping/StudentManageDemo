package demo.repository;

import demo.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wang on 16/7/27.
 */
public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setCode(resultSet.getString("code"));
        student.setName(resultSet.getString("sname"));
        student.setComment(resultSet.getString("comment"));
        student.setStatus(resultSet.getInt("status"));
        student.setStudentRole(resultSet.getInt("student_role_id"));
        student.setCreateTime(resultSet.getInt("create_time"));
        student.setLastLoginTime(resultSet.getInt("last_login_time"));
        return student;
    }
}
