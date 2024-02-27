package com.skiply.student.service;

import com.skiply.student.dto.StudentRequest;
import com.skiply.student.dto.StudentResponse;
import com.skiply.student.model.Student;
import com.skiply.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testCreate() {
        // Given
        StudentRequest studentRequest = new StudentRequest("John", "A", "1234567890", "XYZ School");
        Student student = new Student(1L, "John", "A", "1234567890", "XYZ School");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // When
        StudentResponse response = studentService.create(studentRequest);

        // Then
        assertNotNull(response);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());
        assertEquals(student.getGrade(), response.getGrade());
        assertEquals(student.getContact(), response.getContact());
        assertEquals(student.getSchool(), response.getSchool());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testOne() {
        // Given
        Long studentId = 1L;
        Student student = new Student(studentId, "John", "A", "1234567890", "XYZ School");
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // When
        StudentResponse response = studentService.one(studentId);

        // Then
        assertNotNull(response);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());
        assertEquals(student.getGrade(), response.getGrade());
        assertEquals(student.getContact(), response.getContact());
        assertEquals(student.getSchool(), response.getSchool());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void testAll() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John", "A", "1234567890", "XYZ School"));
        students.add(new Student(2L, "Jane", "B", "9876543210", "ABC School"));
        when(studentRepository.findAll()).thenReturn(students);

        // When
        List<StudentResponse> responses = studentService.all();

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(students.get(0).getId(), responses.get(0).getId());
        assertEquals(students.get(0).getName(), responses.get(0).getName());
        assertEquals(students.get(0).getGrade(), responses.get(0).getGrade());
        assertEquals(students.get(0).getContact(), responses.get(0).getContact());
        assertEquals(students.get(0).getSchool(), responses.get(0).getSchool());
        assertEquals(students.get(1).getId(), responses.get(1).getId());
        assertEquals(students.get(1).getName(), responses.get(1).getName());
        assertEquals(students.get(1).getGrade(), responses.get(1).getGrade());
        assertEquals(students.get(1).getContact(), responses.get(1).getContact());
        assertEquals(students.get(1).getSchool(), responses.get(1).getSchool());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        // Given
        Long studentId = 1L;
        StudentRequest studentRequest = new StudentRequest("John", "A", "1234567890", "XYZ School");
        Student student = new Student(studentId, "John", "A", "1234567890", "XYZ School");
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // When
        StudentResponse response = studentService.update(studentRequest, studentId);

        // Then
        assertNotNull(response);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());
        assertEquals(student.getGrade(), response.getGrade());
        assertEquals(student.getContact(), response.getContact());
        assertEquals(student.getSchool(), response.getSchool());
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDelete() {
        // Given
        Long studentId = 1L;

        // When
        studentService.delete(studentId);

        // Then
        verify(studentRepository, times(1)).deleteById(studentId);
    }
}
