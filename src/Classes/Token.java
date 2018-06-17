package Classes;

public class Token {
    public State  type;
    public int    index;
    public int    level;
    public String text;

    public Token(State type, int index, int level, String text) {
        this.type  = type;
        this.index = index;
        this.level = level;
        this.text  = text;
    }

    public Token() {
        this.type  = State.Nothing;
        this.index = 0;
        this.level = 0;
        this.text  = "";
    }

    public String toString() {
        return this.text;
    }
}
