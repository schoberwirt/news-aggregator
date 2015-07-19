package schoberwirt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import schoberwirt.analyzer.Analyzer;
import schoberwirt.analyzer.FilteredArticleAnalyzer;
import schoberwirt.domain.Article;
import schoberwirt.parser.ArticleParser;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    ArticleParser zeitOnlineArticleParser;

	@Override
	public void run(String... args) throws MalformedURLException, URISyntaxException {
        List<String> filters = Lists.newArrayList();
        filters.add("zeit.de");
        filters.add("doubleclick");
        filters.add("?commentstart=");
        filters.add("?commentstart=");
        filters.add("#comments");

        ListMultimap<String, String> domainCount = ArrayListMultimap.create();

        final List<Article> articlesWithLinks = zeitOnlineArticleParser.findArticles();

        Analyzer analyzer = new FilteredArticleAnalyzer(filters);

        analyzer.analyze(articlesWithLinks).forEach(article -> {
            article.getLinks().forEach(uri -> {

                domainCount.put(uri.getHost(), uri.toString());
            });
        });

        domainCount.keySet().forEach(s -> {
            final List<String> strings = domainCount.get(s);
            System.out.println("### " + s + " Links: " + strings.size());
            strings.forEach(System.out::println);
        });


    }


    public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
