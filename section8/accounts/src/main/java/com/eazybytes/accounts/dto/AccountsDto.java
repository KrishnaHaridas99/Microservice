package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Accounts information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of customer", example = "1000977654"
    )
    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number should be of length 10")
    private Long accountNumber;

    @Schema(
            description = "Account Type of customer", example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Branch Address of Account", example = "1234 North York"
    )
    @NotEmpty(message = "Branch Address cannot be null or empty")
    private String branchAddress;


}
