package com.ascent.HotelService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hotel_Details")
public class Hotel {

    @Id
    private String id;
    private String name;
    private String location;
    private String about;
}
