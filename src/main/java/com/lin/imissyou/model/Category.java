package com.lin.imissyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null and online = 1")
public class Category extends BaseEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;
}
