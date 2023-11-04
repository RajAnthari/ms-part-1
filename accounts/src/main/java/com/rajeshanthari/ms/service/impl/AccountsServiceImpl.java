package com.rajeshanthari.ms.service.impl;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.rajeshanthari.ms.constants.AccountsConstants;
import com.rajeshanthari.ms.domain.Accounts;
import com.rajeshanthari.ms.domain.Customer;
import com.rajeshanthari.ms.dto.AccountsDto;
import com.rajeshanthari.ms.dto.AccountsMessageDto;
import com.rajeshanthari.ms.dto.CustomerDto;
import com.rajeshanthari.ms.exception.CustomerAlreadyExistsException;
import com.rajeshanthari.ms.exception.ResourceNotFoundException;
import com.rajeshanthari.ms.mapper.AccountsMapper;
import com.rajeshanthari.ms.mapper.CustomerMapper;
import com.rajeshanthari.ms.repository.AccountsRepository;
import com.rajeshanthari.ms.repository.CustomerRepository;
import com.rajeshanthari.ms.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	private StreamBridge streamBridge;

	private static final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

	@Override
	public void createAccount(CustomerDto customerDto) {
		Optional<Customer> exists = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if (exists.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already exists with the given mobile number " + customerDto.getMobileNumber());
		}
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Customer savedCustomer = customerRepository.save(customer);
		Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
		sendCommunication(savedAccount, savedCustomer);
	}

	private void sendCommunication(Accounts account, Customer customer) {
		var accountsMsgDto = new AccountsMessageDto(account.getAccountNumber(), customer.getName(), customer.getEmail(),
				customer.getMobileNumber());
		log.info("Sending Communication request for the details: {}", accountsMsgDto);
		var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
		log.info("Is the Communication request successfully triggered ? : {}", result);
	}

	/**
	 * @param customer - Customer Object
	 * @return the new account details
	 */
	private Accounts createNewAccount(Customer customer) {
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

		newAccount.setAccountNumber(randomAccNumber);
		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddress(AccountsConstants.ADDRESS);
		return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
		AccountsDto accountsDto = customerDto.getAccountsDto();
		if (accountsDto != null) {
			Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber",
							accountsDto.getAccountNumber().toString()));
			AccountsMapper.mapToAccounts(accountsDto, accounts);
			accounts = accountsRepository.save(accounts);

			Long customerId = accounts.getCustomerId();
			Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	/**
	 * @param mobileNumber - Input Mobile Number
	 * @return boolean indicating if the delete of Account details is successful or
	 *         not
	 */
	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}
	

    /**
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber !=null ){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return  isUpdated;
    }
}
