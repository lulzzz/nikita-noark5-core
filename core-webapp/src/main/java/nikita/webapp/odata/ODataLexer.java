// Generated from /home/tsodring/git/nikita-noark5-core/core-webapp/src/main/resources/odata/OData.g4 by ANTLR 4.7
package nikita.webapp.test.odata;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ODataLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, WHITESPACE = 16,
            EQ = 17, GT = 18, LT = 19, GE = 20, LE = 21, AND = 22, OR = 23, ASC = 24, DESC = 25, TOP = 26,
            SKIP_ = 27, COUNT = 28, ORDERBY = 29, WS = 30, DIGITS = 31, HEX = 32, STRING = 33, COLON = 34,
            SEPERATOR = 35;
    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
            "T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "WHITESPACE", "EQ",
            "GT", "LT", "GE", "LE", "AND", "OR", "ASC", "DESC", "TOP", "SKIP_", "COUNT",
            "ORDERBY", "WS", "DIGITS", "HEX", "STRING", "COLON", "SEPERATOR"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2%\u00f6\b\1\4\2\t" +
                    "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3" +
                    "\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7" +
                    "\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3" +
                    "\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3" +
                    "\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3" +
                    "\16\3\16\3\17\3\17\3\20\3\20\3\21\6\21\u009f\n\21\r\21\16\21\u00a0\3\21" +
                    "\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26" +
                    "\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32" +
                    "\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\35" +
                    "\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37" +
                    "\6\37\u00dc\n\37\r\37\16\37\u00dd\3 \6 \u00e1\n \r \16 \u00e2\3!\3!\3" +
                    "!\6!\u00e8\n!\r!\16!\u00e9\3\"\6\"\u00ed\n\"\r\"\16\"\u00ee\3#\3#\3$\3" +
                    "$\3$\3$\2\2%\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16" +
                    "\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34" +
                    "\67\359\36;\37= ?!A\"C#E$G%\3\2\6\5\2\13\f\17\17\"\"\3\2\62;\5\2\62;C" +
                    "Hch\6\2\62;C\\c|\u0080\u0080\2\u00fa\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2" +
                    "\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23" +
                    "\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2" +
                    "\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2" +
                    "\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3" +
                    "\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2" +
                    "\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\3I\3\2\2\2\5N\3\2\2\2\7T\3\2\2\2" +
                    "\tV\3\2\2\2\13X\3\2\2\2\ra\3\2\2\2\17j\3\2\2\2\21p\3\2\2\2\23w\3\2\2\2" +
                    "\25\u0081\3\2\2\2\27\u0083\3\2\2\2\31\u008c\3\2\2\2\33\u008e\3\2\2\2\35" +
                    "\u0099\3\2\2\2\37\u009b\3\2\2\2!\u009e\3\2\2\2#\u00a4\3\2\2\2%\u00a7\3" +
                    "\2\2\2\'\u00aa\3\2\2\2)\u00ad\3\2\2\2+\u00b0\3\2\2\2-\u00b3\3\2\2\2/\u00b7" +
                    "\3\2\2\2\61\u00ba\3\2\2\2\63\u00be\3\2\2\2\65\u00c3\3\2\2\2\67\u00c7\3" +
                    "\2\2\29\u00cc\3\2\2\2;\u00d2\3\2\2\2=\u00db\3\2\2\2?\u00e0\3\2\2\2A\u00e7" +
                    "\3\2\2\2C\u00ec\3\2\2\2E\u00f0\3\2\2\2G\u00f2\3\2\2\2IJ\7j\2\2JK\7v\2" +
                    "\2KL\7v\2\2LM\7r\2\2M\4\3\2\2\2NO\7j\2\2OP\7v\2\2PQ\7v\2\2QR\7r\2\2RS" +
                    "\7u\2\2S\6\3\2\2\2TU\7\61\2\2U\b\3\2\2\2VW\7A\2\2W\n\3\2\2\2XY\7&\2\2" +
                    "YZ\7h\2\2Z[\7k\2\2[\\\7n\2\2\\]\7v\2\2]^\7g\2\2^_\7t\2\2_`\7?\2\2`\f\3" +
                    "\2\2\2ab\7&\2\2bc\7u\2\2cd\7g\2\2de\7c\2\2ef\7t\2\2fg\7e\2\2gh\7j\2\2" +
                    "hi\7?\2\2i\16\3\2\2\2jk\7&\2\2kl\7v\2\2lm\7q\2\2mn\7r\2\2no\7?\2\2o\20" +
                    "\3\2\2\2pq\7&\2\2qr\7u\2\2rs\7m\2\2st\7k\2\2tu\7r\2\2uv\7?\2\2v\22\3\2" +
                    "\2\2wx\7&\2\2xy\7q\2\2yz\7t\2\2z{\7f\2\2{|\7g\2\2|}\7t\2\2}~\7d\2\2~\177" +
                    "\7{\2\2\177\u0080\7?\2\2\u0080\24\3\2\2\2\u0081\u0082\7)\2\2\u0082\26" +
                    "\3\2\2\2\u0083\u0084\7e\2\2\u0084\u0085\7q\2\2\u0085\u0086\7p\2\2\u0086" +
                    "\u0087\7v\2\2\u0087\u0088\7c\2\2\u0088\u0089\7k\2\2\u0089\u008a\7p\2\2" +
                    "\u008a\u008b\7u\2\2\u008b\30\3\2\2\2\u008c\u008d\7.\2\2\u008d\32\3\2\2" +
                    "\2\u008e\u008f\7u\2\2\u008f\u0090\7v\2\2\u0090\u0091\7c\2\2\u0091\u0092" +
                    "\7t\2\2\u0092\u0093\7v\2\2\u0093\u0094\7u\2\2\u0094\u0095\7Y\2\2\u0095" +
                    "\u0096\7k\2\2\u0096\u0097\7v\2\2\u0097\u0098\7j\2\2\u0098\34\3\2\2\2\u0099" +
                    "\u009a\7*\2\2\u009a\36\3\2\2\2\u009b\u009c\7+\2\2\u009c \3\2\2\2\u009d" +
                    "\u009f\t\2\2\2\u009e\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u009e\3\2" +
                    "\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a3\b\21\2\2\u00a3" +
                    "\"\3\2\2\2\u00a4\u00a5\7g\2\2\u00a5\u00a6\7s\2\2\u00a6$\3\2\2\2\u00a7" +
                    "\u00a8\7i\2\2\u00a8\u00a9\7v\2\2\u00a9&\3\2\2\2\u00aa\u00ab\7n\2\2\u00ab" +
                    "\u00ac\7v\2\2\u00ac(\3\2\2\2\u00ad\u00ae\7i\2\2\u00ae\u00af\7g\2\2\u00af" +
                    "*\3\2\2\2\u00b0\u00b1\7n\2\2\u00b1\u00b2\7g\2\2\u00b2,\3\2\2\2\u00b3\u00b4" +
                    "\7c\2\2\u00b4\u00b5\7p\2\2\u00b5\u00b6\7f\2\2\u00b6.\3\2\2\2\u00b7\u00b8" +
                    "\7q\2\2\u00b8\u00b9\7t\2\2\u00b9\60\3\2\2\2\u00ba\u00bb\7c\2\2\u00bb\u00bc" +
                    "\7u\2\2\u00bc\u00bd\7e\2\2\u00bd\62\3\2\2\2\u00be\u00bf\7f\2\2\u00bf\u00c0" +
                    "\7g\2\2\u00c0\u00c1\7u\2\2\u00c1\u00c2\7e\2\2\u00c2\64\3\2\2\2\u00c3\u00c4" +
                    "\7v\2\2\u00c4\u00c5\7q\2\2\u00c5\u00c6\7r\2\2\u00c6\66\3\2\2\2\u00c7\u00c8" +
                    "\7u\2\2\u00c8\u00c9\7m\2\2\u00c9\u00ca\7k\2\2\u00ca\u00cb\7r\2\2\u00cb" +
                    "8\3\2\2\2\u00cc\u00cd\7e\2\2\u00cd\u00ce\7q\2\2\u00ce\u00cf\7w\2\2\u00cf" +
                    "\u00d0\7p\2\2\u00d0\u00d1\7v\2\2\u00d1:\3\2\2\2\u00d2\u00d3\7q\2\2\u00d3" +
                    "\u00d4\7t\2\2\u00d4\u00d5\7f\2\2\u00d5\u00d6\7g\2\2\u00d6\u00d7\7t\2\2" +
                    "\u00d7\u00d8\7d\2\2\u00d8\u00d9\7{\2\2\u00d9<\3\2\2\2\u00da\u00dc\7\"" +
                    "\2\2\u00db\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd" +
                    "\u00de\3\2\2\2\u00de>\3\2\2\2\u00df\u00e1\t\3\2\2\u00e0\u00df\3\2\2\2" +
                    "\u00e1\u00e2\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3@\3" +
                    "\2\2\2\u00e4\u00e5\7\'\2\2\u00e5\u00e6\t\4\2\2\u00e6\u00e8\t\4\2\2\u00e7" +
                    "\u00e4\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2" +
                    "\2\2\u00eaB\3\2\2\2\u00eb\u00ed\t\5\2\2\u00ec\u00eb\3\2\2\2\u00ed\u00ee" +
                    "\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00efD\3\2\2\2\u00f0" +
                    "\u00f1\7<\2\2\u00f1F\3\2\2\2\u00f2\u00f3\7<\2\2\u00f3\u00f4\7\61\2\2\u00f4" +
                    "\u00f5\7\61\2\2\u00f5H\3\2\2\2\b\2\u00a0\u00dd\u00e2\u00e9\u00ee\3\b\2" +
                    "\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'http'", "'https'", "'/'", "'?'", "'$filter='", "'$search='", "'$top='",
            "'$skip='", "'$orderby='", "'''", "'contains'", "','", "'startsWith'",
            "'('", "')'", null, "'eq'", "'gt'", "'lt'", "'ge'", "'le'", "'and'", "'or'",
            "'asc'", "'desc'", "'top'", "'skip'", "'count'", "'orderby'", null, null,
            null, null, "':'", "'://'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, "WHITESPACE", "EQ", "GT", "LT", "GE", "LE", "AND",
            "OR", "ASC", "DESC", "TOP", "SKIP_", "COUNT", "ORDERBY", "WS", "DIGITS",
            "HEX", "STRING", "COLON", "SEPERATOR"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    static {
        RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION);
    }

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

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public ODataLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String getGrammarFileName() {
        return "OData.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}