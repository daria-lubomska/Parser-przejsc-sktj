package sktj.parser.controller.specification;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import sktj.parser.entity.Cave;

@And({
    @Spec(path = "name", spec = LikeIgnoreCase.class),
    @Spec(path = "region", spec = LikeIgnoreCase.class)
})
public interface ParserCaveSpecification extends Specification<Cave> {

}
