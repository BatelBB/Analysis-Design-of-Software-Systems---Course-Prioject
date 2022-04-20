package groupk.workers.data;

public class BankAccount {
    private String bank;
    private int bankID;
    private int bankBranch;


    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public void setBankBranch(int bankBranch) {
        this.bankBranch = bankBranch;
    }

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
