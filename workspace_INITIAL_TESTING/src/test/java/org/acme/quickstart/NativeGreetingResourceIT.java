package org.acme.quickstart;

import io.quarkus.test.junit.SubstrateTest;

/**
 * mvn verify
 * mvn integration-test
 */
/**
 * Compila a nativo y después lo ejecutas utiliza el perfil del pom integration-test utilizando verify
 * 
 * se ejecuta desde ./mvnw verify -Pnative
 * 
 */
@SubstrateTest
public class NativeGreetingResourceIT extends GreetingResourceTest {
    // Execute the same tests but in native mode.
}