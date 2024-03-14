package axl.lexer;

import axl.Options;

import java.util.ArrayList;

public class Lexer implements Options {

    private final String[] lines;
    private final String filename;

    private final char[] content;
    private int offset;

    private int row;
    private int column;
    private Position position;

    private boolean end = false;

    private Token last;

    public Lexer(String value, String filename) {
        this.content = value.toCharArray();
        this.lines = value.split("\n");
        this.filename = filename;
    }

    private char next() {
        if(offset+1 == content.length)
            end = true;
        if(offset+1 != content.length) {
            if(get() == '\n') {
                row++;
                column = 1;
            } else
                column++;

            end = false;
            return content[offset++];
        }
        return get();
    }

    private char get() {
        return content[offset];
    }

    private Token positioning(Token token) {
        token.position = position;
        last = token;
        return token;
    }

    private boolean isNotEnd() {
        return !end;
    }

    private boolean isEnd() {
        return end;
    }

    private Position getPosition() {
        return new Position(row, column, lines[row-1], filename);
    }

    public ArrayList<Token> tokenize() throws LexerException {
        this.offset = 0;
        this.row = 1;
        this.column = 1;
        this.end = false;
        this.last = null;
        this.position = getPosition();

        ArrayList<Token> tokens = new ArrayList<>();

        while (isNotEnd()) { // main process
            label:
            {
                // skip whitespace and other; exit if end
                if (get() == ' ' || get() == '\n' || get() == '\r' || get() == '\t') {
                    skip();
                    if (isEnd())
                        break label;
                }

                this.position = getPosition();

                if ((get() >= '0' && '9' >= get()) || get() == '.' || get() == '-') {
                    tokens.add(positioning(number()));
                    continue;
                }

                if (Character.isDigit(get()) || Character.isLetter(get()) || get() == '_') {
                    tokens.add(positioning(word())); // and keywords
                    continue;
                }

                if (get() == '"') {
                    tokens.add(positioning(string()));
                    break label;
                }

                if (get() == '\'') {
                    tokens.add(positioning(character()));
                    break label;
                }

                if (get() == '/') {
                    if (offset + 1 != content.length)
                        if (content[offset + 1] == '/') {
                            comment_uno();
                            next();
                            break label;
                        } else if (content[offset + 1] == '*') {
                            comment_multi();
                            next();
                            break label;
                        }
                }

                tokens.add(positioning(op()));
                continue;
            }
            next();
        }

        return tokens;
    }

    private void skip() {
        next();
        while (isNotEnd() && (get() == ' ' || get() == '\n' || get() == '\r' || get() == '\t'))
            next();
    }

    private Token number() throws LexerException {
        Token.Type type;
        if (get() == '.')
            type = Token.Type.FLOAT;
        else
            type = Token.Type.INT;

        if (get() == '-')
            if (last != null)
                switch (last.getType()) {
                    case CHAR, INT, LONG, FLOAT, DOUBLE, WORD, STRING, RPAR, RBRACKET -> {
                        return op();
                    }
                }

        StringBuilder builder = new StringBuilder();

        do {
            if(isEnd())
                break;

            if(get() == '.') {
                if (type == Token.Type.DOUBLE)
                    throw new LexerException(getLanguageController().getPointInRealNumber(), position);
                else
                    type = Token.Type.DOUBLE;
            }

            builder.append(next());
        } while ((get() >= '0' && '9' >= get()) || get() == '.');

        String value = builder.toString();

        if(value.equals("-") || value.equals(".")) {
            offset--;
            column--;
            return op();
        }
        if(get() == 'f' || get() == 'F')
            return new Token(Float.parseFloat(value));
        if(get() == 'd' || get() == 'D')
            return new Token(Double.parseDouble(value));
        if(get() == 'l' || get() == 'L')
            if (type == Token.Type.DOUBLE)
                throw new LexerException(getLanguageController().getPostfixInRealNumber(), position);
            else
                return new Token(Long.parseLong(value));
        if(type == Token.Type.FLOAT)
            return new Token(Float.parseFloat(value));
        return new Token(Integer.parseInt(value));
    }

    private Token word() {
        StringBuilder builder = new StringBuilder();

        do {
            if(isEnd())
                break;

            builder.append(next());
        } while (Character.isDigit(get()) || Character.isLetter(get()) || get() == '_' || (get() >= '0' && '9' >= get()));

        String value = builder.toString();

        if(Token.keywords.containsKey(value))
            return new Token(Token.keywords.get(value));
        return new Token(value, Token.Type.WORD);
    }

    private Token string() throws LexerException {
        StringBuilder builder = new StringBuilder();
        next();
        for(;;) {
            if(isEnd() || get() == '\n')
                throw new LexerException(getLanguageController().getStringNotClosed(), position);

            if(get() == '\\') {
                next();
                builder.append(switch (next()) {
                    case '\\' -> '\\';
                    case '"' -> '"';
                    case 'n' -> '\n';
                    case 't' -> '\t';
                    case 'b' -> '\b';
                    case 'r' -> '\r';
                    default -> throw new LexerException(getLanguageController().getUnknownCharacterAfterSlash(), position);
                });
                continue;
            }

            if (get() == '"')
                break;
            builder.append(next());
        }

        String value = builder.toString();

        return new Token(value, Token.Type.STRING);
    }

    private Token character() throws LexerException {
        next();
        char value;
        if (isEnd() || get() == '\n')
            throw new LexerException(getLanguageController().getCharacterNotClosed(), position);
        if (get() == '\'')
            throw new LexerException(getLanguageController().getCharacterDoesNotMatter(), position);
        if (get() == '\\') {
            next();
            value = switch (next()) {
                case '\\' -> '\\';
                case '\'' -> '\'';
                case 'n' -> '\n';
                case 't' -> '\t';
                case 'b' -> '\b';
                case 'r' -> '\r';
                default -> throw new LexerException(getLanguageController().getUnknownCharacterAfterSlash(), position);
            };
        } else
            value = next();
        if (get() != '\'')
            throw new LexerException(getLanguageController().getCharacterNotClosed(), position);
        return new Token(value);
    }

    private Token op() throws LexerException {
        char first = next();
        char second = isNotEnd() ? get() : 0;
        if (isEnd()) {
            String value = String.valueOf(first);
            if(Token.operators.containsKey(value))
                return new Token(Token.operators.get(value));
            else
                throw new LexerException(getLanguageController().getUnknownCharacter(), position);
        } else {
            String value = String.valueOf(first)+second;
            if(Token.operators.containsKey(value)) {
                next();
                return new Token(Token.operators.get(value));
            } else {
                value = String.valueOf(first);
                if(Token.operators.containsKey(value))
                    return new Token(Token.operators.get(value));
                else
                    throw new LexerException(getLanguageController().getUnknownCharacter(), position);
            }
        }
    }

    private void comment_multi() throws LexerException {
        while (isNotEnd())
        {
            if(next() == '*')
                if(get() == '/')
                    break;
        }

        if (isEnd())
            throw new LexerException(getLanguageController().getCommentNotClosed(), position);
    }

    private void comment_uno() {
        while (isNotEnd() && !(get() == '\n' || get() == '\r'))
            next();
    }

}
