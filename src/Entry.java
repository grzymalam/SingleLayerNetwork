public class Entry {
    private String language;
    private Vector vector;

    public Entry(String language, Vector vector) {
        this.language = language;
        this.vector = vector;
    }

    public String getLanguage() {
        return language;
    }

    public Vector getVector() {
        return vector;
    }
}
