package demo.repository;

import demo.util.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import demo.entity.Student;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wang on 16/7/26.
 */
@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Student create(final Student student) {
        student.setCode(IdGen.getInstance().nextId()+"");
        student.setCreateTime(new Date().getTime()/1000);
        String sql = "insert into student(code, sname, comment, status, student_role_id, create_time, last_login_time) " +
                "values (?,?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, student.getCode());
                preparedStatement.setString(2, student.getName());
                preparedStatement.setString(3, student.getComment());
                preparedStatement.setInt(4, student.getStatus());
                preparedStatement.setInt(5, student.getStudentRole());
                preparedStatement.setLong(6, student.getCreateTime());
                preparedStatement.setLong(7, student.getLastLoginTime());
                return preparedStatement;
            }
        }, holder);

        int newId = holder.getKey().intValue();
        student.setId(newId);
        return student;
    }

    public void delete(Student student) {
        this.deleteById(student.getId());
    }

    public void deleteById(int id) {
        String sql = "delete from student where id = ?";
        jdbcTemplate.update(sql, new Object[]{id}, new int[]{Types.INTEGER});
    }

    public void update(final Student student) {
        jdbcTemplate.update("UPDATE student SET sname=?, comment=?, status=?, student_role_id=?",
                new Object[]{student.getName(), student.getComment(), student.getStatus(), student.getStudentRole()});
    }

    /**
     * 启用账户
     * @param student
     */
    public void enableAccount(Student student) {
        student.setStatus(1);
        this.update(student);
    }

    /**
     * 禁用账户
     * @param student
     */
    public void disableAccount(Student student) {
        student.setStatus(0);
        this.update(student);
    }

    public int countAll(){
        String sql = "SELECT COUNT(*) FROM student";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Transactional
    public List<Student> findAll() {
        return jdbcTemplate.query("select * from student", new StudentRowMapper());
    }

    @Transactional
    public List<Student> findByPage(int start, int length) {
        if (length == -1) {
            return findAll();
        }
        return jdbcTemplate.query("select * from student limit ?,?", new Object[]{start, length}, new StudentRowMapper());
    }

    @Transactional
    public Student findById(int id) {
        return jdbcTemplate.queryForObject("select * from student where id=?", new Object[]{id}, new StudentRowMapper());
    }

    @Transactional
    public List<Student> filter(String code, String name, Long startTime, Long endTime, Integer role) {

        List params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder("SELECT * FROM student where");
        if(!StringUtils.isEmpty(code)){
            sb.append(" code like ?");
            params.add("%"+code+"%");
        }
        if(!StringUtils.isEmpty(name)){
            if(params.size() != 0){
                sb.append(" and");
            }
            sb.append(" sname like ?");
            params.add("%"+name+"%");
        }
        if(startTime != null && endTime != null && startTime > 0 && endTime > 0) {
            if(params.size() != 0){
                sb.append(" and");
            }
            sb.append(" create_time between ? and ?");
            params.add(startTime);
            params.add(endTime);
        }
        if (role != null && role != -1) {
            if(params.size() != 0){
                sb.append(" and");
            }
            sb.append(" student_role_id = ?");
            params.add(role);
        }
        sb.append(";");

        String pSql = sb.toString();
        if(pSql.contains("where;")){
            pSql = pSql.replace("where;", ";");
        }
        return jdbcTemplate.query(pSql, params.toArray(), new StudentRowMapper());
    }
    @Transactional
    public List<Student> filterByPage(String code, String name, Long startTime, Long endTime, Integer role, int start, int page) {

        List params = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder("SELECT * FROM student where");
        if(!StringUtils.isEmpty(code)){
            sb.append(" code like ?");
            params.add("%"+code+"%");
        }
        if(!StringUtils.isEmpty(name)){
            if(params.size() != 0){
                sb.append(" and");
            }
            sb.append(" sname like ?");
            params.add("%"+name+"%");
        }
        if(startTime != null && endTime != null) {
            if(params.size() != 0){
                sb.append(" and");
            }
            sb.append(" create_time between ? and ?");
            params.add(startTime);
            params.add(endTime);
        }
        if (role != null) {
            if(params.size() != 0){
                sb.append(" and");
            }
            sb.append(" student_role_id = ?");
            params.add(role);
        }
        params.add(start);
        params.add(page);
        sb.append(" limit ?,?;");
        return jdbcTemplate.query(sb.toString(), params.toArray(), new StudentRowMapper());
    }
}
