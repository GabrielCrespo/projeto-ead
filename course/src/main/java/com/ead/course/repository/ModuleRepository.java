package com.ead.course.repository;

import com.ead.course.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {

//    @EntityGraph(attributePaths = {"course"})
//    Module findByTitle(String title);

    @Query(value = "select * from tb_module where course_course_id = :courseId", nativeQuery = true)
    List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "select * from tb_module where course_course_id = :courseId and module_id = :moduleId",
            nativeQuery = true)
    Optional<Module> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}
