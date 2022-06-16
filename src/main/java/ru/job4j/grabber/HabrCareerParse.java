package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

    public class HabrCareerParse {
        private static final String SOURCE_LINK = "https://career.habr.com";
        private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);

        private String retrieveDescription(String link) throws IOException {
            Document document = Jsoup.connect(link).get();
            Elements element = document.select(".style-ugc");
            return element.text();
        }

        public static void main(String[] args) throws IOException {
            for (int i = 1; i <= 5; i++) {
                Connection connection = Jsoup.connect(PAGE_LINK + i);
                Document document = connection.get();
                Elements rows = document.select(".vacancy-card__inner");
                rows.forEach(row -> {
                    Element titleElement = row.select(".vacancy-card__title").first();
                    Element dateElement = row.select(".vacancy-card__date").first().child(0);
                    Element linkElement = titleElement.child(0);
                    String vacancyName = titleElement.text();
                    String vacancyDate = dateElement.attr("datetime");
                    String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                    System.out.printf("%s %s %s%n", vacancyName, vacancyDate, link);
                });
            }
        }
    }

