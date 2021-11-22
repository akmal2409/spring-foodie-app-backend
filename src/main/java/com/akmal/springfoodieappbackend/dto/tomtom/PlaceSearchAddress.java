package com.akmal.springfoodieappbackend.dto.tomtom;

/**
 * The class represents the address object embedded into Place Search Result,
 * returned by TomTom API.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 21/11/2021 - 7:01 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record PlaceSearchAddress(String streetName,
                                 String municipality,
                                 String countrySubdivision,
                                 String postalCode,
                                 String extendedPostalCode,
                                 String countryCode,
                                 String country,
                                 String freeformAddress,
                                 String localName) {
}
