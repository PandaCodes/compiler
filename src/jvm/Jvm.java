package jvm;

import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Created by user on 17.06.16.
 */
public class Jvm {

    private static ArrayList<Integer> constants = new ArrayList<>();
    private static ArrayList<Integer> vars = new ArrayList<>();
    private static ArrayList<String> instructions = new ArrayList<>();
    private static Stack<Integer> stack = new Stack<>();

    private static int address(String s) {
        int spaceOne = s.indexOf(' ');
        int spaceTwo = s.indexOf(' ', spaceOne + 1);
        return (Integer.parseInt(s.substring(spaceOne + 1,spaceTwo)) << 8) +
                Integer.parseInt(s.substring(spaceTwo + 1));
    }
    private static int code(String s) {
        if (s.equals("iadd")) return 1;
        if (s.equals("isub")) return 2;
        if (s.equals("imul")) return 3;
        if (s.equals("idiv")) return 4;
        if (s.equals("ixor")) return 5;

        if (s.equals("ineg")) return 6;

        if (s.contains("istore")) return 7;
        if (s.contains("iload")) return 8;
        if (s.contains("ldc")) return 9;

        if (s.contains("iconst_")) return 10;

        if (s.contains("ifeq")) return 11;
        if (s.contains("ifne")) return 12;
        if (s.contains("ifgt")) return 13;

        if (s.contains("if_icmpeq")) return 14;
        if (s.contains("if_icmpne")) return 15;
        if (s.contains("if_icmpge")) return 16;
        if (s.contains("if_icmpgt")) return 17;
        if (s.contains("if_icmple")) return 18;
        if (s.contains("if_icmplt")) return 19;

        if (s.contains("iinc")) return 22;

        if (s.contains("goto")) return 20;
        if (s.contains("ret")) return 21;
        return 0;
    }

    public static void main(String[] args) throws IOException {
        if (args[0] == null) {
            System.out.println("filereader wants to read a file, omnomnom");
            return;
        }

        constants.add(0);
        vars.add(0);

        new FileReader(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(args[0]));

        String s = br.readLine();
        while (s != null) {
            if (s.contains("const ")) {
                constants.add(Integer.parseInt(s.substring(s.indexOf(' ', 8) + 1)));
            } else {
                instructions.add(s);
            }
            s = br.readLine();
        }

        int i = 0;
        while (i < instructions.size()) {
            s = instructions.get(i);
            int num = s.indexOf("#") > 0 ? Integer.parseInt(s.substring(s.indexOf("#") + 1,
                    s.lastIndexOf(' ') > s.indexOf('#') ? s.lastIndexOf(' ') : s.length() )) : 0;
            switch (code(s)) {
                case 1:
                    stack.push(stack.pop() + stack.pop());
                    break;
                case 2:
                    stack.push(stack.pop() - stack.pop());
                    break;
                case 3:
                    stack.push(stack.pop() * stack.pop());
                    break;
                case 4:
                    stack.push(stack.pop() / stack.pop());
                    break;
                case 5:
                    stack.push(stack.pop() ^ stack.pop());
                    break;
                case 6:
                    stack.push(-stack.pop());
                    break;

                //STORE
                case 7:
                    if (vars.size() == num) {
                        vars.add(stack.pop());
                    } else {
                        vars.set(num, stack.pop());
                    }
                    break;
                //LOAD
                case 8:
                    stack.push(vars.get(num));
                    break;
                //LOAD CONST
                case 9:
                    stack.push(constants.get(num));
                    break;
                //LOAD CONST
                case 10:
                    num = Integer.parseInt(s.substring(s.indexOf("_") + 1));
                    stack.push(num);
                    break;

                //IF </=/> 0
                case 11:
                    if (stack.pop() == 0)
                        i = address(s) - 1;
                    break;
                case 12:
                    if (stack.pop() != 0)
                        i = address(s) - 1;
                    break;
                case 13:
                    if (stack.pop() > 0)
                        i = address(s) - 1;
                    break;

                //IF A </=/> B
                case 14:
                    if (stack.pop() == stack.pop())
                        i = address(s) - 1;
                    break;
                case 15:
                    if (stack.pop() != stack.pop())
                        i = address(s) - 1;
                    break;
                case 16:
                    if (stack.pop() >= stack.pop())
                        i = address(s) - 1;
                    break;
                case 17:
                    if (stack.pop() > stack.pop())
                        i = address(s) - 1;
                    break;
                case 18:
                    if (stack.pop() <= stack.pop())
                        i = address(s) - 1;
                    break;
                case 19:
                    if (stack.pop() < stack.pop())
                        i = address(s) - 1;
                    break;
                //IINC
                case 22:
                    vars.set(num, vars.get(num) + Integer.parseInt(s.substring(s.lastIndexOf(' ') + 1)));
                    break;
                //GOTO
                case 20:
                    i = address(s) - 1;
                    break;
                //ret
                case 21:
                    System.out.println(vars.get(num));
                default:
                    break;
            }
            i++;
        }

    }

}
