package com.skiply.student.controller;

import com.skiply.student.dto.StudentRequest;
import com.skiply.student.dto.StudentResponse;
import com.skiply.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testGetStudentById() throws Exception {
        // Given
        Long studentId = 1L;
        StudentResponse expectedResponse = new StudentResponse(studentId, "John Doe", "A", "XYZ School", "1234567890");
        when(studentService.one(studentId)).thenReturn(expectedResponse);

        // When-Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/{id}", studentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.school").value("XYZ School"));

        verify(studentService, times(1)).one(studentId);
    }

    @Test
    void testGetAllStudents() throws Exception {
        // Given
        List<StudentResponse> expectedResponses = new ArrayList<>();
        expectedResponses.add(new StudentResponse(1L, "John Doe", "A", "XYZ School", "1234567890"));
        expectedResponses.add(new StudentResponse(2L, "Alice Smith", "B", "ABC School", "9876543210"));
        when(studentService.all()).thenReturn(expectedResponses);

        // When-Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].grade").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].contact").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].school").value("XYZ School"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Alice Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].grade").value("B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].contact").value("9876543210"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].school").value("ABC School"));

        verify(studentService, times(1)).all();
    }


    @Test
    void testCreateStudent() throws Exception {
        // Given
        StudentRequest studentRequest = new StudentRequest("John Doe", "A", "XYZ School", "1234567890");
        StudentResponse expectedResponse = new StudentResponse(1L, "John Doe", "A", "XYZ School", "1234567890");

        when(studentService.create(studentRequest)).thenReturn(expectedResponse);

        // When-Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"grade\":\"A\",\"contact\":\"1234567890\",\"school\":\"XYZ School\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.school").value("XYZ School"));

        verify(studentService, times(1)).create(any(StudentRequest.class));
    }

    @Test
    void testUpdateStudent() throws Exception {
        // Given
        Long studentId = 1L;
        StudentResponse expectedResponse = new StudentResponse(studentId, "John Doe", "A", "XYZ School", "1234567890");
        when(studentService.update(any(StudentRequest.class), eq(studentId))).thenReturn(expectedResponse);

        // When-Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"grade\":\"A\",\"contact\":\"1234567890\",\"school\":\"XYZ School\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.school").value("XYZ School"));

        verify(studentService, times(1)).update(any(StudentRequest.class), eq(studentId));
    }

    @Test
    void testDeleteStudent() throws Exception {
        // Given
        Long studentId = 1L;

        // When-Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/{id}", studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(studentService, times(1)).delete(studentId);
    }
}
