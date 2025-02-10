package Expense.Tracker.demo.repository;

import Expense.Tracker.demo.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    Optional<Expense> findByUserIdAndId(Long userId, Long expenseId);
}
