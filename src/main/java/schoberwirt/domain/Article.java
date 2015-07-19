package schoberwirt.domain;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

/**
 * @author phofmann
 * @since 19.07.15
 */
@Getter
@Setter
public class Article {

    private URI address;

    private List<URI> links;

    private Author author;

    private LocalTime createdAt;

}
