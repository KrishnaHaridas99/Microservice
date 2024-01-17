package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Accounts extends BaseEntity{

    private Long customerId;

    @Id
    private Long accountNumber;

    private String accountType;

    private String branchAddress;

    private boolean communicationSw;

}
