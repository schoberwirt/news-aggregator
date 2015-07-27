package schoberwirt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import schoberwirt.analyzer.ArticleAnalyzer;
import schoberwirt.analyzer.FilteredArticleAnalyzer;
import schoberwirt.domain.Article;
import schoberwirt.parser.ArticleParser;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    List<ArticleParser> articleParsers;

	@Override
	public void run(String... args) throws MalformedURLException, URISyntaxException {

        ListMultimap<String, String> domainCount = ArrayListMultimap.create();

        articleParsers.forEach(articleParser -> {
            final List<Article> articlesWithLinks = articleParser.findArticles();
            domainCount.putAll(articleParser.analyze(articlesWithLinks));
        } );

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
