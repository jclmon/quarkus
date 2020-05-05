package org.acme.quickstart;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class DeveloperRepository implements PanacheRepository<Developer> {

    public Developer create(Developer developer) {
        persist(developer);
        return developer;
    }

    public Developer findByName(String name) {
        return find("name", name).firstResult();
    }

}