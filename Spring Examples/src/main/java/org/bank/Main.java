package org.bank;

import org.bank.bank.details.BankAccountDetails;
import org.bank.banking.services.BankAccountService;
import org.bank.banking.services.BankServiceHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
        BankServiceHelper bankServiceHelper=(BankServiceHelper) applicationContext.getBean("bankHelper");
        Scanner input=new Scanner(System.in);
        bankServiceHelper.bankingOptions(input);
    }

}
