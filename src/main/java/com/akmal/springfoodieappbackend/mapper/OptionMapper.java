package com.akmal.springfoodieappbackend.mapper;

import com.akmal.springfoodieappbackend.dto.OptionDto;
import com.akmal.springfoodieappbackend.model.Option;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Interface represents a contract based on which Mapstruct library generates the mapper class at
 * runtime. Converts Option Object to DTO and vice versa.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 23/10/2021 - 12:59 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OptionMapper {

  OptionDto toDto(Option option);

  Option from(OptionDto optionDto);
}
