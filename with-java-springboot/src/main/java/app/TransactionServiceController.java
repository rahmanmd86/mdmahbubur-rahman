package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transactionservice")
public class TransactionServiceController {

    public static final Logger logger = LoggerFactory.getLogger(TransactionServiceController.class);

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/transactions")
    public ResponseEntity<?> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } 
        catch (Exception e) {
            logger.error("No Transactions are found.");
            return new ResponseEntity<String>(("No Transactions has been found"), HttpStatus.NOT_FOUND);
        }  
    }

    @GetMapping(value = "/transaction/{id}", produces = "application/json")
    public ResponseEntity<?> getTransactionByID(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            Transaction transaction = transactionService.getTransactionByID(id);
            res.put("amount", transaction.getAmount());
            res.put("type", transaction.getType());
            res.put("parentID", transaction.getParentID());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } 
        catch (Exception e) {
            logger.error("Transaction with id {} not found.", id);
            String errorMsg = "Transaction with id " + id + " not found.";
            res.put("error", errorMsg);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }  
    }

    // @GetMapping(value = "/transaction/{id}")
    // public ResponseEntity<?> getTransactionByID(@PathVariable Long id) {
    //     try {
    //         Transaction transaction = transactionService.getTransactionByID(id);
    //         return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    //     } 
    //     catch (Exception e) {
    //         logger.error("Transaction with id {} not found.", id);
    //         return new ResponseEntity<String>(("Transaction with id " + id + " not found"), HttpStatus.NOT_FOUND);
    //     }  
    // }

    @GetMapping(value = "/types/{type}")
    public ResponseEntity<?> getTransactionsByType(@PathVariable String type) {
        Map<String, Object> res = new HashMap<>();
        List<Long> transactionIDs = transactionService.getTransactionsByType(type);
        if (transactionIDs.size() > 0) {
            return new ResponseEntity<List<Long>>(transactionIDs, HttpStatus.OK);
        }
        else {
            logger.error("Type with {} not found.", type);
            String errorMsg = "Type with " + type + " not found.";
            res.put("error", errorMsg);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/transaction/{id}")
    public ResponseEntity<?> updateTransaction(@Valid @RequestBody Transaction transaction, @PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            transactionService.updateTransaction(id, transaction);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        catch (Exception e) {
            logger.error("Transaction with id {} not found.", id);
            String errorMsg = "Transaction with id " + id + " not found.";
            res.put("error", errorMsg);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }  
    }

    @GetMapping(value = "/sum/{id}")
    public ResponseEntity<?> getSumOfAllAmountByID(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        Double sum = transactionService.calculateSumOfAmountByID(id);
        res.put("sum", sum);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}