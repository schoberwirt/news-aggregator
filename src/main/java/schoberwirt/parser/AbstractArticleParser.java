package schoberwirt.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.messaging.Message;
import schoberwirt.domain.Article;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author phofmann
 * @since 19.07.15
 */
@Getter
@Setter
public abstract class AbstractArticleParser {

    private final FeedEntryMessageSource feedEntryMessageSourceBeanReference;

    private final URL feed;

    private final String articleCssSelector;


    protected AbstractArticleParser(String feedUrl, String articleCssSelector, FeedEntryMessageSource feedEntryMessageSource) throws MalformedURLException {
        this.articleCssSelector = articleCssSelector;
        this.feed = new URL(feedUrl);
        this.feedEntryMessageSourceBeanReference = feedEntryMessageSource;
    }

    public List<Article> findArticles() throws URISyntaxException {

        List<Article> articles = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {
            // receive the message feed
            Message<SyndEntry> message = feedEntryMessageSourceBeanReference.receive();
            if (message != null) {
                SyndEntry entry = message.getPayload();
                // display
                //System.out.println(entry.getPublishedDate() + " - " + entry.getLink());
                Article article = new Article();
                article.setAddress(new URI(entry.getLink()));
                article.setLinks(parse(entry.getLink()));

                articles.add(article);
            }
        }

        return articles;
    }

    public List<URI> parse(String articleLink){

        List<URI> result = Lists.newArrayList();
        Document document = null;
        try {
            document = Jsoup.connect(articleLink).get();
            document.select(articleCssSelector).forEach(element -> {
                final URI link;
                try {
                    link = new URI(element.attr("href"));
                    result.add(link);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    };

}
