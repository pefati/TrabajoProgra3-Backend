package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.AsientoInvalidoException;
import com.example.aereopuerto.dto.AsientoDTO;
import com.example.aereopuerto.model.Asiento;
import com.example.aereopuerto.repository.AsientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsientoService {

    private final AsientoRepository asientoRepository;


    public List<AsientoDTO> obtenerAsientosPorVuelo(Integer vueloId) {

        List<Asiento> asientos = asientoRepository.findByVueloId(vueloId);

        return asientos.stream()
                .map(this::toDTO)
                .toList();
    }



    public Asiento buscarPorId(Integer asientoId) {

        return asientoRepository.findById(asientoId)
                .orElseThrow(() ->
                        new AsientoInvalidoException("Asiento no encontrado"));
    }


    private AsientoDTO toDTO(Asiento asiento) {

        AsientoDTO dto = new AsientoDTO();

        dto.setId(asiento.getId());
        dto.setCodigo(asiento.getCodigo());
        dto.setOcupado(asiento.getOcupado());
        dto.setPrecioExtra(asiento.getPrecioExtra());

        if (asiento.getVuelo() != null) {
            dto.setVueloId(asiento.getVuelo().getId());
        }

        return dto;
    }
}

