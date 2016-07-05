// Generated from Compiler.g4 by ANTLR 4.5.3

package compiler;
import com.sun.corba.se.impl.io.TypeMismatchException;
import com.sun.org.apache.xml.internal.security.signature.ReferenceNotInitializedException; // ...

import java.util.*;


import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CompilerLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "TYPE", 
		"INC_DEC", "INT_OP", "INT_EQ_OP", "CMP_OP", "BOOL_OP", "NAME", "INT_NUM", 
		"BLOCK_COMMENTS", "LINE_COMMENTS", "WS"
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


	public CompilerLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Compiler.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2%\u010b\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3"+
		"\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3"+
		"\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3"+
		"\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\3\32\5\32\u00b5\n\32\3\33\3\33\3\33\3\33\5\33\u00bb"+
		"\n\33\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35"+
		"\u00c9\n\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u00d4\n"+
		"\36\3\37\3\37\3\37\3\37\5\37\u00da\n\37\3 \3 \7 \u00de\n \f \16 \u00e1"+
		"\13 \3!\3!\7!\u00e5\n!\f!\16!\u00e8\13!\3\"\3\"\3\"\3\"\7\"\u00ee\n\""+
		"\f\"\16\"\u00f1\13\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\7#\u00fc\n#\f#\16"+
		"#\u00ff\13#\3#\3#\3#\3#\3$\6$\u0106\n$\r$\16$\u0107\3$\3$\4\u00ef\u00fd"+
		"\2%\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%\3\2\7\6\2\'\',-//\61\61\4\2>>@@\4\2C\\c|\5\2\62;C\\"+
		"c|\5\2\13\f\17\17\"\"\u011a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\3I\3\2\2\2\5P\3\2\2\2\7V\3\2\2\2\tX\3\2"+
		"\2\2\13_\3\2\2\2\rd\3\2\2\2\17f\3\2\2\2\21m\3\2\2\2\23o\3\2\2\2\25q\3"+
		"\2\2\2\27u\3\2\2\2\31z\3\2\2\2\33|\3\2\2\2\35\u0083\3\2\2\2\37\u0085\3"+
		"\2\2\2!\u0087\3\2\2\2#\u0089\3\2\2\2%\u008b\3\2\2\2\'\u008e\3\2\2\2)\u0093"+
		"\3\2\2\2+\u0099\3\2\2\2-\u009d\3\2\2\2/\u00a2\3\2\2\2\61\u00a8\3\2\2\2"+
		"\63\u00b4\3\2\2\2\65\u00ba\3\2\2\2\67\u00bc\3\2\2\29\u00c8\3\2\2\2;\u00d3"+
		"\3\2\2\2=\u00d9\3\2\2\2?\u00db\3\2\2\2A\u00e2\3\2\2\2C\u00e9\3\2\2\2E"+
		"\u00f7\3\2\2\2G\u0105\3\2\2\2IJ\7r\2\2JK\7w\2\2KL\7d\2\2LM\7n\2\2MN\7"+
		"k\2\2NO\7e\2\2O\4\3\2\2\2PQ\7e\2\2QR\7n\2\2RS\7c\2\2ST\7u\2\2TU\7u\2\2"+
		"U\6\3\2\2\2VW\7}\2\2W\b\3\2\2\2XY\7u\2\2YZ\7v\2\2Z[\7c\2\2[\\\7v\2\2\\"+
		"]\7k\2\2]^\7e\2\2^\n\3\2\2\2_`\7o\2\2`a\7c\2\2ab\7k\2\2bc\7p\2\2c\f\3"+
		"\2\2\2de\7*\2\2e\16\3\2\2\2fg\7U\2\2gh\7v\2\2hi\7t\2\2ij\7k\2\2jk\7p\2"+
		"\2kl\7i\2\2l\20\3\2\2\2mn\7]\2\2n\22\3\2\2\2op\7_\2\2p\24\3\2\2\2qr\7"+
		"\60\2\2rs\7\60\2\2st\7\60\2\2t\26\3\2\2\2uv\7c\2\2vw\7t\2\2wx\7i\2\2x"+
		"y\7u\2\2y\30\3\2\2\2z{\7+\2\2{\32\3\2\2\2|}\7t\2\2}~\7g\2\2~\177\7v\2"+
		"\2\177\u0080\7w\2\2\u0080\u0081\7t\2\2\u0081\u0082\7p\2\2\u0082\34\3\2"+
		"\2\2\u0083\u0084\7=\2\2\u0084\36\3\2\2\2\u0085\u0086\7\177\2\2\u0086 "+
		"\3\2\2\2\u0087\u0088\7.\2\2\u0088\"\3\2\2\2\u0089\u008a\7?\2\2\u008a$"+
		"\3\2\2\2\u008b\u008c\7k\2\2\u008c\u008d\7h\2\2\u008d&\3\2\2\2\u008e\u008f"+
		"\7g\2\2\u008f\u0090\7n\2\2\u0090\u0091\7u\2\2\u0091\u0092\7g\2\2\u0092"+
		"(\3\2\2\2\u0093\u0094\7y\2\2\u0094\u0095\7j\2\2\u0095\u0096\7k\2\2\u0096"+
		"\u0097\7n\2\2\u0097\u0098\7g\2\2\u0098*\3\2\2\2\u0099\u009a\7h\2\2\u009a"+
		"\u009b\7q\2\2\u009b\u009c\7t\2\2\u009c,\3\2\2\2\u009d\u009e\7v\2\2\u009e"+
		"\u009f\7t\2\2\u009f\u00a0\7w\2\2\u00a0\u00a1\7g\2\2\u00a1.\3\2\2\2\u00a2"+
		"\u00a3\7h\2\2\u00a3\u00a4\7c\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7u\2\2"+
		"\u00a6\u00a7\7g\2\2\u00a7\60\3\2\2\2\u00a8\u00a9\7#\2\2\u00a9\62\3\2\2"+
		"\2\u00aa\u00ab\7k\2\2\u00ab\u00ac\7p\2\2\u00ac\u00b5\7v\2\2\u00ad\u00ae"+
		"\7d\2\2\u00ae\u00af\7q\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1\7n\2\2\u00b1"+
		"\u00b2\7g\2\2\u00b2\u00b3\7c\2\2\u00b3\u00b5\7p\2\2\u00b4\u00aa\3\2\2"+
		"\2\u00b4\u00ad\3\2\2\2\u00b5\64\3\2\2\2\u00b6\u00b7\7-\2\2\u00b7\u00bb"+
		"\7-\2\2\u00b8\u00b9\7/\2\2\u00b9\u00bb\7/\2\2\u00ba\u00b6\3\2\2\2\u00ba"+
		"\u00b8\3\2\2\2\u00bb\66\3\2\2\2\u00bc\u00bd\t\2\2\2\u00bd8\3\2\2\2\u00be"+
		"\u00bf\7-\2\2\u00bf\u00c9\7?\2\2\u00c0\u00c1\7/\2\2\u00c1\u00c9\7?\2\2"+
		"\u00c2\u00c3\7,\2\2\u00c3\u00c9\7?\2\2\u00c4\u00c5\7\61\2\2\u00c5\u00c9"+
		"\7?\2\2\u00c6\u00c7\7\'\2\2\u00c7\u00c9\7?\2\2\u00c8\u00be\3\2\2\2\u00c8"+
		"\u00c0\3\2\2\2\u00c8\u00c2\3\2\2\2\u00c8\u00c4\3\2\2\2\u00c8\u00c6\3\2"+
		"\2\2\u00c9:\3\2\2\2\u00ca\u00d4\t\3\2\2\u00cb\u00cc\7?\2\2\u00cc\u00d4"+
		"\7?\2\2\u00cd\u00ce\7#\2\2\u00ce\u00d4\7?\2\2\u00cf\u00d0\7@\2\2\u00d0"+
		"\u00d4\7?\2\2\u00d1\u00d2\7>\2\2\u00d2\u00d4\7?\2\2\u00d3\u00ca\3\2\2"+
		"\2\u00d3\u00cb\3\2\2\2\u00d3\u00cd\3\2\2\2\u00d3\u00cf\3\2\2\2\u00d3\u00d1"+
		"\3\2\2\2\u00d4<\3\2\2\2\u00d5\u00d6\7(\2\2\u00d6\u00da\7(\2\2\u00d7\u00d8"+
		"\7~\2\2\u00d8\u00da\7~\2\2\u00d9\u00d5\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da"+
		">\3\2\2\2\u00db\u00df\t\4\2\2\u00dc\u00de\t\5\2\2\u00dd\u00dc\3\2\2\2"+
		"\u00de\u00e1\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0@\3"+
		"\2\2\2\u00e1\u00df\3\2\2\2\u00e2\u00e6\4\62;\2\u00e3\u00e5\4\62;\2\u00e4"+
		"\u00e3\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2"+
		"\2\2\u00e7B\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00ea\7\61\2\2\u00ea\u00eb"+
		"\7,\2\2\u00eb\u00ef\3\2\2\2\u00ec\u00ee\13\2\2\2\u00ed\u00ec\3\2\2\2\u00ee"+
		"\u00f1\3\2\2\2\u00ef\u00f0\3\2\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f2\3\2"+
		"\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3\7,\2\2\u00f3\u00f4\7\61\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5\u00f6\b\"\2\2\u00f6D\3\2\2\2\u00f7\u00f8\7\61\2\2"+
		"\u00f8\u00f9\7\61\2\2\u00f9\u00fd\3\2\2\2\u00fa\u00fc\13\2\2\2\u00fb\u00fa"+
		"\3\2\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe"+
		"\u0100\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100\u0101\7\f\2\2\u0101\u0102\3\2"+
		"\2\2\u0102\u0103\b#\2\2\u0103F\3\2\2\2\u0104\u0106\t\6\2\2\u0105\u0104"+
		"\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108"+
		"\u0109\3\2\2\2\u0109\u010a\b$\2\2\u010aH\3\2\2\2\r\2\u00b4\u00ba\u00c8"+
		"\u00d3\u00d9\u00df\u00e6\u00ef\u00fd\u0107\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}