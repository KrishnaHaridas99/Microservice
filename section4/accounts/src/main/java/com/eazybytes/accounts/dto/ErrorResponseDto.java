package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold Error response from API"
)
public class ErrorResponseDto {

    @Schema(
            description = "Api path"
    )
    private String apiPath;

    @Schema(
            description = "Error code "
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message description"
    )
    private String errorMessage;

    @Schema(
            description = "Error Time"
    )
    private LocalDateTime errorTime;


}
