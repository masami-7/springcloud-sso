package com.yl.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 3570548663999909287L;
    private Long id;
    private String username;
    private String password;
    private String status;

}