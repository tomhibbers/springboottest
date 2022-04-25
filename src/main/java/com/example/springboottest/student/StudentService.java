package com.example.springboottest.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public List<Student> getStudents(){
        return this.studentRepository.findAll();
    }
    public void addStudent(Student student){
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        this.studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public void updateStudent(Student student) {
        Student foundStudent = studentRepository.findById(student.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + student.getId() + " does not exist"
                ));
        if(student.getName() != null && student.getName().length()>0 && !Objects.equals(student.getName(), foundStudent.getName())){
            foundStudent.setName(student.getName());
        }
        if(student.getEmail() !=null && student.getEmail().length()>0 && !Objects.equals(student.getEmail(),foundStudent.getEmail())){
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
            if(studentByEmail.isPresent()){
                throw new IllegalStateException("email taken");
            }
            foundStudent.setEmail(student.getEmail());
        }
    }
}
