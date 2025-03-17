package com.example.cryptography.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 355)
    private String userDocument;

    @Column(length = 355)
    private String creditCardToken;

    private Long paymentValue;

    public PaymentEntity(String userDocument, String creditCardToken, Long paymentValue) {
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.paymentValue = paymentValue;
    }
}