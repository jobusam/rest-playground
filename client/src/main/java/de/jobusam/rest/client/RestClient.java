package de.jobusam.rest.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.jobusam.rest.model.Podcast;
import feign.Feign;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    public static void main(String[] args) {
        LOGGER.info("Execute Rest Calls!");
        // accessGithubApi();
        accessPodcastService();
    }

    private static Feign createClient() {
        ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return Feign.builder()
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .encoder(new JacksonEncoder(mapper))
                .decoder(new JacksonDecoder(mapper))
                .build();
    }

    private static void accessGithubApi() {
        GitHub github =
                createClient().newInstance(new Target.HardCodedTarget<>(
                        GitHub.class, "https://api.github.com"));

        github.contributors("jobusam", "rest-playground")
                .forEach(c -> LOGGER.info("Login = {}, contribs = {}", c.login, c.contributions));

    }

    private static void accessPodcastService() {
        PodcastService podcastService = createClient().newInstance(new Target.HardCodedTarget<>(
                PodcastService.class, "http://localhost:7000"));
        podcastService.getAllPodcasts().forEach(podcast -> LOGGER.info("Podcast: {}", podcast));
        // updateNumberOfEpisodes(podcastService);
        // updateWholePodcast(podcastService);
    }

    private static void updateNumberOfEpisodes(PodcastService podcastService) {
        podcastService.getAllPodcasts().stream().findAny().ifPresent(existingPodcast ->
                // send only modified name attribute (PATCH)
                podcastService.updatePodcastAttributes(existingPodcast.id(),
                        new Podcast(null, null, null, 380, null))
        );
    }

    private static void updateWholePodcast(PodcastService podcastService) {
        podcastService.getAllPodcasts().stream().findAny().ifPresent(existingPodcast ->
                // replace whole podcast, set amount and tags to null!
                podcastService.updatePodcast(existingPodcast.id(),
                        new Podcast(null, "Logbuch Netzpolitik - Updated", null, null, null))
        );
    }
}
