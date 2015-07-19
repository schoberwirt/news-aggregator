package schoberwirt.analyzer;

import schoberwirt.domain.Article;
import schoberwirt.graph.LinkGraph;

import java.net.URI;
import java.util.List;

/**
 * @author phofmann
 * @since 19.07.15
 */
public interface Analyzer {

    List<Article> analyze(List<Article> urls);

}
