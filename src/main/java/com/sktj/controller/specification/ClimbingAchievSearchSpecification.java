package com.sktj.controller.specification;

import com.sktj.entity.ClimbingAchievements;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinFetch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@JoinFetch(paths = {"authors", "country", "notificationAuthor"})
@Or({@Spec(path = "country.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "authors.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "authors.surname", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "notificationAuthor.email", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "wall", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "difficultyGrade", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "region", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "anotherAuthors", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "comment", params = "chars", spec = LikeIgnoreCase.class)})
public interface ClimbingAchievSearchSpecification extends Specification<ClimbingAchievements> {

}
