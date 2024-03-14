package axl.lexer;

import lombok.Getter;

@Getter
public class Position {

    private final int row;
    private final int col;
    private final String line;
    private final String filename;

    protected Position(Position pos) {
        this.row = pos.row;
        this.col = pos.col;
        this.line = pos.line;
        this.filename = pos.filename;
    }

    public Position(int row, int col, String line, String filename) {
        this.row = row;
        this.col = col;
        this.line = line;
        this.filename = filename;
    }

}
