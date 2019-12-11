package sktj.parser.controller.specification;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinFetch;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import sktj.parser.entity.CaveAchievements;

@JoinFetch(paths = {"country", "caveName", "authors", "notificationAuthor"})
@Or({@Spec(path = "country.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "caveName.name", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "notificationAuthor.email", params = "chars", spec = LikeIgnoreCase.class),
    @Spec(path = "caveOvercomeStyle", params = "style", spec = LikeIgnoreCase.class)})
public interface CaveAchievSearchSpecification extends Specification<CaveAchievements> {

}
