public enum Quality {
    AGAIN(0),   // total failure
    HARD(3),    // struggled
    GOOD(4),    // decent recall
    EASY(5);    // perfect recall

    private final int score;

    Quality(int score) {
        this.score = score;
    }

    public int getScore() { return score; }

    @Override
    public String toString() {
        return name() + "(" + score + ")";
    }
}
