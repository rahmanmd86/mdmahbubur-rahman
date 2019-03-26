package app;

//import java.util.concurrent.atomic.AtomicLong;

import javax.validation.constraints.NotNull;

public class Transaction {

    //private final AtomicLong incrementID = new AtomicLong();
    private Long transactionID;
    @NotNull
    private Double amount;
    @NotNull
    private String type;
    private Long parentID;

    public Transaction() {
        super();
    }

    public Transaction(Long transactionID, Double amount, String type, Long parentID) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.type = type;
        this.parentID = parentID;
    }

    public Transaction(Long transactionID, Double amount, String type) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.type = type;
        this.parentID = null;
    }

    /**
     * @return the transactionID
     */
    public Long getTransactionID() {
        return this.transactionID;
    }

    public void setTransactionID(Long id) {
        this.transactionID = id;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return this.amount;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @return the parentID
     */
    public Long getParentID() {
        return this.parentID;
    }

    public void setParentID(Long id) {
        this.parentID = id;
    }
    
}

