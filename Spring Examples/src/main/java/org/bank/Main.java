package org.bank;

import org.bank.account.AccountType;
import org.bank.bank.details.BankAccountDetails;
import org.bank.banking.services.BankAccountService;
import org.bank.banking.services.BankServiceHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        Main main=new Main();
        Scanner input=new Scanner(System.in);
        main.bankingOptions(input);
    }

    public void bankingOptions(Scanner input)
    {
        System.out.println("Welcome to ABC bank");

        System.out.println("Select the service from given below\n" +
                "1)Create Account\n" +
                "2)Login to account");
        int selection=input.nextInt();

        if(selection==1)
        {       //create account
            createBankAccount(input);
        }
        else if(selection==2)
        {
            //ask whether the user have an account in the bank
            loginToAccount(input);
        }
    }

    /***
     * This method is use to create new account of user
     * @param input Scanner to take input
     */
    public void createBankAccount(Scanner input)
    {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
        BankServiceHelper bankServiceHelper=(BankServiceHelper) applicationContext.getBean("BankHelper");
        System.out.println("Choose the type of bank account you want to open from the following" +
                "\n1)Savings\n2)Current");
        HashMap<Integer,AccountType> accountTypeHashMap=new HashMap<Integer, AccountType>();
        accountTypeHashMap.put(1,AccountType.Savings);
        accountTypeHashMap.put(2,AccountType.Current);
        int selectType=input.nextInt();

        if(accountTypeHashMap.get(selectType).equals(AccountType.Savings))
        {       //create Savings account
            bankServiceHelper.createAccount(AccountType.Savings,input);
        }
        else if(accountTypeHashMap.get(selectType).equals(AccountType.Current))
        {
            //create current account
        }
        else
        {
            System.out.println("Select the proper option");
        }
    }

    /***
     * This method is use to check the user bank account and let the user login
     * the name and password is taken from user and verified using the login method of helper
     * @param input the scanner object
     */
    public void loginToAccount(Scanner input)
    {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
        BankServiceHelper bankServiceHelper=(BankServiceHelper) applicationContext.getBean("BankHelper");
        System.out.println("Login to your account");
        System.out.println("Please enter the name ");
        String name=input.next();
        System.out.println("Please enter your password");
        String password=input.next();
        if(name.length()>0 && password.length()>0)
        {
            BankAccountService bankAccountService=bankServiceHelper.loginIntoAccount(name,password);

            loginServices(bankAccountService,input,bankServiceHelper);
        }
        else
        {
            System.out.println("Please enter proper username and password");
        }
    }

    /***
     * The services provided by a bank after logging in by a user
     * @param bankAccountService service object
     * @param input Scanner object
     * @param bankServiceHelper Helper object to access services
     */
    public void loginServices(BankAccountService bankAccountService,Scanner input,BankServiceHelper bankServiceHelper)
    {
        BankAccountDetails Acc_details=bankAccountService.getBankAccountDetails();
        System.out.println(Acc_details.getHolder_name()+"you are successfully login");
        System.out.println("Select a service");
        System.out.println("Services provided are\n1)Check Balance\n2)Withdraw money\n3)Deposit Money\n4)CLose Account");
        int val=input.nextInt();
        if(val==1)
        {
            long balance=bankServiceHelper.checkTheBalanceInAcc(bankAccountService);
            System.out.println(Acc_details.getHolder_name()+", you have"+balance+" in your account");
        }
        else if(val==2)
        {
            System.out.println("Enter the amount of money you want to withdraw, amount should be greater or equal to 100");
            Long amount=input.nextLong();
            if(amount>0 && amount>100)
            {
               boolean withdraw= bankServiceHelper.withdrawMoney(bankAccountService, amount);
            }
            else
            {
                System.out.println("Enter number greater than 0 and also greater than 100");
            }
        }
        else if(val==3)
        {
            System.out.println("Enter the amount of money you want to deposit");
            long amount=input.nextLong();
            if(amount>0)
            {
                bankServiceHelper.depositMoney(bankAccountService,amount);
            }
            else
            {
                System.out.println("Enter amount greater than 0");
            }
        }
        else if(val==4)
        {
           bankAccountService.closeAccount();
        }
    }

}
