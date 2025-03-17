package com.example.cryptography.controller;

import com.example.cryptography.utils.CryptoUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crypto")
@AllArgsConstructor
public class CryptoController {

    private final CryptoUtils cryptoUtils;

    @PostMapping("encrypt")
    public ResponseEntity encrypt(@RequestBody String value) {
        return ResponseEntity.ok(cryptoUtils.encrypt(value));
    }

    @PostMapping("decrypt")
    public ResponseEntity decrypt(@RequestBody String value) {
        return ResponseEntity.ok(cryptoUtils.decrypt(value));
    }
}
