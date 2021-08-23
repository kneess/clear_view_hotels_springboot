package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    @NotBlank(message = "Must enter in first name")
    String firstName;
    @NonNull
    @NotBlank(message = "Must enter in last name")
    String lastName;
    @NonNull
    @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "Not a valid email")
    String email;
    @NonNull
    @NotBlank(message = "Must enter in phone number")
    String phoneNumber;
    @NonNull
    Boolean active;
    @ManyToOne
    Hotel hotel;
    @OneToOne
    Room room;
}