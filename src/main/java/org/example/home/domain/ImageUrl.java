package org.example.home.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUrl {
    @Id
    private int id;

    @Pattern(regexp = "^[0-9]*$")
    private Integer parentId;

    private String text;

    private String nextText;

    private String url;

    private Boolean active;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
