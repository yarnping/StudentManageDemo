package demo.controller;

import demo.entity.Student;
import demo.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping("/")
    String home() {
        return "stu_manage";
    }

    /**
     * 没有过滤条件的情况下拉取数据
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping("/jsontest")
    @ResponseBody
    String jsontest(@RequestParam(value = "draw") int draw, @RequestParam(value = "start") int start,
                    @RequestParam(value = "length") int length) {
        List<Student> students = studentRepository.findByPage(start, length);
        StudentJsonHelper jsonHelper = new StudentJsonHelper(students);

        int recordsTotal = studentRepository.countAll();
        int recordsFiltered = recordsTotal;

        jsonHelper.addProperty("draw", draw);
        jsonHelper.addProperty("recordsTotal", recordsTotal);
        jsonHelper.addProperty("recordsFiltered", recordsTotal);
        log.info("jsontest: draw: "+draw+" start: "+ start+" length: "+length);
        return jsonHelper.toString();
    }

    /**
     * 有过滤条件的情况下拉取数据
     * @param draw
     * @param start
     * @param length
     * @param code
     * @param name
     * @param role
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/stufilter")
    @ResponseBody
    String stufilter(@RequestParam(value = "draw") int draw, @RequestParam(value = "start") int start,
                     @RequestParam(value = "length") int length, @RequestParam(value = "code") String code,
                     @RequestParam(value = "name") String name, @RequestParam(value = "role") int role,
                     @RequestParam(value = "startTime") long startTime, @RequestParam(value = "endTime") long endTime) {
        List<Student> students = studentRepository.filter(code, name, startTime, endTime, role);
        StudentJsonHelper jsonHelper;
        if (students.size() <= length) {
            jsonHelper = new StudentJsonHelper(students);
        }else {
            jsonHelper = new StudentJsonHelper(students.subList(start, (students.size()-start)>length? start+length-1 : students.size()));
        }
        int recordsTotal = students.size();
        int recordsFiltered = recordsTotal;

        jsonHelper.addProperty("draw", draw);
        jsonHelper.addProperty("recordsTotal", recordsTotal);
        jsonHelper.addProperty("recordsFiltered", recordsTotal);
        log.info("stufilter: draw: "+draw+" start: "+ start+" length: "+length+" code: "+code+" name: "+name+" role: "+role+" startTime: "+startTime+" endTime: "+endTime);

        return jsonHelper.toString();
    }


}