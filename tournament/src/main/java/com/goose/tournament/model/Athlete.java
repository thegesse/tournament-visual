package com.goose.tournament.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Athlete {
    private int id;
    private String name;
    private int athleticism;
}
