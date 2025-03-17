package com.example.cryptography.controller;

import com.example.cryptography.dto.PaymentDto;
import com.example.cryptography.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity create(@RequestBody PaymentDto paymentDto) throws Exception {
        return ResponseEntity.ok(paymentService.create(paymentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long paymentId) throws Exception {
        return ResponseEntity.ok(paymentService.getById(paymentId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity partiallyUpdate(@PathVariable("id") Long paymentId,
                                          @RequestBody PaymentDto paymentDto) throws Exception {
        return ResponseEntity.ok(paymentService.updatePayment(paymentId, paymentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable("id") Long paymentId) throws Exception {
        paymentService.remove(paymentId);
        return ResponseEntity.noContent().build();
    }
}
