package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    String name;
    @NonNull
    String address;
    @NonNull
    String state;
    @NonNull
    String zipCode;
    @OneToOne
    Employee manager;
    @OneToMany
    List<Employee> employees;
    @OneToMany
    List<Room> rooms;
    @OneToMany(mappedBy = "hotel")
    List<HotelExpense> hotel_expenses;
}