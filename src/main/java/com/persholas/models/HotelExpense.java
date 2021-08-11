package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hotel_expenses")
public class HotelExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    Date created_date;
    @OneToOne
    ExpenseType expenseType;
    @ManyToOne
    Hotel hotel;
    @NonNull
    Double employee_payments;
    @NonNull
    Double general_maintenance_payments;
}