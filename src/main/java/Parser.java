import java.io.PushbackReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    enum TYPE {INTDATATYPE}

    public class SymbolTableItem {
        String name;
        TYPE type;
    }

    Map<String, SymbolTableItem> symbolTable = new HashMap<>();

    Scanner scanner;

    Scanner.TOKEN nextToken;

    public boolean matches(Scanner.TOKEN token) throws Exception {
        if (token == nextToken) {
            // Capture buffer BEFORE scanning next token
            String currentBuffer = scanner.getTokenBufferString();

            // Scan the next token
            nextToken = scanner.scan();

            // Print AFTER advancing so buffer still holds current value
            System.out.println("Matched token: " + token + " | Token buffer: " + currentBuffer);

            return true;
        }

        throw new Exception("Parse Error\nExpected: " + token
                + "\nReceived: " + nextToken
                + "\nBuffer: " + scanner.getTokenBufferString());
    }

    public boolean parse(String program) {
        try {
            symbolTable = new HashMap<>();  // reset for each parse
            PushbackReader pbr = new PushbackReader(new StringReader(program));

            scanner = new Scanner(pbr);
            nextToken = scanner.scan();

            parseProgram();

            matches(Scanner.TOKEN.SCANEOF);

            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return false;
        }
    }


    private void parseProgram() throws Exception {
        parseDeclarations();
        parseStatements();
    }


    private void parseDeclarations() throws Exception {
        while (nextToken == Scanner.TOKEN.DECLARE) {
            parseDeclare();
        }
    }


    private void parseDeclare() throws Exception {
        matches(Scanner.TOKEN.DECLARE);

        // Capture name BEFORE matching ID (matching will advance the scanner)
        String varName = scanner.getTokenBufferString();

        matches(Scanner.TOKEN.ID);

        // Check if variable is already in the symbol table
        if (symbolTable.containsKey(varName)) {
            throw new Exception("Error: Variable '" + varName + "' has already been declared.");
        }

        // Add to symbol table
        SymbolTableItem item = new SymbolTableItem();
        item.name = varName;
        item.type = TYPE.INTDATATYPE;
        symbolTable.put(varName, item);

        System.out.println("Declared variable: " + varName);
    }


    private void parseStatements() throws Exception {
        while (nextToken == Scanner.TOKEN.PRINT   || nextToken == Scanner.TOKEN.INITIALIZE || nextToken == Scanner.TOKEN.IF      ||
                nextToken == Scanner.TOKEN.LOOP    || nextToken == Scanner.TOKEN.CALC) {
            parseStatement();
        }
    }


    private void parseStatement() throws Exception {
        switch (nextToken) {
            case PRINT:      parsePrint();      break;
            case INITIALIZE: parseInitialize(); break;
            case IF:         parseIf();         break;
            case LOOP:       parseLoop();       break;
            case CALC:       parseCalc();       break;
            default:
                throw new Exception("Expected statement but got: " + nextToken
                        + " | Buffer: " + scanner.getTokenBufferString());
        }
    }


    private void parseInitialize() throws Exception {
        matches(Scanner.TOKEN.INITIALIZE);


        String varName = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.EQUALS);


        String value = scanner.getTokenBufferString();

        matches(Scanner.TOKEN.INTCONST);

        System.out.println("Initialize: " + varName + " = " + value);
    }

    private void parsePrint() throws Exception {
        matches(Scanner.TOKEN.PRINT);

        String varName = scanner.getTokenBufferString();

        matches(Scanner.TOKEN.ID);

        System.out.println("Print: " + varName);
    }

    private void parseIf() throws Exception {
        matches(Scanner.TOKEN.IF);

        String leftId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.EQUALS);

        String rightId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.THEN);

        parseStatements();

        matches(Scanner.TOKEN.ENDIF);
    }

    private void parseLoop() throws Exception {
        matches(Scanner.TOKEN.LOOP);

        String leftId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.NOTEQUALS);

        String rightId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.DO);

        parseStatements();

        matches(Scanner.TOKEN.ENDLOOP);
    }

    private void parseCalc() throws Exception {
        matches(Scanner.TOKEN.CALC);

        String varName = scanner.getTokenBufferString();

        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.EQUALS);

        parseExpr();

        System.out.println("Calc: " + varName);
    }

    private void parseTerm() throws Exception {
        if (nextToken == Scanner.TOKEN.ID) {
            String id = scanner.getTokenBufferString();

            matches(Scanner.TOKEN.ID);
        }

        else if (nextToken == Scanner.TOKEN.INTCONST) {
            String val = scanner.getTokenBufferString();

            matches(Scanner.TOKEN.INTCONST);
        }

        else {
            throw new Exception("Expected ID or INTCONST but got: " + nextToken
                    + " | Buffer: " + scanner.getTokenBufferString());
        }
    }


    private void parseExpr() throws Exception {
        parseTerm();
        parseMoreExpr();
    }


    private void parseMoreExpr() throws Exception {
        if (nextToken == Scanner.TOKEN.PLUS) {
            matches(Scanner.TOKEN.PLUS);
            parseExpr();
        }

    }

}
