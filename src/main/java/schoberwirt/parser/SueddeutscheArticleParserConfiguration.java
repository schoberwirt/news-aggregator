package schoberwirt.parser;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import schoberwirt.analyzer.FilteredArticleAnalyzer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author phofmann
 * @since 19.07.15
 */
@Configuration
public class SueddeutscheArticleParserConfiguration {

    @Bean
    public FeedEntryMessageSource sueddeutscheFeedEntryMessageSource() throws MalformedURLException {
        URL feedUrl = new URL("http://suche.sueddeutsche.de/?output=rss");
        return new FeedEntryMessageSource(feedUrl, "");
    }

    @Bean
    public ArticleParser sueddeutscheArticleParser() throws MalformedURLException {

        List<String> filters = Lists.newArrayList();
        filters.add("doubleclick");


        final ArticleParser articleParser = new ArticleParser("http://suche.sueddeutsche.de/?output=rss", "article.article", sueddeutscheFeedEntryMessageSource());
        articleParser.setAnalyzer(new FilteredArticleAnalyzer(filters));
        return articleParser;
    }

}
