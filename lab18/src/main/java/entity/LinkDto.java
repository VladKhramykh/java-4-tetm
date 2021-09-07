package entity;

import lombok.Data;

@Data
public class LinkDto {
    int id;
    String url;
    String description;
    int minus;
    int plus;
    String role;
}
