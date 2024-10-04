package br.com.fiap.ecommerce.controller;

import br.com.fiap.ecommerce.dtos.cliente.ClienteRequestCreateDto;
import br.com.fiap.ecommerce.dtos.cliente.ClienteRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.cliente.ClienteResponseDto;
import br.com.fiap.ecommerce.dtos.pedido.PedidoRequestCreateDto;
import br.com.fiap.ecommerce.dtos.pedido.PedidoRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.pedido.PedidoResponseDto;
import br.com.fiap.ecommerce.service.ClienteService;
import br.com.fiap.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> list() {
        List<PedidoResponseDto> dtos = pedidoService.list()
                .stream()
                .map(e -> new PedidoResponseDto().toDto(e))
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> create(@RequestBody PedidoRequestCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new PedidoResponseDto().toDto(
                                pedidoService.save(dto.toModel()))
                );
    }

    @PutMapping("{id}")
    public ResponseEntity<PedidoResponseDto> update(
            @PathVariable Long id,
            @RequestBody PedidoRequestUpdateDto dto) {
        if (pedidoService.existsById(id)){
            throw new RuntimeException("Id inexistente");
        }
        return ResponseEntity.ok()
                .body(
                        new PedidoResponseDto().toDto(
                                pedidoService.save(dto.toModel(id)))
                );
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        if (pedidoService.existsById(id)){
            throw new RuntimeException("Id inexistente");
        }

        pedidoService.delete(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<PedidoResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(
                        pedidoService
                                .findById(id)
                                .map(e -> new PedidoResponseDto().toDto(e))
                                .orElseThrow(() -> new RuntimeException("Id inexistente"))
                );

    }
}