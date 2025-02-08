package Expense.Tracker.demo.service;

import Expense.Tracker.demo.model.Expense;
import Expense.Tracker.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setAmount(updatedExpense.getAmount());
                    expense.setDescription(updatedExpense.getDescription());
                    expense.setDate(updatedExpense.getDate());
                    expense.setCategory(updatedExpense.getCategory());
                    expense.setLastUpdateTime(updatedExpense.getLastUpdateTime());
                    return expenseRepository.save(expense);
                })
                .orElseGet(() -> {
                    updatedExpense.setId(id);
                    return expenseRepository.save(updatedExpense);
                });
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
