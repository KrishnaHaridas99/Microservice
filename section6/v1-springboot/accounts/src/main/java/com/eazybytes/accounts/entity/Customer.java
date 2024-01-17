package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
//Not mandatory if table name matches entity name
@Table(name = "customer")
public class Customer extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name ="native",strategy = "native")
    //Not mandatory as column name is matching the field name(exclude _ and next word with caps letter)
    @Column(name = "customer_id")
    private Long customerId;

    private String name;

    private String email;

    private String mobileNumber;

}
