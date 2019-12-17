package com.sktj.mapper;

import com.sktj.entity.Cave;
import com.sktj.entity.User;
import com.sktj.model.Country;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class Mapper {

  public List<com.sktj.model.User> mapUserEntityToModel(List<User> users){
    List<com.sktj.model.User> usersModel = new ArrayList<>();
    users.forEach(i-> usersModel.add(new com.sktj.model.User(i.getName(),i.getSurname(),i.getEmail())));
    return usersModel;
  }

  public List<com.sktj.model.Cave> mapCaveEntityToModel(List<Cave> caves){
    List<com.sktj.model.Cave> cavesModel = new ArrayList<>();
    caves.forEach(i-> cavesModel.add(new com.sktj.model.Cave(i.getName(),i.getRegion())));
    return cavesModel;
  }

  public List<Country> mapCountryEntityToModel(List<com.sktj.entity.Country> caves){
    List<Country> countriesModel = new ArrayList<>();
    caves.forEach(i-> countriesModel.add(new Country(i.getName())));
    return countriesModel;
  }
}
