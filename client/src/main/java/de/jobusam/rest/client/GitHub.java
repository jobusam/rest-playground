package de.jobusam.rest.client;

import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * Sample from Open Feign Website
 */
public interface GitHub {
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repository);

    class Contributor {
        public String login;
        public int contributions;
    }
}
