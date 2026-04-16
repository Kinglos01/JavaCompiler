import java.util.List;
import java.util.ArrayList;

public class AbstractSyntaxTree {

    NodeProgram root;

    public void setNodeProgram(NodeProgram program) {
        this.root = program;
    }

    public NodeProgram getProgram() {
        return root;
    }

    public void show() {
        root.show();
    }

    public abstract class NodeBase {

        public abstract void show();
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
    }

    public class NodeIntConst extends NodeExpr {
        int value;

        NodeIntConst(int value) {
            this.value = value;
        }

        public void show() {
            System.out.println("AST int const " + value);
        }
    }

    public class NodePlus extends NodeExpr {
        NodeExpr left, right;

        NodePlus(NodeExpr left, NodeExpr right) {
            this.left = left; this.right = right;
        }


        public void show() {
            System.out.println("AST plus");
            System.out.print("LHS:"); left.show();
            System.out.print("RHS:"); right.show();
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
    }

    public class NodeInitialize extends NodeStmt {
        NodeId id;
        NodeIntConst intConst;

        NodeInitialize(NodeId id, NodeIntConst intConst) {
            this.id = id; this.intConst = intConst;
        }

        public void show() {
            System.out.println("AST initialize");
            id.show();
            intConst.show();
        }
    }

    public class NodeCalc extends NodeStmt {
        NodeId id;
        NodeExpr expr;

        NodeCalc(NodeId id, NodeExpr expr) {
            this.id = id; this.expr = expr;
        }

        public void show() {
            System.out.println("AST calc");
            id.show();
            expr.show();
        }
    }

    public class NodeIf extends NodeStmt {
        NodeId left, right;
        NodeStmts stmts;

        NodeIf(NodeId left, NodeId right, NodeStmts stmts) {
            this.left = left; this.right = right; this.stmts = stmts;
        }

        public void show() {
            System.out.println("AST if");
            System.out.print("LHS: "); left.show();
            System.out.print("RHS: "); right.show();
            System.out.println("if body");
            //So AST statements doesn't multiple times to match specs
            for (NodeStmt s : stmts.stmts) s.show();
            System.out.println("AST endif");
        }
    }

    public class NodeLoop extends NodeStmt {
        NodeId left, right;
        NodeStmts stmts;

        NodeLoop(NodeId left, NodeId right, NodeStmts stmts) {
            this.left = left; this.right = right; this.stmts = stmts;
        }

        public void show() {
            System.out.println("AST loop");
            System.out.print("LHS: "); left.show();
            System.out.print("RHS: "); right.show();
            System.out.println("loop body");
            //So AST statements doesn't multiple times to match specs
            for (NodeStmt s : stmts.stmts) s.show();
            System.out.println("AST endloop");
        }
    }

    public class NodeStmts extends NodeBase {
        List<NodeStmt> stmts = new ArrayList<>();


        public void show() {
            System.out.println("AST Statements");
            for (NodeStmt s : stmts) s.show();
        }
    }

    public class NodeDecls extends NodeBase {

        List<NodeId> decls = new ArrayList<>();

        public void show() {
            System.out.println("AST Declarations");
            for (NodeId d : decls) d.show();
        }
    }

    public class NodeProgram extends NodeBase {
        NodeDecls decls;
        NodeStmts stmts;

        NodeProgram(NodeDecls decls, NodeStmts stmts) {
            this.decls = decls; this.stmts = stmts;
        }

        public void show() {
            decls.show();
            stmts.show();
        }
    }
}