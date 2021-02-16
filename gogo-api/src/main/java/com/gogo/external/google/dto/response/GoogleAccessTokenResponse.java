package com.gogo.external.google.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
public class GoogleAccessTokenResponse {

    private String accessToken;

    private String refreshToken;

    private String expiresIn;

    private String idToken;

    @Builder(builderMethodName = "testBuilder")
    public GoogleAccessTokenResponse(String accessToken, String refreshToken, String expiresIn, String idToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.idToken = idToken;
    }

}
