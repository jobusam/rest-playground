package de.jobusam.rest.service;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

import static io.javalin.apibuilder.ApiBuilder.crud;

public class PodcastService {
    public static void main(String[] args) {
        Javalin app = Javalin
                .create(JavalinConfig::enableDevLogging)
                .start(7000);

        // Crud-Controller only supports PATCH requests
        // Put requests must be implemented as extra path
        PodcastController pc = new PodcastController();
        app.routes(() -> crud("podcasts/:id", pc));
        app.put("podcasts/:id", ctx -> pc.updateWholePodcast(ctx, ctx.pathParam("id")));
    }
}