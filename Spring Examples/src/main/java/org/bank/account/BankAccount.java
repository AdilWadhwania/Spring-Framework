package org.bank.account;

import org.bank.bank.details.BankAccountDetails;
import org.bank.banking.services.BankAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class BankAccount implements BankAccountService
{
    private BankAccountDetails bankAccountDetails;

    public BankAccount()
    {
    }

    public BankAccount(BankAccountDetails bankAccountDetails)
    {
        this.bankAccountDetails = bankAccountDetails;
    }

    public BankAccountDetails getBankAccountDetails()
    {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(BankAccountDetails bankAccountDetails)
    {
        this.bankAccountDetails = bankAccountDetails;
    }

    /***
     * Creates a savings bank account adding all the details of account in the instance variable bankaccountdetails
     * @param account_no the account number for bank account the bank provides this
     * @param account_name The account's holder name
     * @param username The user name of the account
     * @return The Savings account object
     */
    @Override
    public BankAccountService createAccount(Long account_no,String account_name,
                                            String password,String username)
    {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
        Scanner in=new Scanner(System.in);

        System.out.println("To create your savings account you need to keep 1000 rupees cash in your account \n enter :-" +
                "\n 1)Yes \n 2)No");
        int input=in.nextInt();
        if(input==1)
        {
            try
            {
                bankAccountDetails = (BankAccountDetails) applicationContext.getBean("bankAccountDetails");
                bankAccountDetails.setAccount_no(account_no);
                bankAccountDetails.setHolder_name(account_name);
                bankAccountDetails.setUserName(username);
                bankAccountDetails.setPassword(password);

                System.out.println("Your account has been created successfully");
                boolean isDeposited=this.deposit(1000);
                if(isDeposited)
                {
                    System.out.println("Deposited amount 1000 rupees in bank account and details of your " +
                            "of your bank account is "+bankAccountDetails.getAccount_no()+"\n"
                                                      +bankAccountDetails.getHolder_name()+"\n");
                }
                else
                {
                    System.out.println("Error while depositing the money");
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return null;
            }
        }
        else
        {
            System.out.println("Oops!, You have selected not to keep 1000 rupees minimum limit in Savings account");
        }
        return this;
    }


    /***
     * This method is use to deposit your money in your bank account
     * @param amount the amount you want to deposit
     * @return true or false based on the transaction
     */
    @Override
    public boolean deposit(long amount)
    {
        boolean deposited;
        try {
            long oldBalance = this.bankAccountDetails.getBank_balance();
            this.bankAccountDetails.setBank_balance((oldBalance + amount));
            deposited=true;
        }
        catch (Exception ex)
        {
            System.out.println("Something went wrong while depositing the amount, please try again");
            deposited=false;
            ex.printStackTrace();
        }
        return deposited;
    }

    /***
     * This method is used to withdraw money from your account
     * @param amount the amount you want to withdraw
     * @return true or false based on the transaction
     */
    @Override
    public boolean withdrawBalance(long amount)
    {
        boolean withdraw = false;
        Scanner input=new Scanner(System.in);
        try
        {
         long oldBalance =this.bankAccountDetails.getBank_balance();
         if(amount>oldBalance)
         {
             System.out.println("You don't have enough balance to withdraw this amount\n" +
                     "your bank balance is "+oldBalance);
         }
         else if(oldBalance-amount<1000)
         {
             System.out.println("Your bank balance is"+oldBalance+" if you withdraw this amount " +
                     "your account balance will go less than the minimum balance in savings account(1000)" +
                     "and their would be penalty charge");
             System.out.println("Would you like to withdraw money?\n1)Yes\n2)No");
             int ans=input.nextInt();
             if(ans==1)
             {
                 System.out.println("Deposit the money to reach minimum balance policy within next 5 days to avoid penalty");
                 this.bankAccountDetails.setBank_balance(oldBalance-amount);
                 withdraw=true;
             }
             else
             {
                 withdraw=false;
                 System.out.println("You have choose to not withdraw the money ");
             }
         }
         else
         {
             this.bankAccountDetails.setBank_balance(oldBalance-amount);
             withdraw=true;
         }
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong to withdraw, please try again after some time");
            e.printStackTrace();
            withdraw=false;
        }
        return withdraw;
    }

    /***
     * This method is used to close the account of the user
     */
    @Override
    public boolean closeAccount()
    {
        boolean closed;
        try
        {
            System.out.println("We are closing account with account no "+this.bankAccountDetails.getAccount_no()+"" +
                    "and name is "+this.bankAccountDetails.getHolder_name());

            closed=true;
        }
         catch (Throwable throwable)
         {
             closed=false;
            throwable.printStackTrace();
        }
        return closed;
    }

    @Override
    public long checkBalance()
    {
        return this.bankAccountDetails.getBank_balance();
    }

    @Override
    public BankAccountService getAccountType()
    {
        return this;
    }

    @Override
    public String toString()
    {
        return "Savings{" +
                "bankAccountDetails=" + bankAccountDetails +
                '}';
    }
}
