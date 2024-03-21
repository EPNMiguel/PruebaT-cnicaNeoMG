package com.neoris.cuenta.controllers;


import com.neoris.cuenta.model.dto.MovimientoRequest;
import com.neoris.cuenta.model.dto.MovimientoResponse;
import com.neoris.cuenta.services.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearMovimiento(@RequestBody MovimientoRequest movimientoRequest) {
        this.movimientoService.addMovimiento(movimientoRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovimientoResponse> getAllMovimientos() {
        return this.movimientoService.getAllMovimientos();
    }


}
