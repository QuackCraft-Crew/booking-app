package com.example.accommodationbookingservice.util;

public class MapUtil {
    public String addressToUrl(String streetName,
                               String streetNumber,
                               String city,
                               String country) {
        return "https://www.google.com/maps/search/?api=1&query=%s+%s+%s+%s"
                .formatted(streetName, streetNumber, city, country);
    }
}
