package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @NotBlank(message = "Must enter in name")
    String firstName;
    @NonNull
    @NotBlank(message = "Must enter in last name")
    String lastName;
    @NonNull
    @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "Not a valid email")
    String email;
    @NonNull
    @NotBlank(message = "Must enter in address")
    String address;
    @NonNull
    @NotBlank(message = "Must enter in city")
    String city;
    @NonNull
    @NotBlank(message = "Must enter in state")
    String state;
    @NonNull
    @NotBlank(message = "Must enter in zip code")
    String zipCode;
    @NonNull
    @NotBlank(message = "Must enter in phone number")
    String phoneNumber;
    @NonNull
    @NotBlank(message = "Must enter in title")
    String title;
    @NonNull
    @Min(value = 12000, message = "must be equal or greater than 12000")
    Double salary;
    @OneToOne
    Employee employeeManager;
    @NonNull
    @NotNull
    Boolean active;

    public Employee(String firstname, String email, String address, String city, String state, String zip, String phoneNumber,
                    String title, Double salary, Employee employeeManager, Boolean active)
    {
        this.firstName = firstname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zip;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.salary = salary;
        this.employeeManager = employeeManager;
        this.active = active;
    }
}