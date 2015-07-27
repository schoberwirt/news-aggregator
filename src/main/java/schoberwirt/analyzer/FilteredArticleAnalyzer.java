package schoberwirt.analyzer;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import schoberwirt.domain.Article;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * @author phofmann
 * @since 19.07.15
 */
@Getter
@Setter
public class FilteredArticleAnalyzer implements ArticleAnalyzer {

    private final List<String> filters;

    public FilteredArticleAnalyzer(List<String> filters) {
        this.filters = filters;
    }

    @Override
    public List<Article> analyze(List<Article> articles) {

        List<Article> result = Lists.newArrayList();

        articles.forEach(article -> {
            article.setLinks(article.getLinks().stream().filter(this::keep).collect(toList()));
            result.add(article);
        });

        return articles;
    }

    private boolean keep(URI uri) {
        final String linkString = uri.toString();

        for(String filter : filters) {

            if(linkString.contains(filter)) {
                return false;
            }

        }

        return true;
    }

}
