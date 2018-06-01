package utility;

public class TestPair {
    public String getFailMessage(String actualOutput) {
        if (actualOutput == null) {
            return "Fail with " + this + "\nThe program provided no output for that command";
        }

        return "Fail with " + this + "\nOutput was:"
                + (actualOutput.contains("\n") ? "\n" : " ")
                + actualOutput + (actualOutput.isEmpty() ? "(empty)" : "");
    }

    public enum Type {
        CHECK_EQUALS,
        CHECK_STARTS_WITH,
        CHECK_FOR_ERROR,
        CHECK_CONTAINS,
        CHECK_FOR_NO_OUTPUT,
        NO_CHECK,
    }

    private String input;
    private String output;
    private Type type;

    public TestPair(String input, String output, Type type) {
        assert input != null : "input is null";
        assert output != null : "output is null";
        this.input = input;
        this.output = output;
        this.type = type;
    }

    public TestPair(String input, String output) {
        assert input != null : "input is null";
        assert output != null : "output is null";
        this.input = input;
        this.output = output;
        this.type = Type.CHECK_EQUALS;
    }

    public TestPair(String input, Type type) {
        assert input != null : "input is null";
        this.input = input;
        this.type = type;
        this.output = "";
    }

    /**
     * Constructs a new utility.TestPair, that tests for an empty output for a given input
     *
     * @param input is the input to check
     */
    public TestPair(String input) {
        this.input = input;
        this.output = null;
        this.type = Type.CHECK_FOR_NO_OUTPUT;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == Type.CHECK_FOR_ERROR && output.equals(TestBase.ERROR_PREFIX + "...")
                || type == Type.CHECK_FOR_NO_OUTPUT) {
            return "utility.TestPair{'" + input + " @" + type + "}";
        }

        String outStr = "'" + output.split("\n")[0] + "\'";

        return "utility.TestPair{"
                + "'" + input + "\'->" + outStr + (output.contains("\n") ? "..." : "")
                + " @" + type + "}";

    }
}
