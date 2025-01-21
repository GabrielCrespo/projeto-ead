package com.ead.course.service;

import com.ead.course.dto.LessonRecordDto;
import com.ead.course.model.Lesson;
import com.ead.course.model.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {

    List<Lesson> findAllLessonsIntoModule(UUID moduleId);

    Lesson save(LessonRecordDto lessonRecordDto, Module module);

    Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(Lesson lesson);

    Lesson update(LessonRecordDto lessonRecordDto, Lesson lesson);

    Page<Lesson> findAllLessonsIntoModule(Specification<Lesson> specification, Pageable pageable);
}
