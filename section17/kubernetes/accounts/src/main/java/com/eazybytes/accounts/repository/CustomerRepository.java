package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    //Finding customer with mobileNumber being passed
    Optional<Customer> findByMobileNumber(String mobileNumber);

    //Finding customer with mobileNumber and email being passed
    //Optional<Customer> findByMobileNumberAndEmail(String mobileNumber,String email);
}
