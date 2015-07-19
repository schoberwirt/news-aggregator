package schoberwirt.parser;

import org.springframework.integration.feed.inbound.FeedEntryMessageSource;

import java.net.MalformedURLException;

/**
 * @author phofmann
 * @since 19.07.15
 */
public class ArticleParser extends AbstractArticleParser{
    public ArticleParser(String feedUrl, String articleCssSelector, FeedEntryMessageSource feedEntryMessageSource) throws MalformedURLException {
        super(feedUrl, articleCssSelector, feedEntryMessageSource);
    }
}
