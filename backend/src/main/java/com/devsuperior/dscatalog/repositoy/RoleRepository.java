package com.devsuperior.dscatalog.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
