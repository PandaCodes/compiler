// Generated from Compiler.g4 by ANTLR 4.5.3

package compiler;
import com.sun.corba.se.impl.io.TypeMismatchException;
import com.sun.org.apache.xml.internal.security.signature.ReferenceNotInitializedException; // ...

import java.util.*;


import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CompilerParser}.
 */
public interface CompilerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CompilerParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(CompilerParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilerParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(CompilerParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilerParser#varDeclarationString}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclarationString(CompilerParser.VarDeclarationStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilerParser#varDeclarationString}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclarationString(CompilerParser.VarDeclarationStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilerParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(CompilerParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilerParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(CompilerParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CompilerParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilerParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CompilerParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilerParser#varStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarStatement(CompilerParser.VarStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilerParser#varStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarStatement(CompilerParser.VarStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CompilerParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CompilerParser.ExpressionContext ctx);
}