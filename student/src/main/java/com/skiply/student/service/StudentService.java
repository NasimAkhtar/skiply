package com.skiply.student.service;

import com.skiply.student.dto.StudentRequest;
import com.skiply.student.dto.StudentResponse;
import com.skiply.student.exception.StudentNotFoundException;
import com.skiply.student.model.Student;
import com.skiply.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentResponse create(StudentRequest studentRequest) {
        Student saved = studentRepository.save(buildStudentModel(studentRequest));

        log.info("Student: {} is saved", saved.getId());

        return mapToStudentResponse(saved);
    }

    public StudentResponse one(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if(!student.isPresent())
            throw new StudentNotFoundException(id);
        return mapToStudentResponse(student.get());
    }

    public List<StudentResponse> all() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> mapToStudentResponse(student)).toList();
    }

    public StudentResponse update(StudentRequest studentRequest, Long id) {
        Optional<Student> saved = studentRepository.findById(id);

        if (!saved.isPresent()) throw new StudentNotFoundException(id);

        Student student = buildStudentModel(studentRequest);

        student.setId(id);

        Student updated = studentRepository.save(student);

        log.info("Student: {} is updated", updated.getId());

        return mapToStudentResponse(updated);
    }
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .grade(student.getGrade())
                .contact(student.getContact())
                .school(student.getSchool())
                .build();
    }

    private static Student buildStudentModel(StudentRequest studentRequest) {
        return Student.builder()
                .name(studentRequest.getName())
                .grade(studentRequest.getGrade())
                .contact(studentRequest.getContact())
                .school(studentRequest.getSchool())
                .build();
    }

}
