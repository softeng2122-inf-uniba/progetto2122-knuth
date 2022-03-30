package it.uniba.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Main test class of the application.
 */
public class AppTest {
    /**
     * Test the getGreeting method of the App class.
     */
    @Test
    public void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(
                "app should have a greeting", classUnderTest.getGreeting());
    }
}
