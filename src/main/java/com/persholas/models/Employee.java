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
    String firstname;
    @NonNull
    String lastname;
    @NonNull
    @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "Not a valid email")
    String email;
    @NonNull
    @NotBlank
    String address;
    @NonNull
    @NotBlank
    String city;
    @NonNull
    @NotBlank
    String state;
    @NonNull
    @NotBlank
    String zipCode;
    @NonNull
    @NotBlank
    String phoneNumber;
    @NonNull
    @NotBlank
    String title;
    @NonNull
    @Min(value = 12000, message = "must be equal or greater than 12000")
    Double salary;
    @OneToOne
//    @NonNull
    Employee employeeManager;
    @NonNull
    @NotNull
    Boolean active;

    public Employee(String firstname, String email, String address, String city, String state, String zip, String phoneNumber,
                    String title, Double salary, Employee employeeManager, Boolean active)
    {
        this.firstname = firstname;
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