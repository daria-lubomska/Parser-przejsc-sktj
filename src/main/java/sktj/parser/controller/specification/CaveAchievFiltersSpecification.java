package sktj.parser.controller.specification;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinFetch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import sktj.parser.entity.CaveAchievements;

@JoinFetch(paths = { "country", "caveName", "authors", "notificationAuthor" })
@Or({
    @Spec(path = "entryTimestamp", params = {"entryAfter", "entryBefore"},
        spec = Between.class, config = "yyyy-MM-dd HH:mm:ss"),
    @Spec(path = "authors.name", params = "name", spec = EqualIgnoreCase.class),
    @Spec(path = "authors.surname", params = "surname", spec = EqualIgnoreCase.class),
    @Spec(path =  "caveOvercomeStyle", params = "style", spec = EqualIgnoreCase.class)
})
public interface CaveAchievFiltersSpecification extends Specification<CaveAchievements> {

}
