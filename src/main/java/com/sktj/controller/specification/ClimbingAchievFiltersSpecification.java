package com.sktj.controller.specification;

import com.sktj.entity.ClimbingAchievements;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinFetch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@JoinFetch(paths = {"authors", "country"})
@Or({
    @Spec(path = "date", params = {"start", "end"},
        spec = Between.class, config = "yyyy-MM-dd"),
    @Spec(path = "wall", spec = LikeIgnoreCase.class),
    @Spec(path = "authors.name", params = "name", spec = EqualIgnoreCase.class),
    @Spec(path = "authors.surname", params = "surname", spec = EqualIgnoreCase.class),
    @Spec(path = "country.name", params = "country", spec = EqualIgnoreCase.class)})
public interface ClimbingAchievFiltersSpecification extends Specification<ClimbingAchievements> {

}
