import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AbstractSyntaxTree {

    NodeProgram root;
    List<String> code = new ArrayList<>();
    int nextRegister = 1;
    Map<String, String> registerMap = new HashMap<>();

    public String getNextRegister() {
        return "ri" + nextRegister++;
    }

    public void setNodeProgram(NodeProgram program) {
        this.root = program;
    }

    public NodeProgram getProgram() {
        return root;
    }

    public void show() {
        root.show();
    }

    public void generateCode() {
        root.generateCode();
    }

    public String getCode() {
        root.generateCode();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.size(); i++) {
            sb.append(code.get(i));
            if (i < code.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public abstract class NodeBase {
        public abstract void show();
        public abstract String generateCode();
    }

    public abstract class NodeExpr extends NodeBase {}

    public abstract class NodeStmt extends NodeBase {}

    public class NodeId extends NodeExpr {
        String name;

        NodeId(String name) {
            this.name = name;
        }

        public void show() {
            System.out.println("AST id " + name);
        }

        public String generateCode() {
            if (registerMap.containsKey(name)) {
                return registerMap.get(name);
            }
            String reg = getNextRegister();
            code.add("loadintvar " + reg + ", " + name);
            registerMap.put(name, reg);
            return reg;
        }
    }

    public class NodeIntConst extends NodeExpr {
        int value;

        NodeIntConst(int value) {
            this.value = value;
        }

        public void show() {
            System.out.println("AST int const " + value);
        }

        public String generateCode() {
            String reg = getNextRegister();
            code.add("loadintliteral " + reg + ", " + value);
            return reg;
        }
    }

    public class NodePlus extends NodeExpr {
        NodeExpr left, right;

        NodePlus(NodeExpr left, NodeExpr right) {
            this.left = left;
            this.right = right;
        }

        public void show() {
            System.out.println("AST plus");
            System.out.print("LHS:"); left.show();
            System.out.print("RHS:"); right.show();
        }

        public String generateCode() {
            String leftReg = left.generateCode();
            String rightReg = right.generateCode();
            String resultReg = getNextRegister();
            code.add("add " + leftReg + ", " + rightReg + ", " + resultReg);
            return resultReg;
        }
    }

    public class NodePrint extends NodeStmt {
        NodeId id;

        NodePrint(NodeId id) {
            this.id = id;
        }

        public void show() {
            System.out.println("AST print");
            id.show();
        }

        public String generateCode() {
            code.add("printi " + id.name);
            return "";
        }
    }

    public class NodeInitialize extends NodeStmt {
        NodeId id;
        NodeIntConst intConst;

        NodeInitialize(NodeId id, NodeIntConst intConst) {
            this.id = id;
            this.intConst = intConst;
        }

        public void show() {
            System.out.println("AST initialize");
            id.show();
            intConst.show();
        }

        public String generateCode() {
            String reg = getNextRegister();
            code.add("loadintliteral " + reg + ", " + intConst.value);
            code.add("storeintvar " + reg + ", " + id.name);
            registerMap.put(id.name, reg);
            return "";
        }
    }

    public class NodeCalc extends NodeStmt {
        NodeId id;
        NodeExpr expr;

        NodeCalc(NodeId id, NodeExpr expr) {
            this.id = id;
            this.expr = expr;
        }

        public void show() {
            System.out.println("AST calc");
            id.show();
            expr.show();
        }

        public String generateCode() {
            String exprReg = expr.generateCode();
            code.add("storeintvar " + exprReg + ", " + id.name);
            registerMap.put(id.name, exprReg);
            return "";
        }
    }

    public class NodeIf extends NodeStmt {
        NodeId left, right;
        NodeStmts stmts;

        NodeIf(NodeId left, NodeId right, NodeStmts stmts) {
            this.left = left;
            this.right = right;
            this.stmts = stmts;
        }

        public void show() {
            System.out.println("AST if");
            System.out.print("LHS: "); left.show();
            System.out.print("RHS: "); right.show();
            System.out.println("if body");
            for (NodeStmt s : stmts.stmts) s.show();
            System.out.println("AST endif");
        }

        public String generateCode() {
            String leftReg = left.generateCode();
            String rightReg = right.generateCode();
            String endLabel = "endif" + nextRegister;
            code.add("bne " + leftReg + ", " + rightReg + ", " + endLabel);
            stmts.generateCode();
            code.add(":" + endLabel);
            return "";
        }
    }

    public class NodeLoop extends NodeStmt {
        NodeId left, right;
        NodeStmts stmts;

        NodeLoop(NodeId left, NodeId right, NodeStmts stmts) {
            this.left = left;
            this.right = right;
            this.stmts = stmts;
        }

        public void show() {
            System.out.println("AST loop");
            System.out.print("LHS: "); left.show();
            System.out.print("RHS: "); right.show();
            System.out.println("loop body");
            for (NodeStmt s : stmts.stmts) s.show();
            System.out.println("AST endloop");
        }

        public String generateCode() {
            String startLabel = "loopstart" + nextRegister;
            String endLabel = "loopend" + nextRegister;
            code.add(":" + startLabel);
            String leftReg = left.generateCode();
            String rightReg = right.generateCode();
            code.add("be " + leftReg + ", " + rightReg + ", " + endLabel);
            stmts.generateCode();
            code.add("branch " + startLabel);
            code.add(":" + endLabel);
            return "";
        }
    }

    public class NodeStmts extends NodeBase {
        List<NodeStmt> stmts = new ArrayList<>();

        public void show() {
            System.out.println("AST Statements");
            for (NodeStmt s : stmts) s.show();
        }

        public String generateCode() {
            for (NodeStmt s : stmts) s.generateCode();
            return "";
        }
    }

    public class NodeDecls extends NodeBase {
        List<NodeId> decls = new ArrayList<>();

        public void show() {
            System.out.println("AST Declarations");
            for (NodeId d : decls) d.show();
        }

        public String generateCode() {
            code.add(".data");
            for (NodeId d : decls) {
                code.add("var int " + d.name);
            }
            return "";
        }
    }

    public class NodeProgram extends NodeBase {
        NodeDecls decls;
        NodeStmts stmts;

        NodeProgram(NodeDecls decls, NodeStmts stmts) {
            this.decls = decls;
            this.stmts = stmts;
        }

        public void show() {
            decls.show();
            stmts.show();
        }

        public String generateCode() {
            decls.generateCode();
            code.add(".code");
            stmts.generateCode();
            return "";
        }
    }
}