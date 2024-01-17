package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(
        name = "REST APIs for Customer  - EazyBank",
        description = "REST API to fetch customer details"
)
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@AllArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService iCustomerService;

    @Operation(
            summary = "Fetch Customer Details API",
            description = "REST API to fetch customer details from EazyBank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Data Fetched Successfully"
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("eazybank-correlation-id") String corelationId,
            @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be of length 10")
                                                                   @RequestParam String mobileNumber){
        logger.debug("Eazybank corelation Id found : {}" ,corelationId);
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber,corelationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
