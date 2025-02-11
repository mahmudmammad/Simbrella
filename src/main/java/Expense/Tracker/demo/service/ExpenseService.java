package Expense.Tracker.demo.service;

import Expense.Tracker.demo.exception.ResourceNotFoundException;
import Expense.Tracker.demo.model.Expense;
import Expense.Tracker.demo.model.User;
import Expense.Tracker.demo.repository.ExpenseRepository;
import Expense.Tracker.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userService.getUserById(userDetails.getUserId());
    }

    // Create expense
    public Expense createExpense(Expense expense) {
        User currentUser = getCurrentUser();
        expense.setUser(currentUser);
        return expenseRepository.save(expense);
    }

    // Get all expenses for current user
    public List<Expense> getAllExpenses() {
        User currentUser = getCurrentUser();
        return expenseRepository.findByUserId(currentUser.getId());
    }

    // Get one expense
    public Expense getExpense(Long expenseId) {
        User currentUser = getCurrentUser();
        return expenseRepository.findByUserIdAndId(currentUser.getId(), expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with id " + expenseId));
    }
    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        User currentUser = getCurrentUser();
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay().minusSeconds(1);
        return expenseRepository.findByUserIdAndCreatedAtBetween(currentUser.getId(), startDateTime, endDateTime);
    }

    // New method to get expenses by category
    public List<Expense> getExpensesByCategory(String category) {
        User currentUser = getCurrentUser();
        return expenseRepository.findByUserIdAndCategory(currentUser.getId(), category);
    }

    // Update expense
    public Expense updateExpense(Long expenseId, Expense updatedExpense) {
        Expense expense = getExpense(expenseId); // This will check ownership

        expense.setAmount(updatedExpense.getAmount());
        expense.setDescription(updatedExpense.getDescription());
        expense.setCategory(updatedExpense.getCategory());

        return expenseRepository.save(expense);
    }

    // Delete expense
    public void deleteExpense(Long expenseId) {
        Expense expense = getExpense(expenseId); // This will check ownership
        expenseRepository.delete(expense);
    }
}
