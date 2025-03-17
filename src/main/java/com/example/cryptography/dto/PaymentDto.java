package com.example.cryptography.dto;

public record PaymentDto (Long id,
                          Long userDocument,
                          Long creditCardToken,
                          Long value) {
    public PaymentDto {
    }

    public PaymentDto(Long id, PaymentDto paymentDto) {
        this(id, paymentDto.userDocument(), paymentDto.creditCardToken(), paymentDto.value());
    }
}
