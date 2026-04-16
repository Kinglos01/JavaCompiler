import java.io.PushbackReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    enum TYPE {INTDATATYPE}

 AbstractSyntaxTree ast = new AbstractSyntaxTree();

    public AbstractSyntaxTree getAst() {
        return ast;
    }


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

            ast.setNodeProgram(parseProgram());

            matches(Scanner.TOKEN.SCANEOF);

            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return false;
        }
    }


    private AbstractSyntaxTree.NodeProgram parseProgram() throws Exception {
        AbstractSyntaxTree.NodeDecls decls = parseDeclarations();
        AbstractSyntaxTree.NodeStmts stmts = parseStatements();
        AbstractSyntaxTree.NodeProgram program = ast.new NodeProgram(decls, stmts);
        return program;
    }


    private AbstractSyntaxTree.NodeDecls parseDeclarations() throws Exception {
        AbstractSyntaxTree.NodeDecls declsNode = ast.new NodeDecls();
        while (nextToken == Scanner.TOKEN.DECLARE) {
            AbstractSyntaxTree.NodeId id = parseDeclare();
            declsNode.decls.add(id);
        }
        return declsNode;
    }

    private AbstractSyntaxTree.NodeId parseDeclare() throws Exception {
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
        AbstractSyntaxTree.NodeId idNode = ast.new NodeId(varName);
        return idNode;
    }


    private AbstractSyntaxTree.NodeStmts parseStatements() throws Exception {
        AbstractSyntaxTree.NodeStmts stmtsNode = ast.new NodeStmts();
        while (nextToken == Scanner.TOKEN.PRINT   || nextToken == Scanner.TOKEN.INITIALIZE || nextToken == Scanner.TOKEN.IF      ||
                nextToken == Scanner.TOKEN.LOOP    || nextToken == Scanner.TOKEN.CALC) {
            AbstractSyntaxTree.NodeStmt stmt = parseStatement();
            stmtsNode.stmts.add(stmt);
        }
        return stmtsNode;
    }


    private AbstractSyntaxTree.NodeStmt parseStatement() throws Exception {
        switch (nextToken) {
            case PRINT:      return  parsePrint();
            case INITIALIZE: return parseInitialize();
            case IF:         return  parseIf();
            case LOOP:       return  parseLoop();
            case CALC:       return parseCalc();
            default:
                throw new Exception("Expected statement but got: " + nextToken
                        + " | Buffer: " + scanner.getTokenBufferString());
        }
    }


    private AbstractSyntaxTree.NodeInitialize parseInitialize() throws Exception {
        matches(Scanner.TOKEN.INITIALIZE);


        String varName = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.EQUALS);


        String value = scanner.getTokenBufferString();

        matches(Scanner.TOKEN.INTCONST);

        System.out.println("Initialize: " + varName + " = " + value);

        AbstractSyntaxTree.NodeId idNode = ast.new NodeId(varName);
        AbstractSyntaxTree.NodeIntConst intConstNode = ast.new NodeIntConst(Integer.parseInt(value));
        AbstractSyntaxTree.NodeInitialize initNode = ast.new NodeInitialize(idNode, intConstNode);
        return initNode;
    }

    private AbstractSyntaxTree.NodePrint parsePrint() throws Exception {
        matches(Scanner.TOKEN.PRINT);

        String varName = scanner.getTokenBufferString();

        matches(Scanner.TOKEN.ID);

        System.out.println("Print: " + varName);
        AbstractSyntaxTree.NodeId idNode = ast.new NodeId(varName);
        AbstractSyntaxTree.NodePrint printNode = ast.new NodePrint(idNode);
        return printNode;
    }

    private AbstractSyntaxTree.NodeIf parseIf() throws Exception {
        matches(Scanner.TOKEN.IF);

        String leftId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.EQUALS);

        String rightId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.THEN);

        AbstractSyntaxTree.NodeStmts stmtsNode = parseStatements();

        matches(Scanner.TOKEN.ENDIF);

        AbstractSyntaxTree.NodeId leftNode = ast.new NodeId(leftId);
        AbstractSyntaxTree.NodeId rightNode = ast.new NodeId(rightId);
        AbstractSyntaxTree.NodeIf ifNode = ast.new NodeIf(leftNode, rightNode, stmtsNode);
        return ifNode;
    }

    private AbstractSyntaxTree.NodeLoop parseLoop() throws Exception {
        matches(Scanner.TOKEN.LOOP);

        String leftId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.NOTEQUALS);

        String rightId = scanner.getTokenBufferString();
        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.DO);

        AbstractSyntaxTree.NodeStmts stmtsNode = parseStatements();

        matches(Scanner.TOKEN.ENDLOOP);

        AbstractSyntaxTree.NodeId leftNode = ast.new NodeId(leftId);
        AbstractSyntaxTree.NodeId rightNode = ast.new NodeId(rightId);
        AbstractSyntaxTree.NodeLoop loopNode = ast.new NodeLoop(leftNode, rightNode, stmtsNode);
        return loopNode;
    }

    private AbstractSyntaxTree.NodeCalc parseCalc() throws Exception {
        matches(Scanner.TOKEN.CALC);

        String varName = scanner.getTokenBufferString();

        matches(Scanner.TOKEN.ID);

        matches(Scanner.TOKEN.EQUALS);

        AbstractSyntaxTree.NodeExpr exprNode = parseExpr();

        System.out.println("Calc: " + varName);

        AbstractSyntaxTree.NodeId idNode = ast.new NodeId(varName);
        AbstractSyntaxTree.NodeCalc calcNode = ast.new NodeCalc(idNode, exprNode);
        return calcNode;
    }

    private AbstractSyntaxTree.NodeExpr parseTerm() throws Exception {
        if (nextToken == Scanner.TOKEN.ID) {
            String id = scanner.getTokenBufferString();

            matches(Scanner.TOKEN.ID);
            return ast.new NodeId(id);
        }

        else if (nextToken == Scanner.TOKEN.INTCONST) {
            String val = scanner.getTokenBufferString();

            matches(Scanner.TOKEN.INTCONST);
            return ast.new NodeIntConst(Integer.parseInt(val));
        }

        else {
            throw new Exception("Expected ID or INTCONST but got: " + nextToken
                    + " | Buffer: " + scanner.getTokenBufferString());
        }
    }


    private AbstractSyntaxTree.NodeExpr parseExpr() throws Exception {
        AbstractSyntaxTree.NodeExpr termNode = parseTerm();
        AbstractSyntaxTree.NodeExpr exprNode = parseMoreExpr(termNode);
        return exprNode;
    }


    private AbstractSyntaxTree.NodeExpr parseMoreExpr(AbstractSyntaxTree.NodeExpr leftNode) throws Exception {
        if (nextToken == Scanner.TOKEN.PLUS) {
            matches(Scanner.TOKEN.PLUS);
            AbstractSyntaxTree.NodeExpr rightNode = parseExpr();
            return ast.new NodePlus(leftNode, rightNode);
        }
        return leftNode;
    }

}
