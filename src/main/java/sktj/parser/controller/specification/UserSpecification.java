package sktj.parser.controller.specification;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import sktj.parser.entity.User;

@Or({@Spec(path = "name", spec = LikeIgnoreCase.class),
    @Spec(path = "surname", spec = LikeIgnoreCase.class)})
public interface UserSpecification extends Specification<User> {

}
