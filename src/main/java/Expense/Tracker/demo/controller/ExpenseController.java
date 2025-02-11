package Expense.Tracker.demo.controller;

import Expense.Tracker.demo.dto.ExpenseDTO;
import Expense.Tracker.demo.model.Expense;
import Expense.Tracker.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable("id") Long expenseId) {
        Expense expense = expenseService.getExpense(expenseId);
        return ResponseEntity.ok(expense);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDateRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        List<ExpenseDTO> expenses = expenseService.getExpensesByDateRange(startDate, endDate)
                .stream()
                .map(ExpenseDTO::fromExpense)
                .collect(Collectors.toList());
        return ResponseEntity.ok(expenses);
    }

    // New endpoint to get expenses by category
    @GetMapping("/category")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByCategory(@RequestParam("category") String category) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByCategory(category)
                .stream()
                .map(ExpenseDTO::fromExpense)
                .collect(Collectors.toList());
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable("id") Long expenseId,
            @RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(expenseId, expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        String successMessage = "Expense with ID " + expenseId + " has been successfully deleted.";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(successMessage, headers, HttpStatus.OK);
    }


}
