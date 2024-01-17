package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for Accounts - EazyBank",
        description = "CRUD operation on Accounts in Eazy Bank"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {
    private IAccountsService iAccountsService;
    @Autowired
    public AccountsController (IAccountsService iAccountsService){
        this.iAccountsService =iAccountsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    //API Documentation Annotations-----------------------------------------------------------
    @Operation(
            summary = "Create Account API",
            description = "REST API to create account in EazyBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status Created"
    )
    //API Documentation Annotations-----------------------------------------------------------
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto)
    {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }
//--------------------------------------------------------------------------------------------------------------------
    //API Documentation Annotations-----------------------------------------------------------
    @Operation(
            summary = "Fetch Account Details API",
            description = "REST API to fetch account details from EazyBank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Data Fetched Successfully"
    )
    //API Documentation Annotations-----------------------------------------------------------
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be of length 10")
            @RequestParam String mobileNumber)
    {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }
//--------------------------------------------------------------------------------------------------------------------
   //API Documentation Annotations-----------------------------------------------------------
    @Operation(
            summary = "UPDATE Account API",
            description = "REST API to update account details in EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Data updated successfully"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Updation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )

            )
    }
    )
    //API Documentation Annotations-----------------------------------------------------------
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto)
    {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated)
        {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
                    body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }
//--------------------------------------------------------------------------------------------------------------------
    //API Documentation Annotations-----------------------------------------------------------
    @Operation(
            summary = "Delete Account API",
            description = "REST API to delete account from EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Data deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Updation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )

            )
    }
    )
    //API Documentation Annotations-----------------------------------------------------------
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be of lenth 10")
            @RequestParam String mobileNumber)
    {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted)
        {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
                    body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }
//--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/buildVersion")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/javaVersion")
    public ResponseEntity<String> getJavaVersion(){
        return  ResponseEntity.status(HttpStatus.OK).
                body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contactInfo")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).
                body(accountsContactInfoDto);
    }
}
