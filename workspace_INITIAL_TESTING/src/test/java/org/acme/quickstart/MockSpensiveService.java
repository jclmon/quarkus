package org.acme.quickstart;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.test.Mock;

/**
 * Al anotarlo como Mock injecta este en pruebas preferentemente al real
 */
@ApplicationScoped
@Mock
public class MockSpensiveService implements ExpensiveService {

    @Override
    public int calculate() {
        return 100;
    }

}