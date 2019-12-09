package sktj.parser.controller.specification;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import sktj.parser.entity.User;

@Spec(path = "email", spec = LikeIgnoreCase.class)
public interface ParserUserSpecification extends Specification<User> {

}
