package com.ead.course.controller;

import com.ead.course.dto.ModuleRecordDto;
import com.ead.course.model.Module;
import com.ead.course.service.CourseService;
import com.ead.course.service.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ModuleController {

    private final ModuleService moduleService;

    private final CourseService courseService;

    public ModuleController(ModuleService moduleService, CourseService courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable UUID courseId,
                                             @RequestBody @Valid ModuleRecordDto moduleRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(moduleService.save(moduleRecordDto, courseService.findById(courseId).get()));

    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<Module>> getAllModules(@PathVariable UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.findAllModulesIntoCourse(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.findModuleIntoCourse(courseId, moduleId));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        moduleService.delete(moduleService.findModuleIntoCourse(courseId, moduleId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable UUID courseId,
                                               @PathVariable UUID moduleId,
                                               @RequestBody ModuleRecordDto moduleRecordDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.update(moduleRecordDto, moduleService.findModuleIntoCourse(courseId, moduleId).get()));
    }



}
