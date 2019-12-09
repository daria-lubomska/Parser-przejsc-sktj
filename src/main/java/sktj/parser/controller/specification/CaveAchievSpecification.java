package sktj.parser.controller.specification;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Disjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Joins;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import sktj.parser.entity.CaveAchievements;

@Joins({
    @Join(path = "authors", alias = "a"),
    @Join(path = "country", alias = "c"),
    @Join(path = "notificationAuthor", alias = "na")})
@Disjunction(value = {
    @And({@Spec(path = "entryTimestamp", params = {"entryAfter", "entryBefore"},
        spec = Between.class, config = "yyyy-MM-dd HH:mm:ss"),
        @Spec(path = "a.name", spec = EqualIgnoreCase.class),
        @Spec(path = "a.surname", spec = EqualIgnoreCase.class),
        @Spec(path = "caveOvercomeStyle", spec = In.class)}),
    @And({@Spec(path = "c.name", params = "cname", spec = EqualIgnoreCase.class),
        @Spec(path = "caveName.name", params = "caveName", spec = EqualIgnoreCase.class),
        @Spec(path = "a.name", spec = EqualIgnoreCase.class),
        @Spec(path = "a.surname", spec = EqualIgnoreCase.class),
        @Spec(path = "c.name", spec = EqualIgnoreCase.class ),
        @Spec(path = "na.email", spec = EqualIgnoreCase.class )}) //check
})
public interface CaveAchievSpecification extends Specification<CaveAchievements> {

}
