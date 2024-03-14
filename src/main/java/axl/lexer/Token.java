package axl.lexer;

import java.util.HashMap;

import static axl.lexer.Token.Type.*;

public class Token {

    private String value_string;
    private int value_int;
    private char value_char;
    private long value_long;
    private float value_float;
    private double value_double;
    private Keyword keyword;

    private final Type type;

    Position position;

    public Token(Type type) {
        this.type = type;
    }

    public Token(String value, Type type) {
        this.type = type;
        this.value_string = value;
    }

    public Token(int value) {
        this.type = Type.INT;
        this.value_int = value;
    }

    public Token(long value) {
        this.type = Type.LONG;
        this.value_long = value;
    }

    public Token(float value) {
        this.type = Type.FLOAT;
        this.value_float = value;
    }

    public Token(double value) {
        this.type = Type.DOUBLE;
        this.value_double = value;
    }

    public Token(char value) {
        this.type = Type.CHAR;
        this.value_char = value;
    }

    public Token(Keyword value) {
        this.type = Type.KEYWORD;
        this.keyword = value;
    }

    public String getValueString() {
        return value_string;
    }

    public int getValueInt() {
        return value_int;
    }

    public long getValueLong() {
        return value_long;
    }

    public float getValueFloat() {
        return value_float;
    }

    public double getValueDouble() {
        return value_double;
    }

    public char getValueChar() {
        return value_char;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public Type getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public static final HashMap<String, Type> operators = new HashMap<>() {{
        put("=",    EQUAL);
        put("+",    PLUS);
        put("++",   DPLUS);
        put("-",    MINUS);
        put("--",   DMINUS);
        put("*",    STAR);
        put("/",    SLASH);
        put("%",    MODULO);
        put(">",    GREATER);
        put(">=",   GREATER_OR_EQUAL);
        put("<",    LESS);
        put("<=",   LESS_OR_EQUAL);
        put("==",   EQUAL_TO);
        put("!=",   NOT_EQUAL_TO);
        put("&&",   AND);
        put("||",   OR);
        put("!",    NOT);
        put("&",    BITWISE_AND);
        put("|",    BITWISE_OR);
        put("^",    BITWISE_XOR);
        put("~",    BITWISE_NOT);
        put("<<",   LEFT_SHIFT);
        put(">>",   RIGHT_SHIFT);
        put("?",    TERNARY1);
        put(":",    TERNARY2);
        put("(",    LPAR);
        put(")",    RPAR);
        put("{",    LBRACE);
        put("}",    RBRACE);
        put("[",    LBRACKET);
        put("]",    RBRACKET);
        put(".",    DOT);
        put(",",    COMMA);
        put(";",    SEMI);
        put("@",    AT);
    }};

    public static final HashMap<String, Keyword> keywords = new HashMap<>() {{
        put("class",          Keyword.CLASS);
        put("this",           Keyword.THIS);
        put("package",        Keyword.PACKAGE);
        put("import",         Keyword.IMPORT);
        put("as",             Keyword.AS);
        put("fn",             Keyword.FUNCTION);
        put("boolean",        Keyword.BOOL);
        put("bool",           Keyword.BOOL);
        put("byte",           Keyword.BYTE);
        put("i8",             Keyword.BYTE);
        put("char",           Keyword.CHAR);
        put("short",          Keyword.SHORT);
        put("i16",            Keyword.SHORT);
        put("int",            Keyword.INT);
        put("i32",            Keyword.INT);
        put("long",           Keyword.LONG);
        put("i64",            Keyword.LONG);
        put("float",          Keyword.FLOAT);
        put("f32",            Keyword.FLOAT);
        put("double",         Keyword.DOUBLE);
        put("f64",            Keyword.DOUBLE);
        put("void",           Keyword.VOID);
        put("if",             Keyword.IF);
        put("else",           Keyword.ELSE);
        put("switch",         Keyword.SWITCH);
        put("case",           Keyword.CASE);
        put("while",          Keyword.WHILE);
        put("for",            Keyword.FOR);
        put("goto",           Keyword.GOTO);
        put("try",            Keyword.TRY);
        put("catch",          Keyword.CATCH);
        put("finally",        Keyword.FINALLY);
        put("throws",         Keyword.THROWS);
        put("throw",          Keyword.THROW);
        put("instanceof",     Keyword.INSTANCEOF);
        put("true",           Keyword.TRUE);
        put("false",          Keyword.FALSE);
        put("null",           Keyword.NULL);
        put("public",         Keyword.PUBLIC);
        put("private",        Keyword.PRIVATE);
        put("static",         Keyword.STATIC);
        put("final",          Keyword.FINAL);
    }};

    public enum Keyword {
        CLASS,
        THIS,
        PACKAGE,
        IMPORT,
        AS,
        FUNCTION,
        BOOL,
        BYTE,
        CHAR,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        VOID,
        IF,
        ELSE,
        SWITCH,
        CASE,
        WHILE,
        FOR,
        GOTO,
        TRY,
        CATCH,
        FINALLY,
        THROWS,
        THROW,
        INSTANCEOF,
        TRUE,
        FALSE,
        NULL,
        PUBLIC,
        PRIVATE,
        STATIC,
        FINAL
    }

    public enum Type {
        EQUAL,                  // =
        PLUS,                   // +
        MINUS,                  // -
        DPLUS,                  // ++
        DMINUS,                 // --
        STAR,                   // *
        SLASH,                  // /
        MODULO,                 // %
        GREATER,                // >
        LESS,                   // <
        GREATER_OR_EQUAL,       // >=
        LESS_OR_EQUAL,          // <=
        EQUAL_TO,               // ==
        NOT_EQUAL_TO,           // !=
        AND,                    // &&
        OR,                     // ||
        NOT,                    // !
        BITWISE_AND,            // &
        BITWISE_OR,             // |
        BITWISE_XOR,            // ^
        BITWISE_NOT,            // ~
        LEFT_SHIFT,             // <<
        RIGHT_SHIFT,            // >>
        TERNARY1,               // ?
        TERNARY2,               // :
        LPAR,                   // (
        RPAR,                   // )
        LBRACE,                 // {
        RBRACE,                 // }
        LBRACKET,               // [
        RBRACKET,               // ]
        DOT,                    // .
        COMMA,                  // ,
        SEMI,                   // ;
        AT,                     // @
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        CHAR,
        STRING,
        KEYWORD,
        WORD
    }

    private static final String format = "%-16s: %-32s : %d:%d";

    public String getMessage() {
        return switch (type) {
            case INT -> format.formatted(type.name(), value_int, position.getRow(), position.getCol());
            case LONG -> format.formatted(type.name(), value_long, position.getRow(), position.getCol());
            case FLOAT -> format.formatted(type.name(), value_float, position.getRow(), position.getCol());
            case DOUBLE -> format.formatted(type.name(), value_double, position.getRow(), position.getCol());
            case WORD -> format.formatted(type.name(), value_string, position.getRow(), position.getCol());
            case STRING -> format.formatted(type.name(), value_string.replaceAll("\n", "\\n").replaceAll("\t", "\\t").replaceAll("\b", "\\b").replaceAll("\r", "\\r"), position.getRow(), position.getCol());
            case KEYWORD -> format.formatted(type.name(), keyword.name(), position.getRow(), position.getCol());
            default -> format.formatted(type.name(), "", position.getRow(), position.getCol());
        };
    }

}