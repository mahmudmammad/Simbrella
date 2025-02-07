package Expense.Tracker.demo.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import Expense.Tracker.demo.model.Expense;


@Service
public class ExpenseService {
    private final Map<Long, Expense> expenses = new ConcurrentHashMap<>();
    private long currentId = 1;

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses.values());
    }

    public Expense getExpenseById(Long id) {
        return expenses.get(id);
    }

    public Expense createExpense(Expense expense) {
        expense.setId(currentId++);
        expenses.put(expense.getId(), expense);
        return expense;
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        if (expenses.containsKey(id)) {
            updatedExpense.setId(id);
            expenses.put(id, updatedExpense);
            return updatedExpense;
        }
        return null;
    }

    public boolean deleteExpense(Long id) {
        return expenses.remove(id) != null;
    }
}
