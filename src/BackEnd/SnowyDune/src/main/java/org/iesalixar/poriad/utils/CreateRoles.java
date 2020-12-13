package org.iesalixar.poriad.utils;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.iesalixar.poriad.entity.Cart;
import org.iesalixar.poriad.security.entity.Role;
import org.iesalixar.poriad.security.entity.UserSnowy;
import org.iesalixar.poriad.security.enums.RoleName;
import org.iesalixar.poriad.security.service.RoleService;
import org.iesalixar.poriad.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/*
@Component
public class CreateRoles implements CommandLineRunner{

	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		
		Role roleUser = new Role(RoleName.ROLE_USER);
		Role roleEnterprise = new Role(RoleName.ROLE_ENTERPRISE);
		Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
		
		roleService.save(roleAdmin);
		roleService.save(roleUser);
		roleService.save(roleEnterprise);
		
		String password = "admin1";
		String passwordTwo = "poriad";
		String passwordThree = "bussinessman";
		String passwordFour = "theking";

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		UserSnowy admin = new UserSnowy("The Boss", "The Real Boss", "admin",passwordEncoder.encode(password), "the.best.admin@gmail.com", "C/ El rey del mambo", true,"659009071");
		UserSnowy poriad = new UserSnowy("Pablo", "Oria de Rueda Pérez", "poriad",passwordEncoder.encode(passwordTwo), "pablo.oria1991@gmail.com", "C/ La Niña 97", true,"659009071");
		UserSnowy antrod = new UserSnowy("Antonio", "Rodríguez", "antrod",passwordEncoder.encode(passwordThree), "bussinessMan@gmail.com", "C/ El empresaurio", true,"659009071");
		UserSnowy theKing = new UserSnowy("Juan Carlos", "The King", "juancar",passwordEncoder.encode(passwordFour), "tributo.lo.que.puedo@gmail.com", "C/ Arabia Saudi Palacio 3", true,"000000001");
		
		
		Cart cartOne = new Cart();
		Cart cartTwo = new Cart();
		Cart cartThree = new Cart();
		Cart cartFour = new Cart();
		
		Set<Role> rolesUser = new HashSet<Role>();
		Set<Role> rolesEnterprise = new HashSet<Role>();
		Set<Role> rolesAdmin = new HashSet<Role>();
		
		admin.setCart(cartOne);
		poriad.setCart(cartTwo);
		antrod.setCart(cartThree);
		theKing.setCart(cartFour);
		
		rolesUser.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
		
		poriad.setRoles(rolesUser);
		
		userService.save(poriad);
		
		rolesEnterprise.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
		rolesEnterprise.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
		
		antrod.setRoles(rolesEnterprise);
		theKing.setRoles(rolesEnterprise);
		
		userService.save(antrod);
		userService.save(theKing);
		
		rolesAdmin.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
		rolesAdmin.add(roleService.getByRoleName(RoleName.ROLE_ENTERPRISE).get());
		rolesAdmin.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
		
		admin.setRoles(rolesAdmin);
		
		userService.save(admin);
		}
	
}
*/

