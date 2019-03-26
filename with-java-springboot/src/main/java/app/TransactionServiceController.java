package app;

import java.util.List;

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

    // @RequestMapping(value="/transactionservice/transaction/{id}")
    // public Transaction transaction(@PathVariable long id, @RequestBody Transaction transaction) {
    //     return new Transaction(id, );
    // }

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

    @GetMapping(value = "/transaction/{id}")
    public ResponseEntity<?> getTransactionByID(@PathVariable Long id) {
        try {
            Transaction transaction = transactionService.getTransactionByID(id);
            String trStr = String.format("{ \"amount\": %.1f, \"type\": \"%s\", \"parentID\": %d }", 
                    transaction.getAmount(), transaction.getType(), transaction.getParentID());
            return new ResponseEntity<String>(trStr, HttpStatus.OK);
        } 
        catch (Exception e) {
            logger.error("Transaction with id {} not found.", id);
            return new ResponseEntity<String>(("{ \"error\" : \"Transaction with id " + id + " not found.\" }"), HttpStatus.NOT_FOUND);
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
        try {
            List<Long> transactionIDs = transactionService.getTransactionsByType(type);
            return new ResponseEntity<List<Long>>(transactionIDs, HttpStatus.OK);
        } 
        catch (Exception e) {
            logger.error("Type with {} not found.", type);
            return new ResponseEntity<String>(("{ \"error\" : \"Type with " + type + " not found.\" }"), HttpStatus.NOT_FOUND);
        } 
    }

    @PutMapping(value = "/transaction/{id}")
    public ResponseEntity<?> updateTransaction(@Valid @RequestBody Transaction transaction, @PathVariable Long id) {
        try {
            transactionService.updateTransaction(id, transaction);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        catch (Exception e) {
            logger.error("Transaction with id {} not found.", id);
            return new ResponseEntity<String>(("{ \"error\" : \"Transaction with id " + id + " not found.\" }"), HttpStatus.NOT_FOUND);
        }  
    }

    @GetMapping(value = "/sum/{id}")
    public ResponseEntity<?> getSumOfAllAmountByID(@PathVariable Long id) {
        try {
            Double sum = transactionService.calculateSumOfAmountByID(id);
            String sumStr = String.format("{ \"sum\": %.1f }", sum);
            return new ResponseEntity<String>(sumStr, HttpStatus.OK);
        } 
        catch (Exception e) {
            logger.error("Transaction with id {} not found.", id);
            return new ResponseEntity<String>(("{ \"error\" : \"Transaction with id " + id + " not found.\" }"), HttpStatus.NOT_FOUND);
        } 
    }
}