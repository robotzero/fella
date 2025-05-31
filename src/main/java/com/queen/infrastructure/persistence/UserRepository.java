package com.queen.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
	Optional<User> findByUserName(String name);
}
