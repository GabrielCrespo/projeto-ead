package com.ead.course.service.impl;

import com.ead.course.exception.NotFoundExcepetion;
import com.ead.course.dto.LessonRecordDto;
import com.ead.course.model.Lesson;
import com.ead.course.model.Module;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

    @Override
    public Lesson save(LessonRecordDto lessonRecordDto, Module module) {
        Lesson lesson = new Lesson();
        BeanUtils.copyProperties(lessonRecordDto, lesson);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(module);

        return lessonRepository.save(lesson);
    }

    @Override
    public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        Optional<Lesson> lessonOptional = lessonRepository.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) throw new NotFoundExcepetion("Error: Lesson not found");
        return lessonOptional;
    }

    @Override
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    @Override
    public Lesson update(LessonRecordDto lessonRecordDto, Lesson lesson) {
        BeanUtils.copyProperties(lessonRecordDto, lesson);
        return lessonRepository.save(lesson);
    }
}
