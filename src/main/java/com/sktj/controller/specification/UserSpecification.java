package com.sktj.controller.specification;

import com.sktj.entity.User;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Or({
    @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
    @Spec(path = "surname", params = "name", spec = LikeIgnoreCase.class)})
public interface UserSpecification extends Specification<User> {

}
