package com.EventHorizon.EventHorizon.EventCreation;

public class LocationDTO
{
    public String country;
    public String city;
    public String address;

    public Location getLocation()
    {
        Location location = new Location(country, city, address);
        return location;
    }
}
