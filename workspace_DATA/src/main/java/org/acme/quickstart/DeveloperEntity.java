package org.acme.quickstart;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Panache ya tiene un id y no necesita hacer getters y setters
 */
@Entity
public class DeveloperEntity extends PanacheEntity {

    @Column(unique = true)
    public String name;

    @Column(unique = true)
    public Integer age;

}