package axl.lexer;

public class LexerException extends Exception {

    public final Position position;

    public LexerException(String message) {
        super(message);
        position = new Position(0, 0, null ,null);
    }

    public LexerException(String message, Position position) {
        super(message);

        this.position = position;
    }

    @Override
    public String toString() {
        if (position.getLine() == null)
            return "[ERROR]";

        String whitespaces = " ".repeat(String.valueOf(position.getRow()).length() + 1);
        String format = """
                %s[ERROR]: %s
                                
                %s╭ ⎯⎯⎯ %s:%d:%d ⎯⎯⎯⎯⎯
                %d │ %s
                %s│%s╰ %s""";
        return format.formatted(
                whitespaces, getMessage(),
                whitespaces, position.getFilename(), position.getRow(), position.getCol(),
                position.getRow(), position.getLine(),
                whitespaces, " ".repeat(position.getCol()), getMessage()
        );
    }

}
