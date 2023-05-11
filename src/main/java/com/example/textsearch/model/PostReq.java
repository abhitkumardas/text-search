package com.example.textsearch.model;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class PostReq implements Serializable {
    private String author;
    private String title;
    private String content;
}
