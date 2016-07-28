import demo.Application;
import demo.entity.Student;
import demo.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by wang on 16/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class StudentRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger("StudentRepositoryTest");

    @Autowired
    private StudentRepository repository;

    @Test
    public void testRepo() {
        //List<Student> students = repository.findAll();
        //for (Student s : students) {
        //    log.info(s.toString());
        //}

        //delete
        //repository.delete(students.get(0));
        //
        //students = repository.findAll();
        //for (Student s : students) {
        //    log.info(s.toString());
        //}

        Student studentId3 = repository.findById(3);
        log.info(studentId3.toString());
        //update
        //studentId3.setName("test username");
        //repository.update(studentId3);
        //
        //Student studentId3_ = repository.findById(3);
        //log.info(studentId3_.toString());

        //List<Student> result = repository.filter(null, "st", null, 34343L, null);
        //for (Student s : result) {
        //    System.out.println(s.toString());
        //}

        //for (int i=0; i<100; i++) {
        //    Student student = new Student();
        //    student.setStatus(1);
        //    student.setName("student name " + i);
        //    student.setStudentRole(new Random().nextInt(10)%3 + 1);
        //    student.setComment(UUID.randomUUID().toString());
        //    repository.create(student);
        //}
        log.info(repository.countAll()+"");
    }


}
