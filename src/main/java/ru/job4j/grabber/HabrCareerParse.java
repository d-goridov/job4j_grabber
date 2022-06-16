package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {
    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private String retrieveDescription(String link) throws IOException {
        Document document = Jsoup.connect(link).get();
        Elements element = document.select(".style-ugc");
        return element.text();
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> list = new ArrayList<>();
        Document document = Jsoup.connect(link).get();
        Elements rows = document.select(".vacancy-card__inner");
        rows.forEach(row -> {
            Element titleElement = row.select(".vacancy-card__title").first();
            Element linkElement = titleElement.child(0);
            Element dateElement = row.select(".vacancy-card__date").first().child(0);
            String title = titleElement.text();
            String linkVacancy = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
            String description = "";
            try {
                description = retrieveDescription(linkVacancy);
            } catch (IOException e) {
                e.printStackTrace();
            }

            LocalDateTime dateTime = new HabrCareerDateTimeParser().parse(dateElement.attr("datetime"));
            Post post = new Post(title, linkVacancy, description, dateTime);
            list.add(post);
        });
        return list;
    }

    public static void main(String[] args) throws IOException {
        HabrCareerDateTimeParser dateParser = new HabrCareerDateTimeParser();
        HabrCareerParse careerParse = new HabrCareerParse(dateParser);
        for (Post post : careerParse.list(PAGE_LINK)) {
            System.out.println(post);
        }
    }
}

