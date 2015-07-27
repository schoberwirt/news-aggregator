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
public class SpiegelOnlineArticleParserConfiguration {

    @Bean
    public FeedEntryMessageSource spiegelOnlineFeedEntryMessageSource() throws MalformedURLException {
        URL feedUrl = new URL("http://www.spiegel.de/schlagzeilen/index.rss");
        return new FeedEntryMessageSource(feedUrl, "");
    }

    @Bean
    public ArticleParser spiegelOnlineArticleParser() throws MalformedURLException {

        List<String> filters = Lists.newArrayList();
        filters.add("doubleclick");


        final ArticleParser articleParser = new ArticleParser("http://www.spiegel.de/schlagzeilen/index.rss", ".article-section", spiegelOnlineFeedEntryMessageSource());
        articleParser.setAnalyzer(new FilteredArticleAnalyzer(filters));
        return articleParser;
    }

}
