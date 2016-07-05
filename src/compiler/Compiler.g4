//alias antlr4='java -jar /usr/local/lib/antlr-4.5.3-complete.jar'
grammar Compiler;

@header {
package compiler;
import com.sun.corba.se.impl.io.TypeMismatchException;
import com.sun.org.apache.xml.internal.security.signature.ReferenceNotInitializedException; // ...

import java.util.*;

}
@parser::members {
    private HashMap<String, Variable> variables = new HashMap<>();
    private LinkedList<String> instructions = new LinkedList<>();
    private ArrayList<Integer> constants = new ArrayList<>();
    private final String BOOL = "boolean";
    private final String INT = "int";
    private int line;
    private int column;    //TODO

    public ArrayList<Integer> getConstants() {
        return new ArrayList<>(constants);
    }
    public LinkedList<String> getInstructions() {
        return new LinkedList<>(instructions);
    }

    private class Variable {
        int index;
        String type;
        boolean initialized = false;
        Variable(int i, String t) {
            index = i;
            type = t;
        }
    }

    private String bAddr(int addr) {
        return (addr >> 8) + " " + (addr & 0xFF);
    }

    private void checkVariable(String name, String type) {
        checkVariable(name);
        checkType(type, variables.get(name).type);
    }
    private void checkVariable(String name) {
        if (!variables.containsKey(name))
            throw new Error("Variable is not defined: " + name);
    }
    private void checkType(String expected, String found) {
        if (!expected.equals(found)) {
            throw  new Error("Type mistmatch: " + expected + " expexted, " + found + " found");
        }
    }
    private void checkVarInitialized(String name) {
        if (!variables.get(name).initialized)
                throw new Error("Variable not initialized: " + name);
    }
    private void checkExpressionType(String expected, ExpressionContext expr) {
            expr.getStart().getCharPositionInLine();
            if (!expected.equals(expr.type)) {
                throw  new TypeMismatchException(expected + " expexted, " + expr.type + " found:" +
                    " position " + expr.getStart().getLine() + ":" + expr.getStart().getCharPositionInLine());
            }
        }

    //Принимает тип условного (как ожидается) выражения и его инструкции.
    //Возвращает адрес начала цикла (условного выражения)
    private int doStuffBeforeLoop(String type, LinkedList<String> ownInstructions) {
        checkType(BOOL, type);
        int loopBeginAddr = instructions.size();
        instructions.addAll(ownInstructions);
        instructions.add("!");
        return loopBeginAddr;
    }

    //Принимает адрес начала цикла
    private void doStuffAfterLoop(int loopBeginAddr, int conditionAddr) {
        instructions.add("goto " + bAddr(loopBeginAddr));
        int loopEndAddr = instructions.size();
        instructions.set(conditionAddr, "ifeq " +  bAddr(loopEndAddr));
    }

    //Устанавливает вместо всех относительных адресов абсолютные в инструкциях вида "?if... +adr"
    private void setJmpAdresses() {
        for (ListIterator<String> i = instructions.listIterator(); i.hasNext();) {
            String instr = i.next();
            if (instr.charAt(0) == '?') {
                int offsetStartInd = instr.indexOf('+');        // '-' TODO ?
                int offset = Integer.parseInt(instr.substring(offsetStartInd));
                int position = i.previousIndex() + offset;
                i.set(instr.substring(1, offsetStartInd) +  bAddr(position));
            }
        }
    }

}

main :
    'public' 'class' NAME '{' ///not  Main ??
        {
            //Соответствие имени файла
        }
    'public' 'static' TYPE 'main''(''String' ('['']' | '...')'args'')' '{'
    (varDeclarationString | statement)*
    'return' NAME ';'
    '}'
    '}'
        {
            String name = $NAME.getText();
            checkVariable(name, $TYPE.getText());
            setJmpAdresses();
            instructions.add("ret #" + variables.get(name).index);  //add return anywhere?
        }
    ;

varDeclarationString
    : TYPE varDeclaration[$TYPE.getText()] (',' varDeclaration[$TYPE.getText()])* ';'
    ;
varDeclaration[String type]
    : NAME
        {
            String name = $NAME.getText();
            if (variables.containsKey(name))
                    throw new Error("Variable '" + name +"' is already defined in the scope");
            int index = variables.size() + 1;
            variables.put(name, new Variable(index, $type));

        }
            ('=' expr=expression
            {
                checkType($type, $expr.type);
                instructions.addAll($expr.ownInstructions);
                variables.get(name).initialized = true;
                instructions.add("istore #" + index);
            })?
    ;

