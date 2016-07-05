// Generated from Compiler.g4 by ANTLR 4.5.3

package compiler;
import com.sun.corba.se.impl.io.TypeMismatchException;
import com.sun.org.apache.xml.internal.security.signature.ReferenceNotInitializedException; // ...

import java.util.*;


import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CompilerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		TYPE=25, INC_DEC=26, INT_OP=27, INT_EQ_OP=28, CMP_OP=29, BOOL_OP=30, NAME=31, 
		INT_NUM=32, BLOCK_COMMENTS=33, LINE_COMMENTS=34, WS=35;
	public static final int
		RULE_main = 0, RULE_varDeclarationString = 1, RULE_varDeclaration = 2, 
		RULE_statement = 3, RULE_varStatement = 4, RULE_expression = 5;
	public static final String[] ruleNames = {
		"main", "varDeclarationString", "varDeclaration", "statement", "varStatement", 
		"expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'public'", "'class'", "'{'", "'static'", "'main'", "'('", "'String'", 
		"'['", "']'", "'...'", "'args'", "')'", "'return'", "';'", "'}'", "','", 
		"'='", "'if'", "'else'", "'while'", "'for'", "'true'", "'false'", "'!'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "TYPE", "INC_DEC", "INT_OP", "INT_EQ_OP", "CMP_OP", "BOOL_OP", "NAME", 
		"INT_NUM", "BLOCK_COMMENTS", "LINE_COMMENTS", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Compiler.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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


	public CompilerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class MainContext extends ParserRuleContext {
		public Token NAME;
		public Token TYPE;
		public List<TerminalNode> NAME() { return getTokens(CompilerParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(CompilerParser.NAME, i);
		}
		public TerminalNode TYPE() { return getToken(CompilerParser.TYPE, 0); }
		public List<VarDeclarationStringContext> varDeclarationString() {
			return getRuleContexts(VarDeclarationStringContext.class);
		}
		public VarDeclarationStringContext varDeclarationString(int i) {
			return getRuleContext(VarDeclarationStringContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).exitMain(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_main);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			match(T__0);
			setState(13);
			match(T__1);
			setState(14);
			((MainContext)_localctx).NAME = match(NAME);
			setState(15);
			match(T__2);

			            //Соответствие имени файла
			        
			setState(17);
			match(T__0);
			setState(18);
			match(T__3);
			setState(19);
			((MainContext)_localctx).TYPE = match(TYPE);
			setState(20);
			match(T__4);
			setState(21);
			match(T__5);
			setState(22);
			match(T__6);
			setState(26);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(23);
				match(T__7);
				setState(24);
				match(T__8);
				}
				break;
			case T__9:
				{
				setState(25);
				match(T__9);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(28);
			match(T__10);
			setState(29);
			match(T__11);
			setState(30);
			match(T__2);
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__17) | (1L << T__19) | (1L << T__20) | (1L << TYPE) | (1L << NAME))) != 0)) {
				{
				setState(33);
				switch (_input.LA(1)) {
				case TYPE:
					{
					setState(31);
					varDeclarationString();
					}
					break;
				case T__2:
				case T__17:
				case T__19:
				case T__20:
				case NAME:
					{
					setState(32);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(38);
			match(T__12);
			setState(39);
			((MainContext)_localctx).NAME = match(NAME);
			setState(40);
			match(T__13);
			setState(41);
			match(T__14);
			setState(42);
			match(T__14);

			            String name = ((MainContext)_localctx).NAME.getText();
			            checkVariable(name, ((MainContext)_localctx).TYPE.getText());
			            setJmpAdresses();
			            instructions.add("ret #" + variables.get(name).index);  //add return anywhere?
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationStringContext extends ParserRuleContext {
		public Token TYPE;
		public TerminalNode TYPE() { return getToken(CompilerParser.TYPE, 0); }
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public VarDeclarationStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclarationString; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).enterVarDeclarationString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).exitVarDeclarationString(this);
		}
	}

	public final VarDeclarationStringContext varDeclarationString() throws RecognitionException {
		VarDeclarationStringContext _localctx = new VarDeclarationStringContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_varDeclarationString);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			((VarDeclarationStringContext)_localctx).TYPE = match(TYPE);
			setState(46);
			varDeclaration(((VarDeclarationStringContext)_localctx).TYPE.getText());
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(47);
				match(T__15);
				setState(48);
				varDeclaration(((VarDeclarationStringContext)_localctx).TYPE.getText());
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(54);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public String type;
		public Token NAME;
		public ExpressionContext expr;
		public TerminalNode NAME() { return getToken(CompilerParser.NAME, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public VarDeclarationContext(ParserRuleContext parent, int invokingState, String type) {
			super(parent, invokingState);
			this.type = type;
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).enterVarDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).exitVarDeclaration(this);
		}
	}

	public final VarDeclarationContext varDeclaration(String type) throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState(), type);
		enterRule(_localctx, 4, RULE_varDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			((VarDeclarationContext)_localctx).NAME = match(NAME);

			            String name = ((VarDeclarationContext)_localctx).NAME.getText();
			            if (variables.containsKey(name))
			                    throw new Error("Variable '" + name +"' is already defined in the scope");
			            int index = variables.size() + 1;
			            variables.put(name, new Variable(index, _localctx.type));

			        
			setState(62);
			_la = _input.LA(1);
			if (_la==T__16) {
				{
				setState(58);
				match(T__16);
				setState(59);
				((VarDeclarationContext)_localctx).expr = expression(0);

				                checkType(_localctx.type, ((VarDeclarationContext)_localctx).expr.type);
				                instructions.addAll(((VarDeclarationContext)_localctx).expr.ownInstructions);
				                variables.get(name).initialized = true;
				                instructions.add("istore #" + index);
				            
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public ExpressionContext expr;
		public Token iselse;
		public VarStatementContext repeatSt;
		public VarStatementContext vs;
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<VarDeclarationStringContext> varDeclarationString() {
			return getRuleContexts(VarDeclarationStringContext.class);
		}
		public VarDeclarationStringContext varDeclarationString(int i) {
			return getRuleContext(VarDeclarationStringContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarStatementContext varStatement() {
			return getRuleContext(VarStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement);
		int _la;
		try {
			setState(119);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{

				            HashMap<String, Variable> outerVariables = new HashMap(variables);  //TODO локальные переменные перекрывают глобальные
				        
				setState(65);
				match(T__2);
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__17) | (1L << T__19) | (1L << T__20) | (1L << TYPE) | (1L << NAME))) != 0)) {
					{
					setState(68);
					switch (_input.LA(1)) {
					case T__2:
					case T__17:
					case T__19:
					case T__20:
					case NAME:
						{
						setState(66);
						statement();
						}
						break;
					case TYPE:
						{
						setState(67);
						varDeclarationString();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(72);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(73);
				match(T__14);

				            variables = outerVariables;
				        
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				match(T__17);
				setState(76);
				match(T__5);
				setState(77);
				((StatementContext)_localctx).expr = expression(0);
				setState(78);
				match(T__11);

				            checkType(BOOL, ((StatementContext)_localctx).expr.type);
				            instructions.addAll(((StatementContext)_localctx).expr.ownInstructions);
				            instructions.add("!");
				            int conditionAddr = instructions.size() - 1;

				        
				setState(80);
				statement();

				            instructions.add("~");
				            int elseOrEndifAddr = instructions.size();

				            instructions.set(conditionAddr, "ifeq " + bAddr(elseOrEndifAddr));
				        
				setState(86);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
				case 1:
					{
					setState(82);
					((StatementContext)_localctx).iselse = match(T__18);
					setState(83);
					statement();

					            int elseEnd = instructions.size();
					            instructions.set(elseOrEndifAddr - 1, "goto " + bAddr(elseEnd));
					        
					}
					break;
				}

				            if (((StatementContext)_localctx).iselse == null) {
				                instructions.set(conditionAddr, "ifeq " + bAddr(elseOrEndifAddr - 1));
				                instructions.remove(elseOrEndifAddr - 1);
				            }
				        
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 3);
				{
				setState(90);
				match(T__19);
				setState(91);
				match(T__5);
				setState(92);
				((StatementContext)_localctx).expr = expression(0);
				setState(93);
				match(T__11);

				            int beginAddr = doStuffBeforeLoop(((StatementContext)_localctx).expr.type, ((StatementContext)_localctx).expr.ownInstructions);
				            int conditionAddr = instructions.size() - 1;
				        
				setState(95);
				statement();

				            doStuffAfterLoop(beginAddr, conditionAddr);
				        
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 4);
				{

				            HashMap<String, Variable> outerVariables = new HashMap(variables);
				        
				setState(99);
				match(T__20);
				setState(100);
				match(T__5);
				setState(103);
				switch (_input.LA(1)) {
				case TYPE:
					{
					setState(101);
					varDeclarationString();
					}
					break;
				case T__13:
					{
					setState(102);
					match(T__13);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(105);
				((StatementContext)_localctx).expr = expression(0);
				setState(106);
				match(T__13);
				setState(108);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(107);
					((StatementContext)_localctx).repeatSt = varStatement();
					}
				}

				setState(110);
				match(T__11);

				            int beginAddr = doStuffBeforeLoop(((StatementContext)_localctx).expr.type, ((StatementContext)_localctx).expr.ownInstructions);
				            int conditionAddr = instructions.size() - 1;
				        
				setState(112);
				statement();

				            //if (repeatSt != null)   //????????????????????? why can't
				                instructions.addAll(((StatementContext)_localctx).repeatSt.ownInstructions);

				            doStuffAfterLoop(beginAddr, conditionAddr);

				            variables = outerVariables;

				        
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 5);
				{
				setState(115);
				((StatementContext)_localctx).vs = varStatement();
				setState(116);
				match(T__13);

				            instructions.addAll(((StatementContext)_localctx).vs.ownInstructions);
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarStatementContext extends ParserRuleContext {
		public LinkedList<String> ownInstructions;
		public Token NAME;
		public ExpressionContext expr;
		public Token INT_EQ_OP;
		public Token INC_DEC;
		public TerminalNode NAME() { return getToken(CompilerParser.NAME, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INT_EQ_OP() { return getToken(CompilerParser.INT_EQ_OP, 0); }
		public TerminalNode INC_DEC() { return getToken(CompilerParser.INC_DEC, 0); }
		public VarStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).enterVarStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).exitVarStatement(this);
		}
	}

	public final VarStatementContext varStatement() throws RecognitionException {
		VarStatementContext _localctx = new VarStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_varStatement);
		 ((VarStatementContext)_localctx).ownInstructions =  new LinkedList<String>(); 
		try {
			setState(134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				((VarStatementContext)_localctx).NAME = match(NAME);
				setState(122);
				match(T__16);
				setState(123);
				((VarStatementContext)_localctx).expr = expression(0);

				            String name = ((VarStatementContext)_localctx).NAME.getText();
				            checkVariable(name);
				            checkType(variables.get(name).type, ((VarStatementContext)_localctx).expr.type); //expexted Var type for expression
				            _localctx.ownInstructions.addAll(((VarStatementContext)_localctx).expr.ownInstructions);
				            _localctx.ownInstructions.add("istore #" + variables.get(name).index);
				            variables.get(name).initialized = true;
				        
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				((VarStatementContext)_localctx).NAME = match(NAME);
				setState(127);
				((VarStatementContext)_localctx).INT_EQ_OP = match(INT_EQ_OP);
				setState(128);
				((VarStatementContext)_localctx).expr = expression(0);

				            String name = ((VarStatementContext)_localctx).NAME.getText();
				            checkVariable(name);
				            Variable var = variables.get(name);
				            checkType(INT, ((VarStatementContext)_localctx).expr.type);
				            checkType(INT, var.type);
				            _localctx.ownInstructions.addAll(((VarStatementContext)_localctx).expr.ownInstructions);
				            _localctx.ownInstructions.add("iload #" + var.index);
				            switch (((VarStatementContext)_localctx).INT_EQ_OP.getText().charAt(0)) {
				                case '+':
				                    _localctx.ownInstructions.add("iadd");
				                    break;
				                case '-':
				                    _localctx.ownInstructions.add("isub");
				                    break;
				                case '*':
				                    _localctx.ownInstructions.add("imul");
				                    break;
				                case '/':
				                    _localctx.ownInstructions.add("idiv");
				                    break;
				                case '%':                //TODO
				                    break;
				                default:
				                    break;
				            }
				            _localctx.ownInstructions.add("istore #" + var.index);
				            variables.get(name).initialized = true;

				        
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(131);
				((VarStatementContext)_localctx).NAME = match(NAME);
				setState(132);
				((VarStatementContext)_localctx).INC_DEC = match(INC_DEC);

				            String name = ((VarStatementContext)_localctx).NAME.getText();   //TODO no repet
				            checkVariable(name, INT);
				            int byteConst = ((VarStatementContext)_localctx).INC_DEC.getText().charAt(0) == '+' ? 1 : -1;
				            _localctx.ownInstructions.add("iinc #" + variables.get(name).index + " " + byteConst);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public LinkedList<String> ownInstructions;
		public String type;
		public ExpressionContext first;
		public Token NAME;
		public Token INT_NUM;
		public ExpressionContext expr;
		public Token INT_OP;
		public ExpressionContext second;
		public Token BOOL_OP;
		public Token CMP_OP;
		public TerminalNode NAME() { return getToken(CompilerParser.NAME, 0); }
		public TerminalNode INT_NUM() { return getToken(CompilerParser.INT_NUM, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode INT_OP() { return getToken(CompilerParser.INT_OP, 0); }
		public TerminalNode BOOL_OP() { return getToken(CompilerParser.BOOL_OP, 0); }
		public TerminalNode CMP_OP() { return getToken(CompilerParser.CMP_OP, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CompilerListener ) ((CompilerListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_expression, _p);
		 ((ExpressionContext)_localctx).ownInstructions =  new LinkedList<>(); 
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(137);
				((ExpressionContext)_localctx).NAME = match(NAME);

				            String name = ((ExpressionContext)_localctx).NAME.getText();
				            checkVariable(name);
				            checkVarInitialized(name);
				            _localctx.ownInstructions.add("iload #" + variables.get(name).index);
				            ((ExpressionContext)_localctx).type =  variables.get(name).type;

				        
				}
				break;
			case INT_NUM:
				{
				setState(139);
				((ExpressionContext)_localctx).INT_NUM = match(INT_NUM);

				            int val = Integer.parseInt(((ExpressionContext)_localctx).INT_NUM.getText());
				            if (val < 6) {
				                _localctx.ownInstructions.add("iconst_" + val);    //tODO iconst_m1
				            } else {
				                int index = constants.indexOf(val) + 1;
				                if (index == 0) {
				                    constants.add(val);
				                    index = constants.size();
				                }
				                _localctx.ownInstructions.add("ldc #" + index);
				            }
				            ((ExpressionContext)_localctx).type =  INT;
				        
				}
				break;
			case T__21:
				{
				setState(141);
				match(T__21);

				            _localctx.ownInstructions.add("iconst_1");
				            ((ExpressionContext)_localctx).type =  BOOL;
				        
				}
				break;
			case T__22:
				{
				setState(143);
				match(T__22);

				            _localctx.ownInstructions.add("iconst_0");
				            ((ExpressionContext)_localctx).type =  BOOL;
				        
				}
				break;
			case T__23:
				{
				setState(145);
				match(T__23);
				setState(146);
				((ExpressionContext)_localctx).expr = expression(4);

				            checkType(BOOL, ((ExpressionContext)_localctx).expr.type);
				            _localctx.ownInstructions.addAll(((ExpressionContext)_localctx).expr.ownInstructions);
				            _localctx.ownInstructions.add("iconst_1");
				            _localctx.ownInstructions.add("ixor");
				        
				}
				break;
			case T__5:
				{
				setState(149);
				match(T__5);
				setState(150);
				((ExpressionContext)_localctx).expr = expression(0);
				setState(151);
				match(T__11);

				            ((ExpressionContext)_localctx).ownInstructions =  ((ExpressionContext)_localctx).expr.ownInstructions;
				            ((ExpressionContext)_localctx).type =  ((ExpressionContext)_localctx).expr.type;
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(173);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(171);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.first = _prevctx;
						_localctx.first = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(156);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(157);
						((ExpressionContext)_localctx).INT_OP = match(INT_OP);
						setState(158);
						((ExpressionContext)_localctx).second = expression(8);

						                      ((ExpressionContext)_localctx).ownInstructions =  new LinkedList<>();  //WHY?! how to optimize

						                      checkType(INT, ((ExpressionContext)_localctx).first.type);
						                      checkType(INT, ((ExpressionContext)_localctx).second.type);
						                      ((ExpressionContext)_localctx).type =  INT;
						                      _localctx.ownInstructions.addAll(((ExpressionContext)_localctx).second.ownInstructions);
						                      _localctx.ownInstructions.addAll(((ExpressionContext)_localctx).first.ownInstructions);
						                      switch (((ExpressionContext)_localctx).INT_OP.getText().charAt(0)) {
						                          case '+':
						                              _localctx.ownInstructions.add("iadd");
						                              break;
						                          case '-':
						                              _localctx.ownInstructions.add("isub");
						                              break;
						                          case '*':
						                              _localctx.ownInstructions.add("imul");
						                              break;
						                          case '/':
						                              _localctx.ownInstructions.add("idiv");
						                              break;
						                          case '%':                //TODO
						                              break;
						                          default:
						                              break;
						                      }
						                  
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.first = _prevctx;
						_localctx.first = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(161);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(162);
						((ExpressionContext)_localctx).BOOL_OP = match(BOOL_OP);
						setState(163);
						((ExpressionContext)_localctx).second = expression(4);

						                      ((ExpressionContext)_localctx).ownInstructions =  new LinkedList<>();

						                      checkType(BOOL, ((ExpressionContext)_localctx).first.type);
						                      checkType(BOOL, ((ExpressionContext)_localctx).second.type);
						                      _localctx.ownInstructions.addAll(((ExpressionContext)_localctx).first.ownInstructions);
						                      int jumpOverTheSecond = ((ExpressionContext)_localctx).second.ownInstructions.size() + 1;
						                      switch(((ExpressionContext)_localctx).BOOL_OP.getText().charAt(0)) {
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
						                      _localctx.ownInstructions.addAll(((ExpressionContext)_localctx).second.ownInstructions);
						                  
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.first = _prevctx;
						_localctx.first = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(166);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(167);
						((ExpressionContext)_localctx).CMP_OP = match(CMP_OP);
						setState(168);
						((ExpressionContext)_localctx).second = expression(3);

						                      ((ExpressionContext)_localctx).ownInstructions =  new LinkedList<>();

						                      checkType(INT, ((ExpressionContext)_localctx).first.type);
						                      checkType(INT, ((ExpressionContext)_localctx).second.type);
						                      ((ExpressionContext)_localctx).type =  BOOL;
						                      _localctx.ownInstructions.addAll(((ExpressionContext)_localctx).second.ownInstructions);
						                      _localctx.ownInstructions.addAll(((ExpressionContext)_localctx).first.ownInstructions);
						                      String op = ((ExpressionContext)_localctx).CMP_OP.getText();
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
						                      _localctx.ownInstructions.add("?" + op + " +3");
						                      _localctx.ownInstructions.add("iconst_0");
						                      _localctx.ownInstructions.add("?goto +2");
						                      _localctx.ownInstructions.add("iconst_1");
						                  
						}
						break;
					}
					} 
				}
				setState(175);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3%\u00b3\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\5\2\35\n\2\3\2\3\2\3\2\3\2\3\2\7\2$\n\2\f\2\16"+
		"\2\'\13\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3\64\n\3\f\3\16"+
		"\3\67\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4A\n\4\3\5\3\5\3\5\3\5\7"+
		"\5G\n\5\f\5\16\5J\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\5\5Y\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\5\5j\n\5\3\5\3\5\3\5\5\5o\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\5\5z\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0089"+
		"\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\5\7\u009d\n\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\7\7\u00ae\n\7\f\7\16\7\u00b1\13\7\3\7\2\3\f\b\2\4\6\b\n\f\2"+
		"\2\u00c4\2\16\3\2\2\2\4/\3\2\2\2\6:\3\2\2\2\by\3\2\2\2\n\u0088\3\2\2\2"+
		"\f\u009c\3\2\2\2\16\17\7\3\2\2\17\20\7\4\2\2\20\21\7!\2\2\21\22\7\5\2"+
		"\2\22\23\b\2\1\2\23\24\7\3\2\2\24\25\7\6\2\2\25\26\7\33\2\2\26\27\7\7"+
		"\2\2\27\30\7\b\2\2\30\34\7\t\2\2\31\32\7\n\2\2\32\35\7\13\2\2\33\35\7"+
		"\f\2\2\34\31\3\2\2\2\34\33\3\2\2\2\35\36\3\2\2\2\36\37\7\r\2\2\37 \7\16"+
		"\2\2 %\7\5\2\2!$\5\4\3\2\"$\5\b\5\2#!\3\2\2\2#\"\3\2\2\2$\'\3\2\2\2%#"+
		"\3\2\2\2%&\3\2\2\2&(\3\2\2\2\'%\3\2\2\2()\7\17\2\2)*\7!\2\2*+\7\20\2\2"+
		"+,\7\21\2\2,-\7\21\2\2-.\b\2\1\2.\3\3\2\2\2/\60\7\33\2\2\60\65\5\6\4\2"+
		"\61\62\7\22\2\2\62\64\5\6\4\2\63\61\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2"+
		"\2\65\66\3\2\2\2\668\3\2\2\2\67\65\3\2\2\289\7\20\2\29\5\3\2\2\2:;\7!"+
		"\2\2;@\b\4\1\2<=\7\23\2\2=>\5\f\7\2>?\b\4\1\2?A\3\2\2\2@<\3\2\2\2@A\3"+
		"\2\2\2A\7\3\2\2\2BC\b\5\1\2CH\7\5\2\2DG\5\b\5\2EG\5\4\3\2FD\3\2\2\2FE"+
		"\3\2\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2JH\3\2\2\2KL\7\21\2\2"+
		"Lz\b\5\1\2MN\7\24\2\2NO\7\b\2\2OP\5\f\7\2PQ\7\16\2\2QR\b\5\1\2RS\5\b\5"+
		"\2SX\b\5\1\2TU\7\25\2\2UV\5\b\5\2VW\b\5\1\2WY\3\2\2\2XT\3\2\2\2XY\3\2"+
		"\2\2YZ\3\2\2\2Z[\b\5\1\2[z\3\2\2\2\\]\7\26\2\2]^\7\b\2\2^_\5\f\7\2_`\7"+
		"\16\2\2`a\b\5\1\2ab\5\b\5\2bc\b\5\1\2cz\3\2\2\2de\b\5\1\2ef\7\27\2\2f"+
		"i\7\b\2\2gj\5\4\3\2hj\7\20\2\2ig\3\2\2\2ih\3\2\2\2jk\3\2\2\2kl\5\f\7\2"+
		"ln\7\20\2\2mo\5\n\6\2nm\3\2\2\2no\3\2\2\2op\3\2\2\2pq\7\16\2\2qr\b\5\1"+
		"\2rs\5\b\5\2st\b\5\1\2tz\3\2\2\2uv\5\n\6\2vw\7\20\2\2wx\b\5\1\2xz\3\2"+
		"\2\2yB\3\2\2\2yM\3\2\2\2y\\\3\2\2\2yd\3\2\2\2yu\3\2\2\2z\t\3\2\2\2{|\7"+
		"!\2\2|}\7\23\2\2}~\5\f\7\2~\177\b\6\1\2\177\u0089\3\2\2\2\u0080\u0081"+
		"\7!\2\2\u0081\u0082\7\36\2\2\u0082\u0083\5\f\7\2\u0083\u0084\b\6\1\2\u0084"+
		"\u0089\3\2\2\2\u0085\u0086\7!\2\2\u0086\u0087\7\34\2\2\u0087\u0089\b\6"+
		"\1\2\u0088{\3\2\2\2\u0088\u0080\3\2\2\2\u0088\u0085\3\2\2\2\u0089\13\3"+
		"\2\2\2\u008a\u008b\b\7\1\2\u008b\u008c\7!\2\2\u008c\u009d\b\7\1\2\u008d"+
		"\u008e\7\"\2\2\u008e\u009d\b\7\1\2\u008f\u0090\7\30\2\2\u0090\u009d\b"+
		"\7\1\2\u0091\u0092\7\31\2\2\u0092\u009d\b\7\1\2\u0093\u0094\7\32\2\2\u0094"+
		"\u0095\5\f\7\6\u0095\u0096\b\7\1\2\u0096\u009d\3\2\2\2\u0097\u0098\7\b"+
		"\2\2\u0098\u0099\5\f\7\2\u0099\u009a\7\16\2\2\u009a\u009b\b\7\1\2\u009b"+
		"\u009d\3\2\2\2\u009c\u008a\3\2\2\2\u009c\u008d\3\2\2\2\u009c\u008f\3\2"+
		"\2\2\u009c\u0091\3\2\2\2\u009c\u0093\3\2\2\2\u009c\u0097\3\2\2\2\u009d"+
		"\u00af\3\2\2\2\u009e\u009f\f\t\2\2\u009f\u00a0\7\35\2\2\u00a0\u00a1\5"+
		"\f\7\n\u00a1\u00a2\b\7\1\2\u00a2\u00ae\3\2\2\2\u00a3\u00a4\f\5\2\2\u00a4"+
		"\u00a5\7 \2\2\u00a5\u00a6\5\f\7\6\u00a6\u00a7\b\7\1\2\u00a7\u00ae\3\2"+
		"\2\2\u00a8\u00a9\f\4\2\2\u00a9\u00aa\7\37\2\2\u00aa\u00ab\5\f\7\5\u00ab"+
		"\u00ac\b\7\1\2\u00ac\u00ae\3\2\2\2\u00ad\u009e\3\2\2\2\u00ad\u00a3\3\2"+
		"\2\2\u00ad\u00a8\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\r\3\2\2\2\u00b1\u00af\3\2\2\2\21\34#%\65@FHXiny\u0088"+
		"\u009c\u00ad\u00af";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}