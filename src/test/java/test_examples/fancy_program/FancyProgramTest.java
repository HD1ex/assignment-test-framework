package test_examples.fancy_program;

//Utility test base class

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utility.TestBase;
import utility.TestPair;

class FancyProgramTest extends TestBase {

    @Test
    @DisplayName("CUSTOM TEST NAME")
    void customName() {
        TestPair[] testPairs = {
                new TestPair("msg", "WABBA", TestPair.Type.CHECK_CONTAINS),
                new TestPair("msg", "WABBALUBBADUPDUP!", TestPair.Type.CHECK_EQUALS),
                new TestPair("pi", "3.14159", TestPair.Type.CHECK_STARTS_WITH),
                new TestPair("meaning of life", "42", TestPair.Type.CHECK_EQUALS),
                new TestPair("error", "Error, ", TestPair.Type.CHECK_FOR_ERROR),
                new TestPair("dragon", ""
                        + "O=- .-  -. -=O\n"
                        + "H  /(    )\\  H\n"
                        + "| |  -^^-  | |\n"
                        + "   \\_ `' _/\n"
                        + "|    \\  )    |\n"
                        + "H     )/     H\n"
                        + "O=-  ('    -=O"
                        , TestPair.Type.CHECK_EQUALS)
        };

        testUsingPairs(testPairs, () -> DummyMain.main(null));
    }

    @Test
    @DisplayName("failing Test")
    void failing() {
        TestPair[] testPairs = {
                new TestPair("msg", "WABBA", TestPair.Type.CHECK_CONTAINS),
                new TestPair("msg", "WABBALUBBADUPDUP!", TestPair.Type.CHECK_EQUALS),
                new TestPair("pi", "3.14159", TestPair.Type.CHECK_STARTS_WITH),
                new TestPair("meaning of life", "42", TestPair.Type.CHECK_EQUALS),
                new TestPair("pi", "Error, ", TestPair.Type.CHECK_FOR_ERROR),
                new TestPair("dragon", ""
                        + "O=- .-  -. -=O\n"
                        + "H  /(    )\\  H\n"
                        + "| |  -^^-  | |\n"
                        + "   \\_ `' _/\n"
                        + "|    \\  )    |\n"
                        + "H     )/     H\n"
                        + "O=-  ('    -=O"
                        , TestPair.Type.CHECK_EQUALS)
        };
        testUsingPairs(testPairs, () -> DummyMain.main(null));

    }

    @Test
    @DisplayName("IO file test")
    void ioTest() {
        testWithIOFile("src/test_examples/fancy_program/data/expected_test.io");
        
        /* Equivalent is also: 
            testWithIOFile("src/test_examples/fancy_program/data/expected_test.io"
                , () -> DummyMain.main(null));
         */
    }

    @Test
    @DisplayName("test Creation() (just works if you successfully ran FancyProgramTestCreation")
    void testCreation() {
        testWithIOFile("IOTestExample.io"
                , () -> DummyMain.main(null));
    }

    @Test
    @DisplayName("performance test")
    void perfTest() {
        testWithIOFile("src/test_examples/fancy_program/data/expected_test.io"
                , () -> DummyMain.main(null));
        testWithIOFile("src/test_examples/fancy_program/data/expected_test.io"
                , () -> DummyMain.main(null));
        testWithIOFile("src/test_examples/fancy_program/data/expected_test.io"
                , () -> DummyMain.main(null));
        testWithIOFile("src/test_examples/fancy_program/data/expected_test.io"
                , () -> DummyMain.main(null));
    }
}