package pl.zielinski.kamil.stockmarket.common.utils;

public class URICreator {
    private String URL;
    private String id;

    public URICreator(String URL, String id) {
        this.URL = URL;
        this.id = id;
    }

    public URICreator(String URL, Long id) {
        this.URL = URL;
        this.id = String.valueOf(id);
    }

    public String getURI() {
        return URL + "/" + id;
    }
}
