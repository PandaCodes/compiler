package compiler;


import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.util.Iterator;
import java.util.ListIterator;

public class Compiler {
    public static void main(String[] args) {

        String filename = "/home/user/IdeaProjects/compiler/test/Main.java";
        ANTLRFileStream in;
        try {
            in = new ANTLRFileStream(filename);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }
        CompilerLexer lexer = new CompilerLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CompilerParser parser = new CompilerParser(tokens);

        // Specify our entry point
        CompilerParser.MainContext context = parser.main();


        File f = new File(filename.substring(0, filename.lastIndexOf('.') + 1) + "mmc");

        f.getParentFile().mkdirs();

        FileOutputStream fos = null;

        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (fos == null)  return;
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        try {
            for (ListIterator<Integer> i = parser.getConstants().listIterator(); i.hasNext(); ) {
                bw.write("const #" + (i.nextIndex() + 1) + " " + i.next());
                bw.newLine();
            }
            for (Iterator<String> i = parser.getInstructions().iterator(); i.hasNext(); ) {
                bw.write(i.next());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Walk it and attach our listener
        //ParseTreeWalker walker = new ParseTreeWalker();
        //CompilerMainListener listener = new CompilerMainListener();
        //walker.walk(listener, context);

    }
}
