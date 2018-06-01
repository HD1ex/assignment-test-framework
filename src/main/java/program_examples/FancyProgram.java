package program_examples;

import edu.kit.informatik.Terminal;

/**
 * @author Alexander Sommer
 * @since 23.01.2018
 */
public class FancyProgram {
    public static void main(String[] args) {
        String input;

        while (!(input = Terminal.readLine()).equals("quit")) {
            switch (input) {
                case "msg":
                    Terminal.printLine("WABBALUBBADUPDUP!");
                    break;
                case "pi":
                    Terminal.printLine(Math.PI);
                    break;
                case "meaning of life":
                    Terminal.printLine(42);
                    break;
                case "error":
                    Terminal.printError("the Praktomat is working!");
                    break;
                case "dragon":
                    Terminal.printLine("O=- .-  -. -=O");
                    Terminal.printLine("H  /(    )\\  H");
                    Terminal.printLine("| |  -^^-  | |");
                    Terminal.printLine("   \\_ `' _/");
                    Terminal.printLine("|    \\  )    |");
                    Terminal.printLine("H     )/     H");
                    Terminal.printLine("O=-  ('    -=O");
                    break;
                case "quit":
                    break;
                default:
                    Terminal.printError("unknown command.");
            }
        }
    }
}
