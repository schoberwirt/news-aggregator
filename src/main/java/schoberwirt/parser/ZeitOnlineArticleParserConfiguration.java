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
public class ZeitOnlineArticleParserConfiguration {

    @Bean
    public FeedEntryMessageSource zeitOnlineFeedEntryMessageSource() throws MalformedURLException {
        URL feedUrl = new URL("http://newsfeed.zeit.de/index");
        return new FeedEntryMessageSource(feedUrl, "");
    }

    @Bean
    public ArticleParser zeitOnlineArticleParser() throws MalformedURLException {

        List<String> filters = Lists.newArrayList();
        filters.add("zeit.de");
        filters.add("doubleclick");
        filters.add("?commentstart=");
        filters.add("?commentstart=");
        filters.add("#comments");


        final ArticleParser articleParser = new ArticleParser("http://newsfeed.zeit.de/index", ".article a", zeitOnlineFeedEntryMessageSource());
        articleParser.setAnalyzer(new FilteredArticleAnalyzer(filters));
        return articleParser;
    }

}
