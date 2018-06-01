package test_examples.worksheet_5_ws17_18.task_a;

import org.junit.jupiter.api.Test;
import utility.TestBase;
import utility.TestPair;

import static utility.TestPair.Type.CHECK_FOR_ERROR;

public class SimpleTests extends TestBase {
    @Test
    void firstExample() {
        TestPair[] testPairs = new TestPair[]{
                new TestPair("place", CHECK_FOR_ERROR),
                new TestPair("place a;a", CHECK_FOR_ERROR),
                new TestPair("place aa", CHECK_FOR_ERROR),
                new TestPair("place 1,1", CHECK_FOR_ERROR),
                new TestPair("place -1;-1", CHECK_FOR_ERROR),
                new TestPair("place 10;10", "OK"),
                new TestPair("state 10;10", "1"),
                new TestPair("place 10;10", CHECK_FOR_ERROR),
                new TestPair("place 11;10", "OK"),
                new TestPair("place 10;10", CHECK_FOR_ERROR),
                new TestPair("place 11;11", "OK"),
                new TestPair("place 12;10", "OK"),
                new TestPair("place 12;12", "OK"),
                new TestPair("place 13;10", "OK"),
                new TestPair("place 13;13", "OK"),
                new TestPair("place 14;10", "OK"),
                new TestPair("place 14;14", "P1 wins"),
                new TestPair("print", ""
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - - - - - -\n"
                        + "- - - - - - - - - - 1 - - - -\n"
                        + "- - - - - - - - - - 2 1 - - -\n"
                        + "- - - - - - - - - - 2 - 1 - -\n"
                        + "- - - - - - - - - - 2 - - 1 -\n"
                        + "- - - - - - - - - - 2 - - - 1"),

        };

        testUsingPairs(testPairs, () -> DummyMain.main(null));
    }

    @Test
    void joshuasTest() {
        TestPair[] testPairs = new TestPair[]{
                new TestPair("place 0;0", "OK", TestPair.Type.CHECK_EQUALS),
        };

        testUsingPairs(testPairs, () -> DummyMain.main(null));
    }

    @Test
    void sampleFromAssignment() {
        testWithIOFile("src/test_examples/worksheet_5_ws17_18/task_a/data/sample_from_assignment.io", () -> DummyMain.main(null));
    }
}
