package com.ascent.HotelService.service;

import com.ascent.HotelService.entities.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {

    public Hotel create(Hotel hotel);
    public List<Hotel> getAll();
    public Hotel get (String id);
}
