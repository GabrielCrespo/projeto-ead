package com.ead.course.service;

import com.ead.course.model.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    List<Lesson> findAllLessonsIntoModule(UUID moduleId);

}
