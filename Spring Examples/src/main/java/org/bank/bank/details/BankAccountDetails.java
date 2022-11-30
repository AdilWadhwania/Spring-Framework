package org.bank.bank.details;


public class BankAccountDetails
{
    private long account_no;
    private String holder_name;

    private long bank_balance;
    private String userName;
    private String password;

    public BankAccountDetails()
    {

    }

    public BankAccountDetails(long account_no, String holder_name, long bank_balance, String userName, String password)
    {
        this.account_no = account_no;
        this.holder_name = holder_name;

        this.bank_balance = bank_balance;
        this.userName = userName;
        this.password = password;
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

    public long getBank_balance()
    {
        return bank_balance;
    }

    public void setBank_balance(long bank_balance)
    {
        this.bank_balance = bank_balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
                ", bank_balance=" + bank_balance +
                '}';
    }
}
