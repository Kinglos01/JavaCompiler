

import program.PseudoAssemblyWithStringProgram;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {




        // Read code from file
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

        System.out.println("\n--- Pseudo Assembler Code ---");
        System.out.println(pseudoCode);

        // Compile and run the Pseudo Assembler code
        int numVirtualRegistersInt = 32;
        int numVirtualRegistersString = 32;
        String outputClassName = "MyProgram";
        String outputPackageNameDot = "mypackage";
        String classRootDir = System.getProperty("user.dir") + "/" + "target/classes";

        PseudoAssemblyWithStringProgram pseudoAssembly = new PseudoAssemblyWithStringProgram(
                pseudoCode,
                outputClassName,
                outputPackageNameDot,
                classRootDir,
                numVirtualRegistersInt,
                numVirtualRegistersString
        );

        boolean parseSuccessful = pseudoAssembly.parse();

        if (parseSuccessful) {
            pseudoAssembly.generateBytecode();
            PrintStream outstream = new PrintStream(System.out);
            System.out.println("\n--- Program Output ---");
            pseudoAssembly.run(outstream);
        } else {
            System.out.println("Pseudo Assembler parse failed.");
            System.out.println(pseudoAssembly.getAllParseMessages());
        }
    }
}