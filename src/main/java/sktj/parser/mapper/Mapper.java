package sktj.parser.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import sktj.parser.model.Cave;
import sktj.parser.model.User;


@Service
public class Mapper {

  public List<User> mapUserEntityToModel(List<sktj.parser.entity.User> users){
    List<User> usersModel = new ArrayList<>();
    users.forEach(i->
      usersModel.add(new User(i.getName(),i.getSurname(),i.getEmail())));
    return usersModel;
  }

  public List<Cave> mapCaveEntityToModel(List<sktj.parser.entity.Cave> caves){
    List<Cave> cavesModel = new ArrayList<>();
    caves.forEach(i->
        cavesModel.add(new Cave(i.getName(),i.getRegion())));
    return cavesModel;
  }
}
