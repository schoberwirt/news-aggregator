package schoberwirt.domain;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;

/**
 * @author phofmann
 * @since 19.07.15
 */
@Getter
@Setter
public class Author {

    private String name;

    private URI homepage;

    private URI twitter;

    private URI email;

}
