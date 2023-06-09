package com.timber.search.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Music implements Serializable {
    private int id;
    private int mvid;
    private String name;

}
