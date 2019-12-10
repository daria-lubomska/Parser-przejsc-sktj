package sktj.parser.controller.specification;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import sktj.parser.entity.CaveAchievements;

// todo authors not initialized

@Or({
    @Spec(path = "entryTimestamp", params = {"entryAfter", "entryBefore"},
        spec = Between.class, config = "yyyy-MM-dd HH:mm:ss"),
//    @Spec(path = "a.name", params = "name", spec = EqualIgnoreCase.class),
//    @Spec(path = "a.surname", params = "surname", spec = EqualIgnoreCase.class)
})
public interface CaveAchievFiltersSpecification extends Specification<CaveAchievements> {

}
