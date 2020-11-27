package com.glovoapp.backender.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
@Builder
public class Courier {

    private String id;
    private String name;
    private Boolean box;
    private Vehicle vehicle;
    private Location location;
}
