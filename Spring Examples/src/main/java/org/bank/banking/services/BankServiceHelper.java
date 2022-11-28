package org.bank.banking.services;

import org.bank.account.AccountType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/*
* create account, login to account, withdraw and deposit cash, delete account, check balance
* */

public class BankServiceHelper
{
    private final HashMap<String, BankAccountService> nameToAccounts=new HashMap<String, BankAccountService>();
    private final HashMap<Long,BankAccountService> idToAccounts=new HashMap<Long, BankAccountService>();

    /***
     * This method is use to create an account
     */
    public void createAccount(AccountType accountType,Scanner input)
    {
        BankServiceHelper bank=new BankServiceHelper();
        BankAccountService bankAccount;
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");

        //get the account holder name
        String name=bank.getNameForAccount();

        //checking weather a account exists with this name or not
        BankAccountService exists=bank.checkAccountExists(name);

        if(exists==null) // if a bank account with given name does not exist then create one
        {
            //Get the random account no
            Long accountno=bank.getAccountNo();

            // Ask user for password
            String password=bank.getPassword(name);
            //check all the things needed to create account
            if (name.length() > 0 && accountType != null && password!=null && accountno!=0)
            {
                    bankAccount=(BankAccountService) applicationContext.getBean("Account_Sa");
                    BankAccountService bankAccountService = bankAccount.createAccount(accountno, name.trim(), accountType,password);
                    nameToAccounts.put(name, bankAccountService);
                    idToAccounts.put(accountno,bankAccountService);
            }
            else {
                System.out.println("Enter a proper name");
            }
        }
        else
        {
            //exists.toString();
            System.out.println("Savings account already exists with "+ name+" this name");
            System.out.println("Do you want to login to this account\n1)yes\n2)No");
            int choice=input.nextInt();
            if(choice==1 || choice==2)
            {
                if(choice==1)
                {
                    System.out.println("Enter the password for this account "+name);
                    String pass=input.next();
                    loginIntoAccount(name,pass);
                }
            }
            else
            {
                System.out.println("Select the proper value");
            }
        }
    }

    /***
     * The method is used to get random numbers for bank account
     * @return bank account number
     */
    public long getAccountNo()
    {
        long number;
        Random random=new Random();
        number=(long) (100000000000000L + random.nextFloat() * 900000000000000L);
        while(!idToAccounts.containsKey(number))
        {
            return number;
        }
        return 0;
    }

    /***
     * To get the name to add in the account details
     * @return Name of the account holder
     */
    public String getNameForAccount()
    {
        System.out.println("Enter the name for your account :- ");
        Scanner input=new Scanner(System.in);
        String name =input.next();
        if(name.length()>0)
        {
            return name;
        }
        else{
            System.out.println("Please enter proper name");
        }
        return null;
    }

    /***
     * Creating password for the account of given name
     * @param name account's holder name
     * @return if correct password is entered then returns the password or else null
     */
    public String getPassword(String name)
    {
        System.out.println("Enter a password for given account :- "+name);
        System.out.println("Password should contain at least 6 letters");
        Scanner input=new Scanner(System.in);
        String pass=input.next();
         if(pass.length()>0 && pass.length()>=6 )
         {
             return pass;
         }
         else
         {
             System.out.println("Enter password according to the rules");
         }
         return null;
    }

    /***
     * Checking that the account with given name exits or not
     * @param name account holder name
     * @return if exists returns the BankAccountService with the given name
     */
    public BankAccountService checkAccountExists(String name)
    {
        try
        {
            BankAccountService bankAccountService=nameToAccounts.get(name).getAccountType();
            if(bankAccountService!=null)
            {
                AccountType accountType=bankAccountService.getBankAccountDetails().getAccount_type();
                if(accountType.equals(AccountType.Savings))
                {
                    return nameToAccounts.get(name);
                }
                else if(accountType.equals(AccountType.Current))
                {
                    //code for current account
                }
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return null;
    }

    /***
     * This method is used to check the credential of user and help him login if it is right
     * @param name The username
     * @param password password for given username
     * @return Object of BankAccountService
     */
    public BankAccountService loginIntoAccount(String name, String password)
    {
        if(nameToAccounts.containsKey(name))
        {
            BankAccountService bankAccountService= nameToAccounts.get(name);
            if(bankAccountService.getBankAccountDetails().getPassword()==password)
            {
                System.out.println("You have successfully login into to account "+name);
                return bankAccountService;
            }
            else
            {
                System.out.println("You have entered wrong password,please try to enter correct password");
            }
        }
        else {
            System.out.println("There is no account with "+name+" this name");
        }
        return null;
    }

    /***
     * Deposit the amount of money in given bank account
     * @param bankAccountService the account of user
     * @param amount the amount of money to be deposited
     * @return true if the money is deposited or else false
     */
    public boolean depositMoney(BankAccountService bankAccountService,Long amount)
    {
        return bankAccountService.deposit(amount);
    }

    public boolean withdrawMoney(BankAccountService bankAccountService,Long amount)
    {
       return bankAccountService.withdrawBalance(amount);
    }

    public long checkTheBalanceInAcc(BankAccountService bankAccountService)
    {
        return bankAccountService.checkBalance();
    }

    public boolean closeTheAccount(BankAccountService bankAccountService)
    {
        String name=bankAccountService.getBankAccountDetails().getHolder_name();
        long id=bankAccountService.getBankAccountDetails().getAccount_no();
       boolean closed= bankAccountService.closeAccount();
       if(closed==true)
       {
           nameToAccounts.remove(name);
           idToAccounts.remove(id);
       }
       else
       {
           System.out.println("Oops!, something went wrong");
       }
       return closed;
    }
}
