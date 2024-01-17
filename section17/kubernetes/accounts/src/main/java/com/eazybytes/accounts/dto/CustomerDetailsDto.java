package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer,Accounts,Cards and Loans information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of customer", example = "Krishna Haridas"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min=5, max=30,message = "Customer name length should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of customer", example = "abc@gmail.com"
    )
    @Email(message = "Email address should be valid")
    @NotEmpty(message = "Email cannot be null or empty")
    private String email;

    @Schema(
            description = "Mobile Number of customer", example = "6478972619"
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be of length 10")
    private String mobileNumber;

    @Schema(
            description = "Account Details of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans Details of the customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Cards Details of the customer"
    )
    private CardsDto cardsDto;

}
