package de.jobusam.rest.service;

import de.jobusam.rest.model.Podcast;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class PodcastController implements CrudHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PodcastController.class.getName());

    private final Map<String, Podcast> podcasts = new HashMap<>();

    public PodcastController() {
        String uuid = UUID.randomUUID().toString();
        podcasts.put(uuid, new Podcast(uuid, "Logbuch Netzpolitik",
                "https://logbuch-netzpolitik.de/", 379,
                List.of("it-security", "netzpolitik")));
    }

    @Override
    public void create(@NotNull Context context) {
        Podcast p = context.bodyAsClass(Podcast.class);
        if (p.id() == null) {
            String uuid = UUID.randomUUID().toString();
            podcasts.put(uuid, new Podcast(uuid, p.name(), p.url(), p.numberOfEpisodes(), p.tags()));
        } else {
            LOGGER.warn("Can't create new podcast {}. Id is not null!", p);
            throw new BadRequestResponse("Attribute id must be null");
        }
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String podcastId) {
        if (podcasts.containsKey(podcastId)) {
            podcasts.remove(podcastId);
        } else {
            LOGGER.warn("There are no podcast with id {}", podcastId);
        }
    }

    @Override
    public void getAll(@NotNull Context context) {
        context.json(podcasts.values().stream().sorted(Comparator.comparing(Podcast::id)).collect(Collectors.toList()));
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String podcastId) {
        if (podcasts.containsKey(podcastId)) {
            context.json(podcasts.get(podcastId));
        } else {
            throw new NotFoundResponse();
        }
    }

    /**
     * Called as HTTP PATCH. Only update existing attributes!
     */
    @Override
    public void update(@NotNull Context context, @NotNull String podcastId) {
        if (podcasts.containsKey(podcastId)) {
            Podcast patch = context.bodyAsClass(Podcast.class);
            if (patch.id() != null) {
                throw new BadRequestResponse("Attribute id must be null");
            }
            Podcast original = podcasts.get(podcastId);
            String name = Optional.ofNullable(patch.name()).orElse(original.name());
            String url = Optional.ofNullable(patch.url()).orElse(original.url());
            Integer numberOfEpisodes = Optional.ofNullable(patch.numberOfEpisodes()).orElse(original.numberOfEpisodes());
            List<String> tags = Optional.ofNullable(patch.tags()).orElse(original.tags());
            podcasts.replace(original.id(), new Podcast(original.id(), name, url, numberOfEpisodes, tags));
        } else {
            throw new NotFoundResponse();
        }
    }

    /**
     * Called as HTTP PUT. Replace whole podcast!
     */
    public void updateWholePodcast(@NotNull Context context, @NotNull String podcastId) {
        if (podcasts.containsKey(podcastId)) {
            Podcast podcast = context.bodyAsClass(Podcast.class);
            if (podcast.id() != null) {
                throw new BadRequestResponse("Attribute id must be null");
            }
            podcasts.replace(podcastId, podcast);
        } else {
            throw new NotFoundResponse();
        }
    }
}
