import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        // Read high-level language code from file
        String input = new String(Files.readAllBytes(Paths.get("input.txt")));

        // Parse the code and generate the AST
        Parser parser = new Parser();
        boolean success = parser.parse(input);

        if (!success) {
            System.out.println("Parse failed.");
            return;
        }

        // Generate Pseudo Assembler code from the AST
        String pseudoCode = parser.getAst().getCode();

        System.out.println(pseudoCode);
    }
}