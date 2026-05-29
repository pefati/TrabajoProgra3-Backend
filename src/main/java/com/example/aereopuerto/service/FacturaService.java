package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Factura;
import com.example.aereopuerto.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaService {

    @Autowired
    private final FacturaRepository facturaRepository;

    @Cacheable(value = "facturas", key = "#id")
    public Factura obtenerFacturaPorId(Integer id) {
        return facturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Factura no encontrada. ID: " + id));
    }

    @Cacheable(value = "facturas", key = "'todos'")
    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    @CachePut(value = "facturas", key = "#result.id_factura")
    public Factura crearOActualizarFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    @CacheEvict(value = "facturas", key = "#id")
    public void eliminarFactura(Integer id) {
        facturaRepository.deleteById(id);
    }

    @CacheEvict(value = "facturas", key = "'todos'")
    public void invalidarListaDeFacturas() {
    }
}
