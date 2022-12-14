package org.bank.banking.services;

import org.bank.bank.details.BankAccountDetails;

/*Important
create account
close account
getbankAccountDetails
* */
public interface BankAccountService
{

    BankAccountService createAccount(Long account_no,String account_name,String password,String username);

     BankAccountService getAccountType();

     BankAccountDetails getBankAccountDetails();

    boolean deposit(long amount);

    boolean withdrawBalance(long amount);

    long checkBalance();

    boolean closeAccount() ;

}
