package com.ead.authuser.specification;

import com.ead.authuser.model.User;
import com.ead.authuser.model.UserCourse;
import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "username", spec = Like.class),
            @Spec(path = "fullname", spec = LikeIgnoreCase.class),
    })
    public interface UserSpec extends Specification<User> {
    }

    public static Specification<User> userCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Join<User, UserCourse> userCourseJoin = root.join("userCourse");
            return criteriaBuilder.equal(userCourseJoin.get("courseId"), courseId);
        };
    }

}
