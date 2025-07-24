package com.oracle.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oracle.entities.InterestIncomeExpense;
import com.oracle.entities.InterestIncomeExpense.EntryType;

@Repository
public interface InterestIncomeExpenseRepository extends CrudRepository<InterestIncomeExpense, Integer> {

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM InterestIncomeExpense i " +
           "WHERE i.entryType = :type AND i.period BETWEEN :startDate AND :endDate")
    BigDecimal getTotalByTypeAndPeriod(EntryType type, LocalDate startDate, LocalDate endDate);

    @Query("""
    	    SELECT YEAR(i.period) AS yr,
    	           SUM(CASE WHEN i.entryType = 'INCOME' THEN i.amount ELSE 0 END) AS income,
    	           SUM(CASE WHEN i.entryType = 'EXPENSE' THEN i.amount ELSE 0 END) AS expense
    	    FROM InterestIncomeExpense i
    	    GROUP BY YEAR(i.period)
    	    ORDER BY yr
    	""")
    List<Object[]> getAnnualIncomeExpenseSummary();
}
