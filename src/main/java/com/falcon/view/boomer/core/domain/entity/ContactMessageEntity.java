package com.falcon.view.boomer.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", updatable = false, nullable = false)
    private String firstName;
    @Column(name = "last_name", updatable = false, nullable = false)
    private String lastName;
    @Column(name = "email", updatable = false, nullable = false)
    private String email;
    @Column(name = "phone_number", updatable = false, nullable = false)
    private String phoneNumber;
    @Column(name = "subject", updatable = false, nullable = false)
    private String subject;
    @Column(name = "message", updatable = false, nullable = false)
    private String message;
}
