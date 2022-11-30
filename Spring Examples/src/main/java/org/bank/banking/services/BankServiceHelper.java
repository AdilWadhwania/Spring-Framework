package org.bank.banking.services;

import org.bank.bank.details.BankAccountDetails;
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

    public void bankingOptions(Scanner input)
    {

        System.out.println("Welcome to ABC bank");

        System.out.println("Select the service from given below\n" +
                "1)Create Account\n" +
                "2)Login to account");
        int selection=input.nextInt();

        if(selection==1)
        {       //create account
            createAccount(input);
        }
        else
        {
            login(input);
        }
    }


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
                try
                {
                    bankAccount=(BankAccountService) applicationContext.getBean("account");
                    BankAccountService bankAccountService = bankAccount.createAccount(account_no, name.trim(), password,userName);
                    bankAccountsHashMap.put(account_no,bankAccountService);
                    listOfIds.add(account_no);
                    login(input);
                }
                catch (Exception e)
                {
                    System.out.println("Problem in creating account,please try again after some time");
                  e.printStackTrace();
                }
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
        String name =input.nextLine();
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
            String userName=input.nextLine();
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
        String pass=input.nextLine();
         if(pass.length()>=6 )
         {
             return pass;
         }
         else
         {
             System.out.println("Enter password according to the rules");
         }
         return null;
    }

   public void login(Scanner input)
   {
       System.out.println("Login to your account");
       System.out.println("Enter the account no :- ");
       long account_no=input.nextLong();
       System.out.println("Please enter the name ");
       String name=input.next();
       System.out.println("Please enter your password");
       String password=input.next();
       if(name.length()>0 && password.length()>0)
       {
         BankAccountService bankAccountService= loginIntoAccount(account_no,name,password);
           loginServices(bankAccountService,input);
       }
   }

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
     * The services provided by a bank after logging in by a user
     * @param bankAccountService service object
     * @param input Scanner object
     */
    public void loginServices(BankAccountService bankAccountService,Scanner input)
    {
        BankAccountDetails Acc_details=bankAccountService.getBankAccountDetails();
        System.out.println(Acc_details.getHolder_name()+"you are successfully login");
        System.out.println("Select a service");
        System.out.println("Services provided are\n1)Check Balance\n2)Withdraw money\n3)Deposit Money\n4)CLose Account");
        int val=input.nextInt();
        if(val==1)
        {
            long balance=checkTheBalanceInAcc(bankAccountService);
            System.out.println(Acc_details.getUserName()+", you have "+balance+" in your account");

        }
        else if(val==2)
        {
            System.out.println("Enter the amount of money you want to withdraw, amount should be greater or equal to 100");
            long amount=input.nextLong();
            if(amount > 100)
            {
                boolean withdraw=withdrawMoney(bankAccountService, amount);
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
                boolean deposited=depositMoney(bankAccountService,amount);
            }
            else
            {
                System.out.println("Enter amount greater than 0");
            }
        }
        else if(val==4)
        {
            closeTheAccount(bankAccountService);
        }
        else
        {
            System.out.println("Select Proper input");
        }
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
