package com.persholas.dao;

import com.persholas.models.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseTypeRepo extends JpaRepository<ExpenseType, Long> {
}
