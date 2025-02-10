package Expense.Tracker.demo.controller;

import Expense.Tracker.demo.dto.ExpenseDTO;
import Expense.Tracker.demo.exception.ResourceNotFoundException;
import Expense.Tracker.demo.model.Expense;
import Expense.Tracker.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users/{userId}/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Add new POST endpoint to create expense
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(
            @PathVariable Long userId,
            @RequestBody Expense expense) {
        try {
            Expense newExpense = expenseService.createExpense(expense, userId);
            return new ResponseEntity<>(ExpenseDTO.fromExpense(newExpense), HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    // Add new GET endpoint to retrieve all expenses
    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(@PathVariable Long userId) {
        try {
            List<ExpenseDTO> expenses = expenseService.getAllExpensesForUser(userId)
                    .stream()
                    .map(ExpenseDTO::fromExpense)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId) {
        try {
            Expense expense = expenseService.getExpenseForUser(userId, expenseId);
            return new ResponseEntity<>(ExpenseDTO.fromExpense(expense), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId,
            @RequestBody Expense expense) {
        try {
            Expense updatedExpense = expenseService.updateExpense(userId, expenseId, expense);
            return new ResponseEntity<>(ExpenseDTO.fromExpense(updatedExpense), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId) {
        try {
            expenseService.deleteExpense(userId, expenseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }
}
