package groupk.workers.data;

public class BankAccount {
    public String bank;
    public int bankID;
    public int bankBranch;


    public BankAccount(String bank, int bankID, int bankBranch){
        this.bank = bank;
        this.bankID = bankID;
        this.bankBranch = bankBranch;

    }

    public String getBank() {
        return bank;
    }
    public int getBankID() { return bankID; }
    public int getBankBranch() {
        return bankBranch;
    }

}
