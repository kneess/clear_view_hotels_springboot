package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String address;
    @NonNull
    String city;
    @NonNull
    String state;
    @NonNull
    String zipCode;
    @NonNull
    String phoneNumber;
    @NonNull
    String title;
    @NonNull
    Double salary;
    @OneToOne
    Employee employeeManager;
    @NonNull
    Boolean active;
}