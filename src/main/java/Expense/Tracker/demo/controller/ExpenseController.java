package Expense.Tracker.demo.controller;

import Expense.Tracker.demo.model.Expense;
import Expense.Tracker.demo.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    // Constructor injection for ExpenseService
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> listExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getExpense(@PathVariable long id) {
        Optional<Expense> expense = expenseService.getExpenseById(id);
        return expense.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(Map.of("error", "Expense not found"), HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateExpense(@PathVariable long id, @RequestBody Expense updatedExpense) {
        Expense expense = expenseService.updateExpense(id, updatedExpense);
        if (expense != null) {
            return new ResponseEntity<>(Map.of("message", "Expense updated successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Expense not found"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(Map.of("message", "Expense deleted successfully"), HttpStatus.OK);
    }
}
