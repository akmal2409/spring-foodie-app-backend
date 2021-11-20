package com.akmal.springfoodieappbackend.repository;

import com.akmal.springfoodieappbackend.model.Image;
import org.springframework.data.repository.CrudRepository;

/**
 * The Spring {@link CrudRepository} interface
 * that defines persistence methods on {@link com.akmal.springfoodieappbackend.model.Image} entity.
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 6:44 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public interface ImageRepository extends CrudRepository<Image, String> {
}
