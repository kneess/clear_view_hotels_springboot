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
@Table(name = "room_expenses")
public class RoomExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    Date createdDate;
    @OneToOne
    @NonNull
    ExpenseType expenseType;
    @ManyToOne
    @NonNull
    Room room;
    @NonNull
    Double maintenancePayment;
}