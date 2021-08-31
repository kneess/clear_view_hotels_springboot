package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
public class User implements Serializable {
    static final long serialVersionUID = 6381462249347345007L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @NonNull @Column(unique = true) @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "Not a valid email")
    String email;
    @NonNull @NotBlank(message = "Must enter in password")
    String uPassword;
    @NonNull @NotBlank(message = "Must enter in first name")
    String firstName;
    @NonNull @NotBlank(message = "Must enter in last name")
    String lastName;
}
