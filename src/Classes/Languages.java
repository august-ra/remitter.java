package Classes;

public enum Languages {
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
