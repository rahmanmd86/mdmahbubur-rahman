package app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private List<Transaction> transactions = Arrays.asList(
        new Transaction(10L, 1000., "cars"),
        new Transaction(11L, 2000., "cars"),
        new Transaction(12L, 3000., "shopping", 10L)
    );

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Transaction getTransactionByID(Long id) {
        return transactions.stream().filter(t -> t.getTransactionID().equals(id)).findFirst().get();
    }

	public List<Long> getTransactionsByType(String type) {
        return transactions.stream().filter(t -> t.getType().equals(type))
            .map(Transaction::getTransactionID)
            .collect(Collectors.toList());
	}

	public void updateTransaction(Long id, Transaction transaction) {
        Transaction currenTransaction = transactions.stream().filter(t -> t.getTransactionID().equals(id)).findFirst().get();
        if(currenTransaction != null) {
            transaction.setTransactionID(id);

            System.out.println(transaction.getParentID());
            
            if(currenTransaction.getParentID() != null && transaction.getParentID() == null) {
                transaction.setParentID(currenTransaction.getParentID());
            }
            else {
                transaction.setParentID(transaction.getParentID());
            }
        }

        for(int i=0; i<transactions.size(); i++) {
            if(transactions.get(i).getTransactionID() == id) {
                transactions.set(i, transaction);
            }
        }
        
	}

	public Double calculateSumOfAmountByID(Long id) {
        double sum = 0;
		for(Transaction tr : transactions) {
            if(tr.getTransactionID() == id || tr.getParentID() == id) {
                sum += tr.getAmount();
            }
        }

        return sum;
	}

	
    
}