statement
    :
        {
            HashMap<String, Variable> outerVariables = new HashMap(variables);  //TODO локальные переменные перекрывают глобальные
        }
    '{' ( statement | varDeclarationString )* '}'
        {
            variables = outerVariables;
        }
    | 'if' '(' expr=expression ')'
        {
            checkType(BOOL, $expr.type);
            instructions.addAll($expr.ownInstructions);
            instructions.add("!");
            int conditionAddr = instructions.size() - 1;

        }
        statement
        {
            instructions.add("~");
            int elseOrEndifAddr = instructions.size();

            instructions.set(conditionAddr, "ifeq " + bAddr(elseOrEndifAddr));
        }
        (iselse='else' statement
        {
            int elseEnd = instructions.size();
            instructions.set(elseOrEndifAddr - 1, "goto " + bAddr(elseEnd));
        })?
        {
            if ($iselse == null) {
                instructions.set(conditionAddr, "ifeq " + bAddr(elseOrEndifAddr - 1));
                instructions.remove(elseOrEndifAddr - 1);
            }
        }

    | 'while' '(' expr=expression ')' //????????????????????????????
        {
            int beginAddr = doStuffBeforeLoop($expr.type, $expr.ownInstructions);
            int conditionAddr = instructions.size() - 1;
        }
        statement
        {
            doStuffAfterLoop(beginAddr, conditionAddr);
        }
    |
        {
            HashMap<String, Variable> outerVariables = new HashMap(variables);
        }
    'for' '(' (varDeclarationString | ';') expr=expression ';' (repeatSt=varStatement)? /*(','repeatSt=varStatement)**/ ')'   //TODO break/ continue -> expression?
        {
            int beginAddr = doStuffBeforeLoop($expr.type, $expr.ownInstructions);
            int conditionAddr = instructions.size() - 1;
        }
        statement
        {
            //if (repeatSt != null)   //????????????????????? why can't
                instructions.addAll($repeatSt.ownInstructions);

            doStuffAfterLoop(beginAddr, conditionAddr);

            variables = outerVariables;

        }
    | vs=varStatement ';'
        {
            instructions.addAll($vs.ownInstructions);
        }
    ;

varStatement returns [LinkedList<String> ownInstructions]
    @init { $ownInstructions = new LinkedList<String>(); }
    : NAME '=' expr=expression
        {
            String name = $NAME.getText();
            checkVariable(name);
            checkType(variables.get(name).type, $expr.type); //expexted Var type for expression
            $ownInstructions.addAll($expr.ownInstructions);
            $ownInstructions.add("istore #" + variables.get(name).index);
            variables.get(name).initialized = true;
        }
    | NAME INT_EQ_OP expr=expression   //iinc ?
        {
            String name = $NAME.getText();
            checkVariable(name);
            Variable var = variables.get(name);
            checkType(INT, $expr.type);
            checkType(INT, var.type);
            $ownInstructions.addAll($expr.ownInstructions);
            $ownInstructions.add("iload #" + var.index);
            switch ($INT_EQ_OP.getText().charAt(0)) {
                case '+':
                    $ownInstructions.add("iadd");
                    break;
                case '-':
                    $ownInstructions.add("isub");
                    break;
                case '*':
                    $ownInstructions.add("imul");
                    break;
                case '/':
                    $ownInstructions.add("idiv");
                    break;
                case '%':                //TODO
                    break;
                default:
                    break;
            }
            $ownInstructions.add("istore #" + var.index);
            variables.get(name).initialized = true;

        }
    | NAME INC_DEC
        {
            String name = $NAME.getText();   //TODO no repet
            checkVariable(name, INT);
            int byteConst = $INC_DEC.getText().charAt(0) == '+' ? 1 : -1;
            $ownInstructions.add("iinc #" + variables.get(name).index + " " + byteConst);
        }
    ;

