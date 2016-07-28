package demo.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import demo.entity.Student;
import demo.entity.StudentRole;
import demo.util.Tool;

import java.util.List;

/**
 * Created by wang on 16/7/28.
 */
public class StudentJsonHelper {
    private List<Student> studentList;
    JsonObject resultJson = new JsonObject();
    JsonArray datas = new JsonArray();

    public StudentJsonHelper(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void addProperty(String key, int value) {
        resultJson.addProperty(key, value);
    }
    public void addProperty(String key, String value) {
        resultJson.addProperty(key, value);
    }

    @Override
    public String toString() {

        for (Student student : studentList) {
            JsonArray jsonArray = new JsonArray();

            jsonArray.add(student.getCode());
            jsonArray.add(student.getName());
            jsonArray.add(student.getComment());
            jsonArray.add(student.getStatus() == 1?"正常": "未启用");
            jsonArray.add(StudentRole.valueOf(student.getStudentRole()).getRoleName());
            jsonArray.add(Tool.formateDate(student.getCreateTime()));
            datas.add(jsonArray);
        }
        resultJson.add("data", datas);
        return resultJson.toString();
    }
}
