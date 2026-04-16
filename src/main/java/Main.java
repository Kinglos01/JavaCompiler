import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        // Hi prof. I didn't know where you wanted to test it so I just put it in main

        String input = "declare a\n" +
                "declare b\n" +
                "initialize a = 1\n" +
                "initialize b = 5\n" +
                "loop a != b do\n" +
                " calc a = a + 1\n" +
                " print a\n" +
                " if a=b then\n" +
                " print a\n" +
                " endif\n" +
                "endloop";

        Parser parser = new Parser();
        boolean success = parser.parse(input);

        if (success) {
            System.out.println("\n--- Abstract Syntax Tree ---");
            parser.getAst().show();
        } else {
            System.out.println("Parse failed.");
        }
    }
}
