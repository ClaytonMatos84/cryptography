package com.example.cryptography.service;

import com.example.cryptography.dto.PaymentDto;
import com.example.cryptography.entity.PaymentEntity;
import com.example.cryptography.repository.PaymentRepository;
import com.example.cryptography.utils.CryptoUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CryptoUtils cryptoUtils;

    public PaymentDto create(PaymentDto paymentDto) throws Exception {
        String userDocument = cryptoUtils.encrypt(String.valueOf(paymentDto.userDocument()));
        String creditCardToken = cryptoUtils.encrypt(String.valueOf(paymentDto.creditCardToken()));

        PaymentEntity saved = paymentRepository.save(new PaymentEntity(userDocument, creditCardToken, paymentDto.value()));
        return new PaymentDto(saved.getId(), paymentDto);
    }

    public PaymentDto getById(Long id) throws Exception {
        PaymentEntity paymentEntity = paymentRepository.findById(id).orElseThrow();
        String userDocument = cryptoUtils.decrypt(paymentEntity.getUserDocument());
        String creditCardToken = cryptoUtils.decrypt(paymentEntity.getCreditCardToken());

        return new PaymentDto(paymentEntity.getId(), Long.valueOf(userDocument), Long.valueOf(creditCardToken), paymentEntity.getPaymentValue());
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) throws Exception {
        PaymentEntity paymentEntity = paymentRepository.findById(id).orElseThrow();
        if (paymentDto.userDocument() != null && paymentDto.userDocument() > 0)
            paymentEntity.setUserDocument(cryptoUtils.encrypt(String.valueOf(paymentDto.userDocument())));
        if (paymentDto.creditCardToken() != null && paymentDto.creditCardToken() > 0)
            paymentEntity.setCreditCardToken(cryptoUtils.encrypt(String.valueOf(paymentDto.creditCardToken())));
        if (paymentDto.value() != null && paymentDto.value() > 0)
            paymentEntity.setPaymentValue(paymentDto.value());

        PaymentEntity updated = paymentRepository.save(paymentEntity);
        String userDocument = cryptoUtils.decrypt(updated.getUserDocument());
        String creditCardToken = cryptoUtils.decrypt(updated.getCreditCardToken());
        return new PaymentDto(updated.getId(), Long.valueOf(userDocument), Long.valueOf(creditCardToken), updated.getPaymentValue());
    }

    public void remove(Long id) {
        PaymentEntity paymentEntity = paymentRepository.findById(id).orElseThrow();
        paymentRepository.delete(paymentEntity);
    }
}
