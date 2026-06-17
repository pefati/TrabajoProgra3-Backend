package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.FacturaInvalidaException;
import com.example.aereopuerto.dto.FacturaDTO;
import com.example.aereopuerto.model.Factura;
import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.Reserva;
import com.example.aereopuerto.repository.FacturaRepository;
import com.example.aereopuerto.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaService {

    @Autowired
    private final FacturaRepository facturaRepository;
    private final ReservaRepository reservaRepository;

    public Factura obtenerFacturaPorId(Integer id) {
        return facturaRepository.findById(id).orElseThrow(() -> new FacturaInvalidaException("Factura no encontrada. ID: " + id));
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public List<Factura> obtenerFacturasPorPersona(Persona persona) {
        return facturaRepository.findByReservaPersona(persona);
    }

    public Factura crearFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    public Factura actualizarFactura(Integer id, FacturaDTO facturaDTO) {

        Factura f = facturaRepository.findById(id)
                .orElseThrow(() -> new FacturaInvalidaException("Factura no encontrada. ID: " + id));

        Reserva reserva = reservaRepository.findById(facturaDTO.getReservaId())
                .orElseThrow(() -> new RuntimeException(
                        "Reserva no encontrada. ID: " + facturaDTO.getReservaId()));

        f.setSituacionFiscal(facturaDTO.getSituacionFiscal());
        f.setReserva(reserva);
        f.setCUIL(facturaDTO.getCUIL());
        f.setFechaEmision(facturaDTO.getFechaEmision());
        f.setMetodoDePago(facturaDTO.getMetodoDePago());

        return facturaRepository.save(f);
    }

    public void eliminarFactura(Integer id) {
        facturaRepository.deleteById(id);
    }
}
