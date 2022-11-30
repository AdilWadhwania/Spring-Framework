package org.bank.banking.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/*
* create account, login to account, withdraw and deposit cash, delete account, check balance
* */

public class BankServiceHelper
{
    /*
    * The key is account no and the value is bank account (no->account mapping)
    * */
    private final HashMap<Long, BankAccountService> bankAccountsHashMap =new HashMap<Long, BankAccountService>();
    private final List<Long> listOfIds=new ArrayList<Long>();

    /***
     * This method is use to create an account
     */
    public void createAccount(Scanner input)
    {
        BankServiceHelper bank=new BankServiceHelper();
        BankAccountService bankAccount;
        Scanner scanner=new Scanner(System.in);
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");

            //get the account holder name
            String name=bank.getNameForAccount(scanner);
            //ask to enter a user name
            String userName=bank.getUserName(scanner);
            //Get the random account no
            Long account_no=bank.getAccountNo();
            // Ask user for password
            System.out.println("Enter password for given user name : "+userName);
            String password=bank.getPassword(scanner);

            //check all the things needed to create account
            if (name.length() > 0 &&userName.length()>0 && password!=null && account_no!=0)
            {
                bankAccount=(BankAccountService) applicationContext.getBean("account");
                BankAccountService bankAccountService = bankAccount.createAccount(account_no, name.trim(), password,userName);
                bankAccountsHashMap.put(account_no,bankAccountService);
                listOfIds.add(account_no);
            }
            else
            {
                System.out.println("Some of the inputs are not valid ");
                System.out.println("Do you want to open a bank account or login to existing account" +
                        "\n1)Create Account\n2)Login\n3)Exit");
                int choice=input.nextInt();
                if(choice==1)
                {
                    createAccount(input);
                }
                else if(choice==2)
                {
                    System.out.println("Enter account no");
                    long no=input.nextLong();
                    System.out.println("Enter User Name");
                    String user=input.next();
                    System.out.println("Enter the password");
                    String pass=input.next();
                    loginIntoAccount(no,user,pass);
                }
                else
                {
                    System.exit(0);
                }
            }
        }


    /***
     * The method is used to get random numbers for bank account *
     * @return bank account number
     */
    public long getAccountNo()
    {
        long number;
        Random random=new Random();
        number=(long) (100000000000000L + random.nextFloat() * 900000000000000L);
        while (listOfIds.contains(number))
        {
            number=(long) (100000000000000L + random.nextFloat() * 900000000000000L);
        }

        return number;
    }

    /***
     * To get the name to add in the account details *
     * @param input Scanner object
     * @return Name of the account holder
     */
    public String getNameForAccount(Scanner input)
    {
        System.out.println("Enter full name(should be greater than 4 characters) for your account :- ");
        String name =input.next();
        if(name.length()>=4)
        {
            return name;
        } 
        else
        {
            System.out.println("Please enter proper name");
        }
        return null;
    }

    /***
     * user name for user to login to this bank account
     * @param input scanner object
     * @return username
     */
    public String getUserName(Scanner input)
    {
            System.out.println("Enter user name, username should have at least four characters");
            String userName=input.next();
           if(userName.length()>=4 )
           {
                return userName;
           }
           else
           {
               System.out.println("Enter proper name according the rules");
           }

        return null;
    }

    /***
     * Creating password for the account of given name
     * @param input Scanner object
     * @return if correct password is entered then returns the password or else null
     */
    public String getPassword(Scanner input)
    {
        System.out.println("Password should contain at least 6 letters");
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

    /* public boolean checkAccountExists(String name)
    {
        try
        {
            HashMap<AccountType,BankAccountService> typeNameAccount=nameTypeAccounts.get(name);
            if(typeNameAccount.size()>0)
            {
                System.out.println(name+" have"+typeNameAccount.size()+"account");
                for (Map.Entry<AccountType,BankAccountService> value:typeNameAccount.entrySet())
                {
                    Long account_no= value.getValue().getBankAccountDetails().getAccount_no();
                    System.out.println("Account no : "+account_no+" Account Type : "+value.getKey());
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }*/

    /***
     * This method is used to check the credential of user and help him login if it is right
     * @param account_no the accoun no user want to login
     * @param username  username of the account
     * @param password password for given username
     * @return Object of BankAccountService
     */
    public BankAccountService loginIntoAccount(long account_no, String username, String password)
    {
        if(bankAccountsHashMap.containsKey(account_no))
        {
            BankAccountService bankAccountService=bankAccountsHashMap.get(account_no);
            String user=bankAccountService.getBankAccountDetails().getUserName();
            String pass=bankAccountService.getBankAccountDetails().getPassword();
            if (user.equals(username))
            {
                if(pass.equals(password))
                {
                    return bankAccountService;
                }
                else
                {
                    System.out.println("You have entered wrong password");
                }
            }
            else
            {
                System.out.println("Please check the username"+username+" does not exists");
            }
        }
        else
        {
            System.out.println("There is no bank account with this account no");
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
       if(closed)
       {
           bankAccountsHashMap.remove(id);
           listOfIds.remove(id);
       }
       else
       {
           System.out.println("Oops!, something went wrong");
       }
       return closed;
    }
}
