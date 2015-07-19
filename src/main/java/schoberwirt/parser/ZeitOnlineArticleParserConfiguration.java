package schoberwirt.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;

import java.net.MalformedURLException;
import java.net.URL;

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
        return new ArticleParser("http://newsfeed.zeit.de/index", ".article a", zeitOnlineFeedEntryMessageSource());
    }

}
