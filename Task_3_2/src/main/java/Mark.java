public enum Mark {
    A(5),
    B(4),
    C(3),

    ACCEPTED(10),
    FAILED(11),
    NONE(12);
    private final int number;

    Mark(int number) {
        this.number = number;
    }

    public int getValue() {
        return this.number;
    }

    public boolean isHighest() {
        return this == A || this == ACCEPTED;
    }

    public boolean isDiff() {
        return this == A || this == B || this == C;
    }
}