package entity;

import lombok.Data;

@Data
public class Link {
    int id;
    String url;
    String description;
    int minus;
    int plus;

    public Link() {
    }

    public Link(int id, String url, String description, int minus, int plus) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.minus = minus;
        this.plus = plus;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public int getMinus() {
        return minus;
    }

    public int getPlus() {
        return plus;
    }
}
