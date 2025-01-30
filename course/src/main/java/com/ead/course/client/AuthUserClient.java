package com.ead.course.client;

import com.ead.course.dto.CourseUserDto;
import com.ead.course.dto.ResponsePageDto;
import com.ead.course.dto.UserRecordDto;
import com.ead.course.exception.NotFoundExcepetion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
public class AuthUserClient {

    private static final Logger LOGGER = LogManager.getLogger(AuthUserClient.class);

    private final RestClient restClient;

    @Value("${ead.api.url.authuser}")
    private String baseUrlAuthUser;

    public AuthUserClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Page<UserRecordDto> getAllUsersByCourse(Pageable pageable, UUID courseId) {

        String url = baseUrlAuthUser + "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replace(": ", ",");

        LOGGER.debug("Request URL: {}", url);

        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDto<UserRecordDto>>() {
                    });

        } catch (RestClientException e) {
            LOGGER.error("Error Request RestClient with cause: {}", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }

    }

    public ResponseEntity<UserRecordDto> getOneUserById(UUID userId) {

        String url = baseUrlAuthUser + "/users/" + userId;

        LOGGER.debug("Request URL: {}", url);

        return restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    LOGGER.error("Error: User not found: {}", userId);
                    throw new NotFoundExcepetion("Error: User not found.");
                }).toEntity(UserRecordDto.class);

    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {

        String url = baseUrlAuthUser + "/users/" + userId + "/courses/subscription";

        LOGGER.debug("Request POST URL: {}", url);

        try {
            var courseUserDto = new CourseUserDto(userId, courseId);
            restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(courseUserDto)
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientException e) {
            LOGGER.error("Error Request POST RestClient with cause: {}", e.getMessage());
            throw new RuntimeException("Error Request POST RestClient", e);
        }

    }

}
