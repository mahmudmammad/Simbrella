package Expense.Tracker.demo.repository;

import Expense.Tracker.demo.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}