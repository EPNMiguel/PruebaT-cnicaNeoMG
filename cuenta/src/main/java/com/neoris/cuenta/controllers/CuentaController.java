package com.neoris.cuenta.controllers;

import com.neoris.cuenta.entities.Cuenta;
import com.neoris.cuenta.model.dto.CuentaRequest;
import com.neoris.cuenta.model.dto.CuentaResponse;
import com.neoris.cuenta.services.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public String createCuenta(@RequestBody CuentaRequest cuentaRequest) {
        return this.cuentaService.addCuenta(cuentaRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CuentaResponse> getAllCuentas() {
        return this.cuentaService.getAllCuentas();
    }

    @PutMapping
    public String updateCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.updateCuenta(cuenta);
    }

}
