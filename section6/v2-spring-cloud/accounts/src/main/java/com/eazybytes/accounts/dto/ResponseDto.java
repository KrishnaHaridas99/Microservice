package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold Response from API"
)
public class ResponseDto {

    @Schema(
            description = "Status code of response", example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Description of status", example = "Successfully created"
    )
    private String statsMSg;
}
