package com.avaliacaopratica.api.controller;

import com.avaliacaopratica.api.dto.course.CoursePostResponseDTO;
import com.avaliacaopratica.api.dto.course.CourseRequestDTO;
import com.avaliacaopratica.api.dto.course.CourseResponseDTO;
import com.avaliacaopratica.api.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/curso")
    public ResponseEntity<CoursePostResponseDTO> create(
            @Valid @RequestBody CourseRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.createCourse(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CourseRequestDTO request
    ) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> findById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @GetMapping("/inscritos/{id}")
    public ResponseEntity<CourseResponseDTO> findPersonsByIdCourse(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @GetMapping("/inscritos-finalizados/{id}")
    public ResponseEntity<CourseResponseDTO> findFinishedPeopleByIdCourse(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @GetMapping("/curso")
    public ResponseEntity<List<CourseResponseDTO>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
