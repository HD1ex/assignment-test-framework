package program_examples;

import edu.kit.informatik.Terminal;

/**
 * This shows how to create a test by interacting with the program
 * 
 * @author Alexander Sommer
 * @since 23.01.2018
 */
public class FancyProgramTestCreation {
    public static void main(String[] args) {
        Terminal.isTestCreation = true;

        FancyProgram.main(args);

        Terminal.saveInputOutputPairs("IOTestExample.io");
    }
}
