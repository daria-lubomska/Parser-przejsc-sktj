package com.sktj.controller.specification;

import com.sktj.entity.User;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "email", spec = EqualIgnoreCase.class)
public interface UserSpecification extends Specification<User> {

}
