/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package schoberwirt;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.messaging.Message;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class Application implements CommandLineRunner {

	// Simple example shows how a command line spring application can execute an
	// injected bean service. Also demonstrates how you can use @Value to inject
	// command line args ('--name=whatever') or application properties

	@Bean
	public FeedEntryMessageSource feedEntryMessageSource() throws MalformedURLException {
		URL feedUrl = new URL("http://newsfeed.zeit.de/index");
		return new FeedEntryMessageSource(feedUrl, "");
	}


	@Autowired
	FeedEntryMessageSource feedEntryMessageSource;

	@Override
	public void run(String... args) {


			for (int i = 0; i < 10; i++) {
				// receive the message feed
				Message<SyndEntry> message = feedEntryMessageSource.receive();
				if (message != null) {
					SyndEntry entry = message.getPayload();
					// display
					System.out.println(entry.getPublishedDate() + " - " + entry.getTitle());
				} else {
					break;
				}
			}

	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
