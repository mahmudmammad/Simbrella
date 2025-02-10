package Expense.Tracker.demo.service;

import Expense.Tracker.demo.exception.ResourceNotFoundException;
import Expense.Tracker.demo.model.Expense;
import Expense.Tracker.demo.model.User;
import Expense.Tracker.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    // Create expense for user
    public Expense createExpense(Expense expense, Long userId) {
        User user = userService.getUserById(userId);
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    // Get all expenses for user
    public List<Expense> getAllExpensesForUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    // Get one expense (checking if it belongs to user)
    public Expense getExpenseForUser(Long userId, Long expenseId) {
        return expenseRepository.findByUserIdAndId(userId, expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with id " + expenseId + " for user " + userId));
    }

    public Expense updateExpense(Long userId, Long expenseId, Expense updatedExpense) {
        Expense expense = getExpenseForUser(userId, expenseId);
        expense.setAmount(updatedExpense.getAmount());
        expense.setDescription(updatedExpense.getDescription());
        expense.setCategory(updatedExpense.getCategory());
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long userId, Long expenseId) {
        Expense expense = getExpenseForUser(userId, expenseId);
        expenseRepository.delete(expense);
    }
}
