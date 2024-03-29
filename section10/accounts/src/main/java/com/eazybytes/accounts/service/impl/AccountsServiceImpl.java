package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExist;
import com.eazybytes.accounts.exception.ResourceNotFound;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service @AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    //@Autowired - Not Mandatory as there is only one constructor in this class.
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        //Checking if mobile number already exist before creating account
        Optional<Customer> customerExist = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(customerExist.isPresent())
        {
            throw new CustomerAlreadyExist("Customer already exist with the mobile number : " +customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(createNewAccount(savedCustomer));

    }
    public Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());

        long accountNumber = 1000000000L +new Random().nextInt(900000000);
        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).
                orElseThrow(() -> new ResourceNotFound("Customer","Mobile Number", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).
                orElseThrow(() -> new ResourceNotFound("Account","Customer Id", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null)
        {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).
                    orElseThrow(() -> new ResourceNotFound("Account","Account Number",accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).
                    orElseThrow(() -> new ResourceNotFound("Customer","Customer Id",customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).
                orElseThrow(() -> new ResourceNotFound("Customer","mobile Number", mobileNumber));
        if(customer != null)
        {
            accountsRepository.deleteByCustomerId(customer.getCustomerId());
            customerRepository.deleteById(customer.getCustomerId());
            isDeleted = true;
        }
        return isDeleted;
    }
}
