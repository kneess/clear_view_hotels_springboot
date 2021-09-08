package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "employees")
public class EmployeeProfile implements Serializable {
    static final long serialVersionUID = 6381462249347345007L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull @OneToOne
    User user;
    @NonNull @NotBlank(message = "Must enter in address")
    String address;
    @NonNull @NotBlank(message = "Must enter in city")
    String city;
    @NonNull @NotBlank(message = "Must enter in state")
    String state;
    @NonNull @NotBlank(message = "Must enter in zip code")
    String zipCode;
    @NonNull @Pattern(regexp ="[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "Please follow the format")
    String phoneNumber;
    @NonNull @NotBlank(message = "Must enter in title")
    String title;
    @NonNull @Min(value = 12000, message = "must be equal or greater than 12000")
    Double salary;
    @OneToOne
    EmployeeProfile employeeManager;
    @NonNull @NotNull
    Boolean active;
}