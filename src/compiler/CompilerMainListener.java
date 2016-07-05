package compiler;

import com.sun.corba.se.impl.io.TypeMismatchException;
import org.antlr.v4.tool.ErrorType;

import java.util.*;

public class CompilerMainListener extends  CompilerBaseListener {

    HashMap<String, Variable> variables = new HashMap<>();
    Stack<Integer> frameStack = new Stack<>();
    LinkedList<String> instructions = new LinkedList<>();
    private ArrayList<Integer> constants = new ArrayList<>();
HashMap outerVariables = new HashMap(variables);
    private final String BOOL = "boolean";
    public ArrayList<Integer> getConstants() {
        return new ArrayList<>(constants);
    }
    public LinkedList<String> getInstructions() {
        return new LinkedList<>(instructions);
    }

    private class Variable {
        private int index;
        private String type;
        Variable(int i, boolean isBool) {
            index = i;
            type = isBool ? "boolean" : "int";
        }
        int index() { return index; }
        String type() { return type; }
    }

    private void setJmpAdresses() {
        for (ListIterator<String> i = instructions.listIterator(); i.hasNext();) {
            String instr = i.next();
            if (instr.charAt(0) == '?') {
                int offsetStartInd = instr.indexOf('+') > 0 ? instr.indexOf('+') : instr.indexOf('-');
                int offset = Integer.parseInt(instr.substring(offsetStartInd));
                int position = i.previousIndex() + 1 + offset;
                i.set(instr.substring(1, offsetStartInd - 1) +  (position >> 8) + " " + (position & 0xFF));
            }
        }
    }/*
    @Override
    public void exitVarDeclaration(CompilerParser.VarDeclarationContext ctx) {
        for (ListIterator<String> i = instructions.listIterator(instructions.size()); i.hasPrevious(); ) {
            String instr = i.previous();
            if(instr.charAt(0) == '!') {
                i.set("ifeq" + );
            }
        }
        List<CompilerParser assigns = ctx.assignInDec();
        for (Iterator<CompilerParser.AssignInDecContext> i = assigns.iterator(); i.hasNext(); ) {
            CompilerParser.AssignInDecContext asgnCtx = i.next();

        }
    }

   /* @Override
    public void  exitIntExpression (CompilerParser.IntExpressionContext ctx) {
        while() {

        }

        switch (ctx.exprCase) {
            //NAME
            case 0:
                String name = ctx.NAME().getText();
                if (!variables.containsKey(name))
                    throw new NoSuchFieldError(name);  //TODO
                if (variables.get(name).type != "int")

                    throw  new TypeMismatchException(name);
                instructions.add("iload #" + variables.get(name).index());
                break;
            //INT
            case 1:
                constants.add(Integer.parseInt(ctx.getText()));
                instructions.add("ldc #" + constants.size());  //TODO check if already exist; 0-5
                break;
            // operation
            case 2:
                switch (ctx.INT_OP().getText().charAt(0)) {
                    case '+':
                        instructions.add("iadd");
                        break;
                    case '-':
                        instructions.add("isub");
                        break;
                    case '*':
                        instructions.add("imul");
                        break;
                    case '/':
                        instructions.add("idiv");
                        break;
                    case '%':
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    @Override
    public void exitBoolExpression(CompilerParser.BoolExpressionContext ctx) {
        switch (ctx.exprCase) {
            //NAME
            case 0:
                String name = ctx.NAME().getText();
                if (!variables.containsKey(name))UndefinedVariableException
                if (variables.get(name).type != "boolean")
                    throw  new TypeMismatchException(name);
                break;
            //
                    throw new NoSuchFieldError(name); //TODO: true
            case 1:
                instructions.add("iconst_1");
                break;
            case 2:
                instructions.add("iconst_0");
                break;
            case 3:
                instructions.add("iconst_1");
                instructions.add("ixor");
                break;
            case 4:
                switch(ctx.BOOL_OP().getText()) {
                    case "&&":
                        instructions.add("iand");
                        break;
                    case "||":
                        instructions.add("ior");
                        break;
                    default:
                        break;
                }
                break;
            case 5:
                switch ((ctx.CMP_OP().getText())) {
                    case ">":
                        instructions.add("if_icmpgt X");
                        instructions.add("iconst_0");
                        instructions.add("goto Y");
                        instructions.add("iconst_0");//X
                        instructions.add("if_icmpge #X #Y"); //>=
                        instructions.add("if_icmplt #X #Y"); // <
                        instructions.add("if_icmple #X #Y");
                        instructions.add("if_icmpeq #X #Y"); // ==
                        instructions.add("if_icmpne #X #Y"); //!=

                }


        }
    }*/
}
