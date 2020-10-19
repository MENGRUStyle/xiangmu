package com.kai.home.product.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class PmsBaseCatalog2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer catalog1Id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatalog1Id() {
        return catalog1Id;
    }

    public void setCatalog1Id(Integer catalog1Id) {
        this.catalog1Id = catalog1Id;
    }

    @Override
    public String toString() {
        return "PmsBaseCatalog2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catalog1Id=" + catalog1Id +
                '}';
    }
}
