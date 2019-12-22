package com.sktj.controller.specification;

import com.sktj.entity.OtherActivityAchievements;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinFetch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@JoinFetch(paths = {"country", "authors", "notificationAuthor"})
@Or({@Spec(path = "country.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "achievementDescription", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "notificationAuthor.email", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "region", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "anotherAuthors", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "comment", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "category", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "authors.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "authors.surname", params = "chars", spec = LikeIgnoreCase.class)})
public interface OtherAchievSearchSpecification extends Specification<OtherActivityAchievements> {

}
