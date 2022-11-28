package org.bank.bank.details;


import org.bank.account.AccountType;

public class BankAccountDetails
{
    private long account_no;
    private String holder_name;
    private AccountType account_type;
    private long bank_balance;
    private String password;

    public BankAccountDetails()
    {
        this.account_type=AccountType.Savings;
    }

    public BankAccountDetails(long account_no, String holder_name, AccountType account_type, long bank_balance)
    {
        this.account_no = account_no;
        this.holder_name = holder_name;
        this.account_type = account_type;
        this.bank_balance = bank_balance;
    }

    public long getAccount_no()
    {
        return account_no;
    }

    public void setAccount_no(long account_no)
    {
        this.account_no = account_no;
    }

    public String getHolder_name()
    {
        return holder_name;
    }

    public void setHolder_name(String holder_name)
    {
        this.holder_name = holder_name;
    }

    public AccountType getAccount_type()
    {
        return account_type;
    }

    public void setAccount_type(AccountType account_type)
    {
        this.account_type = account_type;
    }

    public long getBank_balance()
    {
        return bank_balance;
    }

    public void setBank_balance(long bank_balance)
    {
        this.bank_balance = bank_balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "BankAccountDetails{" +
                "account_no=" + account_no +
                ", holder_name='" + holder_name + '\'' +
                ", account_type=" + account_type +
                ", bank_balance=" + bank_balance +
                '}';
    }
}
