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
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hotel_expenses")
public class HotelExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    Date createdDate;
    @NonNull
    @OneToOne
    ExpenseType expenseType;
    @NonNull
    @ManyToOne
    Hotel hotel;
    @NonNull
    Double employeePayments;
    @NonNull
    Double generalMaintenancePayments;
}