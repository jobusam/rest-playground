package de.jobusam.rest.client;

import de.jobusam.rest.model.Podcast;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface PodcastService {

    // get all
    @RequestLine("GET /podcasts")
    List<Podcast> getAllPodcasts();

    // get one
    @RequestLine("GET /podcasts/{id}")
    Podcast getPodcast(@Param("id") String id);

    // create
    @RequestLine("POST /podcasts")
    void createPodcast(Podcast p);

    // delete
    @RequestLine("DELETE /podcasts/{id}")
    void deletePodcast(@Param("id") String id);

    // Only update podcast attributes that are explicitly set
    @RequestLine("PATCH /podcasts/{id}")
    void updatePodcastAttributes(@Param("id") String id, Podcast p);

    // Update whole podcast
    // skipped values must be set to null
    @RequestLine("PUT /podcasts/{id}")
    void updatePodcast(@Param("id") String id, Podcast p);
}
