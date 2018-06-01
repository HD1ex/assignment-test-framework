package utility;

import edu.kit.informatik.Terminal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

//JUnit 5.0
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class is the base for a test and implements utility functions for testing.
 * It interacts with the {@link edu.kit.informatik.Terminal} class.
 * A test should inherit from this class.
 * JUnit 5.0 is required
 *
 * @author Alexander Sommer
 * @author Jon Amos Fehling
 * @author Benjamin Takacs
 * @since 23.01.2018
 */
public abstract class TestBase {
    public static final String ERROR_PREFIX = "Error, ";
    public static final String IO_FILE_COMMAND_LINE_ARGS_PREFIX = Terminal.IO_FILE_COMMAND_LINE_ARGS_PREFIX;
    protected Logger log;
    protected boolean testPassed = false;
    protected static int numFailedTests = 0;
    protected static int numSucceededTests = 0;

    private List<LogRecord> logData = new ArrayList<>(100);
    private String testName;
    private boolean alwaysShowLog;
    private boolean showAllProgramOutput = false;
    private boolean showTestProgress;

    /**
     * Initializes testing
     */
    @BeforeAll
    public static void initAll() {
        Terminal.isTest = true;
    }

    /**
     * Initializes the current test.
     * This is called automatically.
     *
     * @param testInfo is the {@link TestInfo} for this test
     */
    @BeforeEach
    protected void initTest(TestInfo testInfo) {
        alwaysShowLog = false;
        testPassed = false;
        showTestProgress = false;
        testName = testInfo.getDisplayName();
        log = Logger.getLogger(testInfo.getClass().getName());
        log.setUseParentHandlers(false);
        log.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                logData.add(record);
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {
                logData.clear();
            }
        });
        log.info("Running test '" + getTestName(testInfo) + "' ...\n");
    }

    /**
     * Gets the name of the current test based upon a testInfo
     *
     * @param testInfo is the {@link TestInfo} of this test
     * @return the name of the current test based upon a testInfo
     */
    protected String getTestName(TestInfo testInfo) {
        return testInfo.getDisplayName() + " @ " + testInfo.getTestClass().toString().substring(15).replace("]", "");
    }

    /**
     * Clears all data from previous testExamples
     * This is automatically called after every test
     */
    @AfterEach
    protected void cleanUp() {
        if (testPassed) {
            numSucceededTests++;
        } else {
            numFailedTests++;
        }

        if (showAllProgramOutput && !testPassed && !Terminal.OUT_TEST.isEmpty()) {
            System.out.println("The output after that was:");

            while (!Terminal.OUT_TEST.isEmpty())
                System.out.println(Terminal.OUT_TEST.pollFirst());
        }

        clearData();
    }

    /**
     * Clears all test data
     */
    protected void clearData() {
        Terminal.IN_TEST.clear();
        Terminal.OUT_TEST.clear();

        if (alwaysShowLog)
            printLog();
        logData.clear();
    }

    /**
     * Prints the current log
     */
    private void printLog() {
        for (LogRecord logRecord : logData) {
            System.out.print(logRecord.getMessage());
        }
        System.out.println();
        logData.clear();
    }

    /**
     * Lets the test fail and prints a specified message
     *
     * @param message is the message to print
     */
    protected void failAndLog(String message) {
        printLog();
        fail(message);
    }

    /**
     * Sets alwaysShowLog to true. Notice: Before each test alwaysShowLog is set to default value false.
     */
    protected void setAlwaysShowLog() {
        alwaysShowLog = true;
    }

    /**
     * Gets the next output from the output-queue and removes it
     *
     * @return a string containing the output
     */
    protected String popProgramOutput() {
        StringBuilder res = Terminal.OUT_TEST.pollFirst();
        if (res == null)
            return null;
        else
            return res.toString();
    }

    /**
     * Pushes a line to the terminal-input of the program
     *
     * @param line is a string containing the input
     */
    protected void pushProgramInput(String line) {
        Terminal.IN_TEST.add(line);
    }

    /**
     * Tests a specified program using a given {@link TestPair}-array.
     * 'quit' is automatically added as input to the end
     * The {@link Runnable} interface is abused as a sort of function pointer.
     * <p>
     * This function is intended to be used for testing the command-line-interface of a program
     * by creating input-output-pairs that build upon each other
     *
     * @param testPairs    is an array of testPairs
     * @param testedMethod is a reference to a tested program
     */
    protected void testUsingPairs(TestPair[] testPairs, Runnable testedMethod) {
        log.info("Testing using " + testPairs.length + " pairs...\n");

        for (TestPair testPair : testPairs) {
            pushProgramInput(testPair.getInput());
        }

        pushProgramInput("quit");
        Terminal.setupShowProgress(showTestProgress, Terminal.IN_TEST.size());
        testedMethod.run();

        for (TestPair testPair : testPairs) {
            log.info("Testing " + testPair);

            if (Terminal.OUT_TEST.isEmpty()) {
                if (!Terminal.IN_TEST.isEmpty()) {
                    failAndLog("The program quitted too early\n"
                            + "Please check that your program resets all static values!");
                }
                failAndLog("Found no more output. There is some serious issue!");
            }

            String output = popProgramOutput();

            switch (testPair.getType()) {
                case CHECK_EQUALS:
                    if (!testPair.getOutput().equals(output)) {
                        log.info(" Failed!\n");
                        printLog();
                        assertEquals(testPair.getOutput(), output);
                    }
                    break;
                case CHECK_STARTS_WITH:
                    if (output == null || !output.startsWith(testPair.getOutput())) {
                        log.info(" Failed!\n");
                        failAndLog(testPair.getFailMessage(output));
                    }
                    break;
                case CHECK_CONTAINS:
                    if (output == null || !output.contains(testPair.getOutput())) {
                        log.info(" Failed!\n");
                        failAndLog(testPair.getFailMessage(output));
                    }
                    break;
                case CHECK_FOR_ERROR:
                    if (output == null || !output.startsWith(ERROR_PREFIX)) {
                        log.info(" Failed!\n");
                        failAndLog(testPair.getFailMessage(output));
                    }
                    break;
                case CHECK_FOR_NO_OUTPUT:
                    if (output != null) {
                        log.info(" Failed!\n");
                        printLog();
                        if (output.equals("")) {
                            output = "(empty string)";
                        }
                        fail("Output should have been empty but was: " + output);
                    }
                    break;
                case NO_CHECK:
                    break;
                default:
                    fail("Unimplemented error check");
            }
            log.info(" Passed!\n");
        }
        log.info("Test successfully completed.\n");
        testPassed = true;
    }

    /**
     * Tests inputs and outputs of a program specified by an io-file.
     * For the syntax at the examples or in the wiki (It's very intuitive)
     *
     * @param path         is the path of the io-file
     * @param testedMethod is the main method of the tested program
     */
    protected void testWithIOFile(String path, Runnable testedMethod) {
        TestPair[] pairs = loadTestPairsFromIOFile(path);
        testUsingPairs(pairs, testedMethod);
    }

    /**
     * Gets the commandLineArgs from a specified io file
     *
     * @param path is the path of the io-file
     * @return the commandLineArgs from a specified io file
     */
    protected String[] getCommandLineArgsFromIOFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(IO_FILE_COMMAND_LINE_ARGS_PREFIX)) {
                    line = line.substring(2);
                    return line.split(" ");
                }
            }
        } catch (IOException ex) {
            fail("Test '" + testName + "' failed. " + ex.getMessage());
        }

        return null;
    }

    /**
     * Loads TestPairs from inputs and outputs specified by an io-file.
     * For the syntax look at the examples or in the wiki (It's very intuitive)
     *
     * @param path is the path of the io-file
     * @return the test pairs
     */
    protected TestPair[] loadTestPairsFromIOFile(String path) {
        List<TestPair> testPairs = new ArrayList<>();

        if (!Files.exists(Paths.get(path)))
            fail("Test not working. Missing required File: " + path);

        boolean isFileStart = true;
        StringBuilder builder = new StringBuilder();
        final String inputLinePrefix = "> ";
        String input = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(inputLinePrefix)) {
                    if (isFileStart) {
                        isFileStart = false;
                    } else {
                        testPairs.add(getIOTestPair(builder, input));
                    }
                    input = line.replace(inputLinePrefix, "");
                    builder.delete(0, builder.length());
                } else {
                    builder.append(line);
                    builder.append("\n");
                }
            }

        } catch (IOException e) {
            fail("Test '" + testName + "' failed. " + e.getMessage());
        }

        if (builder.length() == 0 && input.length() != 0) {
            testPairs.add(new TestPair(input));
        } else {
            testPairs.add(getIOTestPair(builder, input));
        }

        return testPairs.toArray(new TestPair[testPairs.size()]);
    }

    /**
     * In here some syntax of the io-file is defined
     */
    private TestPair getIOTestPair(StringBuilder builder, String input) {
        final String startsWithPostfix = "...";
        String output = "";
        if (builder.length() == 0) {
            return new TestPair(input);
        } else if (builder.length() != 0)
            output = builder.deleteCharAt(builder.length() - 1).toString();
        if (output.startsWith(ERROR_PREFIX)) {
            return new TestPair(input, output, TestPair.Type.CHECK_FOR_ERROR);
        }
        if (output.contains(startsWithPostfix)) {
            String[] split = output.split(startsWithPostfix);
            assert split.length >= 1 : "Check your .io file for the test. There is a line only containing ...";
            String start = split[0];
            return new TestPair(input, start, TestPair.Type.CHECK_STARTS_WITH);
        }

        return new TestPair(input, output);
    }

    /**
     * If enabled, this shows the output of a program, after a test fails
     */
    protected void enableShowAllProgramOutput() {
        this.showAllProgramOutput = true;
    }

    /**
     * Shows a progress-bar for this test
     */
    protected void enableShowTestProgress() {
        showTestProgress = true;
    }

    //DummyMain wrapper
    protected void testUsingPairs(TestPair[] testPairs) {
        testUsingPairs(testPairs, () -> DummyMain.main(null));
    }

    protected void testWithIOFile(String path) {
        testWithIOFile(path, () -> DummyMain.main(null));
    }
}
