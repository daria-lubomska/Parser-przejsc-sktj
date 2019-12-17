package com.sktj.controller.specification;

import com.sktj.entity.Cave;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "name", spec = LikeIgnoreCase.class)
public interface CaveSpecification extends Specification<Cave> {

}
