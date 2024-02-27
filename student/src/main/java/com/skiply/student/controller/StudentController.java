package com.skiply.student.controller;

import com.skiply.student.dto.StudentRequest;
import com.skiply.student.dto.StudentResponse;
import com.skiply.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")

public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse one(@PathVariable Long id) {
        return studentService.one(id);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> all() {
        return studentService.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse create(@RequestBody StudentRequest student) {
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse update(@RequestBody StudentRequest student, @PathVariable Long id) {
        return studentService.update(student, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}
