package Expense.Tracker.demo.controller;

import Expense.Tracker.demo.dto.ExpenseDTO;
import Expense.Tracker.demo.model.Expense;
import Expense.Tracker.demo.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense testExpense;

    @BeforeEach
    void setUp() {
        testExpense = new Expense();
        testExpense.setId(1L);
        testExpense.setAmount(100.0);
        testExpense.setCategory("Food");
        testExpense.setDescription("Test expense");
    }

    @Test
    void createExpense_ShouldReturnCreatedExpense() {
        when(expenseService.createExpense(any(Expense.class))).thenReturn(testExpense);

        ResponseEntity<Expense> response = expenseController.createExpense(testExpense);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testExpense, response.getBody());
    }

    @Test
    void getAllExpenses_ShouldReturnListOfExpenses() {
        List<Expense> expenses = Arrays.asList(testExpense);
        when(expenseService.getAllExpenses()).thenReturn(expenses);

        ResponseEntity<List<Expense>> response = expenseController.getAllExpenses();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(expenses, response.getBody());
    }

    @Test
    void getExpensesByDateRange_ShouldReturnFilteredExpenses() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        List<Expense> expenses = Arrays.asList(testExpense);

        when(expenseService.getExpensesByDateRange(startDate, endDate))
                .thenReturn(expenses);

        ResponseEntity<List<ExpenseDTO>> response = expenseController
                .getExpensesByDateRange(startDate.toString(), endDate.toString());

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteExpense_ShouldReturnSuccessMessage() {
        Long expenseId = 1L;
        doNothing().when(expenseService).deleteExpense(expenseId);

        ResponseEntity<String> response = expenseController.deleteExpense(expenseId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().contains("successfully deleted"));
    }
}
