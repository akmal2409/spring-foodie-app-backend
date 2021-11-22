package com.akmal.springfoodieappbackend.dto.tomtom;

import com.akmal.springfoodieappbackend.dto.LocationDto;

/**
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 6:59 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record PlaceSearchResult(String type,
                                String id,
                                PlaceSearchAddress address,
                                LocationDto position) {
}
