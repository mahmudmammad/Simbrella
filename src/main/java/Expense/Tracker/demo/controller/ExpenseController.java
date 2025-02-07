package Expense.Tracker.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Expense.Tracker.demo.model.Expense;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private List<Expense> expenses = new ArrayList<>(); // Initialize expense list
    private long idCounter = 1; // Initialize ID counter

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        expense.setId(idCounter++); // Assign unique ID
        expense.setLastUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // Set timestamp
        expenses.add(expense); // Add to list
        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> listExpenses() {
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getExpense(@PathVariable long id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return new ResponseEntity<>(expense, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Expense not found"), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateExpense(@PathVariable long id, @RequestBody Expense updatedExpense) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expense.setAmount(updatedExpense.getAmount());
                expense.setDescription(updatedExpense.getDescription());
                expense.setDate(updatedExpense.getDate());
                expense.setCategory(updatedExpense.getCategory());
                expense.setLastUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // Update timestamp
                return new ResponseEntity<>(Map.of("message", "Expense updated successfully"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Expense not found"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable long id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expenses.remove(expense);
                return new ResponseEntity<>(Map.of("message", "Expense deleted successfully"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Expense not found"), HttpStatus.NOT_FOUND);
    }
}
