package com.empresa.pinturas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "envasados")
public class Envasado {

    @Id
    @Column(name = "id")
    private Integer id;   // 👈 INT, NO CONFUNDIR

    public Envasado() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
