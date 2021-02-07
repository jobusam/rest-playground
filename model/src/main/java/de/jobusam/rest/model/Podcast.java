package de.jobusam.rest.model;

import java.util.List;

/**
 * Shared data model
 */
public record Podcast(String id, String name, String url, Integer numberOfEpisodes, List<String> tags){}