expression returns [LinkedList<String> ownInstructions, String type]
    @init { $ownInstructions = new LinkedList<>(); }
    : NAME
        {
            String name = $NAME.getText();
            checkVariable(name);
            checkVarInitialized(name);
            $ownInstructions.add("iload #" + variables.get(name).index);
            $type = variables.get(name).type;

        }
    | INT_NUM
        {
            int val = Integer.parseInt($INT_NUM.getText());
            if (val < 6) {
                $ownInstructions.add("iconst_" + val);    //tODO iconst_m1
            } else {
                int index = constants.indexOf(val) + 1;
                if (index == 0) {
                    constants.add(val);
                    index = constants.size();
                }
                $ownInstructions.add("ldc #" + index);
            }
            $type = INT;
        }
    | first=expression INT_OP second=expression
        {
            $ownInstructions = new LinkedList<>();  //WHY?! how to optimize

            checkType(INT, $first.type);
            checkType(INT, $second.type);
            $type = INT;
            $ownInstructions.addAll($second.ownInstructions);
            $ownInstructions.addAll($first.ownInstructions);
            switch ($INT_OP.getText().charAt(0)) {
                case '+':
                    $ownInstructions.add("iadd");
                    break;
                case '-':
                    $ownInstructions.add("isub");
                    break;
                case '*':
                    $ownInstructions.add("imul");
                    break;
                case '/':
                    $ownInstructions.add("idiv");
                    break;
                case '%':                //TODO
                    break;
                default:
                    break;
            }
        }
   /* | '-' expr=expression     ////////////TODO
        {
            checkType(INT, $expr.type);
            $ownInstructions.addAll($expr.ownInstructions);
            $ownInstructions.add("ineg");
        }*/
    | 'true'
        {
            $ownInstructions.add("iconst_1");
            $type = BOOL;
        }
    | 'false'
        {
            $ownInstructions.add("iconst_0");
            $type = BOOL;
        }
    | '!' expr=expression
        {
            checkType(BOOL, $expr.type);
            $ownInstructions.addAll($expr.ownInstructions);
            $ownInstructions.add("iconst_1");
            $ownInstructions.add("ixor");
        }
    | first=expression BOOL_OP second=expression
        {
            $ownInstructions = new LinkedList<>();

            checkType(BOOL, $first.type);
            checkType(BOOL, $second.type);
            $ownInstructions.addAll($first.ownInstructions);
            int jumpOverTheSecond = $second.ownInstructions.size() + 1;
            switch($BOOL_OP.getText().charAt(0)) {
                case '&':
                    //Если false, второе выражение не вычисляется
                    instructions.add("?ifeq +" + jumpOverTheSecond);
                    break;
                case '|':
                    //если true, второе выражение не вычисляем
                    instructions.add("?ifne +" + jumpOverTheSecond);
                    break;
                case '^':
                    //TODO
                    break;
                default:
                    break;
            }
            $ownInstructions.addAll($second.ownInstructions);
        }
    | first=expression CMP_OP second=expression
        {
            $ownInstructions = new LinkedList<>();

            checkType(INT, $first.type);
            checkType(INT, $second.type);
            $type = BOOL;
            $ownInstructions.addAll($second.ownInstructions);
            $ownInstructions.addAll($first.ownInstructions);
            String op = $CMP_OP.getText();
            switch (op.charAt(0)) {
                case '>' :
                    op = op.length() > 1 ? "if_icmpge" : "if_icmpgt";
                    break;
                case '<' :
                    op = op.length() > 1 ? "if_icmple" : "if_icmplt";
                    break;
                case '!' :
                    op = "if_icmpne";
                    break;
                case '=' :
                    op = "if_icmpeq";
                    break;
                default:
            }
            $ownInstructions.add("?" + op + " +3");
            $ownInstructions.add("iconst_0");
            $ownInstructions.add("?goto +2");
            $ownInstructions.add("iconst_1");
        }
    | '(' expr=expression ')'
        {
            $ownInstructions = $expr.ownInstructions;
            $type = $expr.type;
        }
        //TODO
    //| boolExpression '?' intExpression ':' intExpression {  }
    ;

TYPE : 'int'
    | 'boolean'
    ;

INC_DEC : '++' | '--' ;
INT_OP : '+' | '-' | '*' | '/' | '%' ;
INT_EQ_OP : '+=' | '-=' | '*=' | '/=' | '%=' ;
CMP_OP : '>' | '<' | '==' | '!=' | '>=' | '<=' ;
BOOL_OP : '&&' | '||' ;

NAME : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9')* ;

INT_NUM : ('0'..'9')('0'..'9')* ;  //to much?

BLOCK_COMMENTS : '/*' (.)*? '*/'  -> skip;
LINE_COMMENTS : '//' (.)*? '\n' -> skip;
WS : [ \t \r \n]+ -> skip ;
