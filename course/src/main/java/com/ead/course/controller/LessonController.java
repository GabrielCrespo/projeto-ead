package com.ead.course.controller;

import com.ead.course.dto.LessonRecordDto;
import com.ead.course.model.Lesson;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleService;
import com.ead.course.specification.SpecificationTemplate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LessonController {

    private final ModuleService moduleService;

    private final LessonService lessonService;

    public LessonController(ModuleService moduleService, LessonService lessonService) {
        this.moduleService = moduleService;
        this.lessonService = lessonService;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable UUID moduleId,
                                             @RequestBody @Valid LessonRecordDto lessonRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonService.save(lessonRecordDto, moduleService.findById(moduleId).get()));

    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Page<Lesson>> getAllLessons(@PathVariable UUID moduleId,
                                                      SpecificationTemplate.LessonSpec spec,
                                                      Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllLessonsIntoModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findLessonIntoModule(moduleId, lessonId).get());
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId) {
        lessonService.delete(lessonService.findLessonIntoModule(moduleId, lessonId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted Successfuly");
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable UUID moduleId,
                                               @PathVariable UUID lessonId,
                                               @RequestBody @Valid LessonRecordDto lessonRecordDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonService.update(lessonRecordDto, lessonService.findLessonIntoModule(moduleId, lessonId).get()));
    }


}
