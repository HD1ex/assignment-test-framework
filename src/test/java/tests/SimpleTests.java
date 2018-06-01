package tests;

import utility.TestBase;

import org.junit.jupiter.api.Test;

/**
 * This can be used as template to create tests
 *
 * @author Alexander Sommer
 * @since 30.05.2018
 */
class SimpleTests extends TestBase {
    private final String IO_FILE_DIR = "src/resources/SimpleTests/";

    @Test
    void firstSimpleTest() {
        testWithIOFile(IO_FILE_DIR + "firstSimpleTest.io");
    }
}
