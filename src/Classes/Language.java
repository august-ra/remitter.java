package Classes;

public enum Language {
    Java,
    VBA;

    @Override
    public String toString() {
        if (this.equals(VBA))
            return "Visual Basic for Applications";
        else
            return super.toString();
    }
}
