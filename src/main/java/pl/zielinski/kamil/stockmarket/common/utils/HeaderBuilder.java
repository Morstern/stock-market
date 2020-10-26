package pl.zielinski.kamil.stockmarket.common.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;


public class HeaderBuilder {
    private Integer ETag;
    private String Location;

    public HeaderBuilder setETag(Integer ETag) {
        this.ETag = ETag;
        return this;
    }

    public HeaderBuilder setLocation(String Location) {
        this.Location = Location;
        return this;
    }

    public MultiValueMap<String, String> build() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        if (ETag != null) {
            headers.add("ETag", String.valueOf(this.ETag));
        }
        if (Location != null) {
            headers.add("Location", this.Location);
        }
        return headers;
    }
}
