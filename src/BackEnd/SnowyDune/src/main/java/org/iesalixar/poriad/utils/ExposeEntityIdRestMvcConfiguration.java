package org.iesalixar.poriad.utils;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.xml.stream.events.Comment;

import org.iesalixar.poriad.security.entity.UserSnowy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class ExposeEntityIdRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

	@Autowired
    private EntityManager entityManager;
	
  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
	  config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(e -> e.getJavaType()).collect(Collectors.toList()).toArray(new Class[0]));
  }
  }
