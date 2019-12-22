package com.sktj.controller.specification;

import com.sktj.entity.CaveAchievements;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinFetch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@JoinFetch(paths = {"country", "caveName", "authors", "notificationAuthor"})
@Or({@Spec(path = "country.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "caveName.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "notificationAuthor.email", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "caveOvercomeStyle", params = "style", spec = LikeIgnoreCase.class),
    @Spec(path = "anotherAuthors", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "comment", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "reachedParts", params = "chars", spec = LikeIgnoreCase.class)})
public interface CaveAchievSearchSpecification extends Specification<CaveAchievements> {

}
