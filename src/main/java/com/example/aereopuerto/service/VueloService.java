package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.AeropuertoInvalidoException;
import com.example.aereopuerto.Exceptions.AvionInvalidoException;
import com.example.aereopuerto.Exceptions.VueloInvalidoException;
import com.example.aereopuerto.Specifications.VueloSpecification;
import com.example.aereopuerto.dto.VueloDTO;
import com.example.aereopuerto.model.Aeropuerto;
import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.model.Vuelo;
import com.example.aereopuerto.model.enums.estadoVuelo;
import com.example.aereopuerto.repository.AeropuertoRepository;
import com.example.aereopuerto.repository.AvionRepository;
import com.example.aereopuerto.repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VueloService {

    @Autowired
    private final VueloRepository vueloRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final AvionRepository avionRepository;

    @Cacheable(value = "vuelos", key = "#id")
    public Vuelo obtenerVueloPorId(Integer id) {
        System.out.println("Buscando vuelo " + id);
        return vueloRepository.findById(id).orElseThrow(() -> new VueloInvalidoException("Vuelo no encontrado. ID: " + id));
    }

    @CachePut(value = "vuelos", key = "#result.id")
    @CacheEvict(value = "vuelos", key = "'todos'")
    public Vuelo crearVuelo(Vuelo vuelo) {
        validarDisponibilidadAvion(
                vuelo.getAvion().getId(),
                vuelo.getFechaSalida(),
                vuelo.getFechaLlegada(), // agregás este
                vuelo.getHoraSalida(),
                vuelo.getHoraLlegada(),
                -1
        );

        validarDatosVuelo(vuelo);

        return vueloRepository.save(vuelo);
    }

    @CachePut(value = "vuelos", key = "#result.id")
    @CacheEvict(value = "vuelos", key = "'todos'")
    public Vuelo actualizarVuelo(Integer id, VueloDTO vueloDTO) {

        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new VueloInvalidoException("Vuelo no encontrado. ID: " + id));

        Aeropuerto origen = aeropuertoRepository.findById(vueloDTO.getAeropuertoOrigenId())
                .orElseThrow(() -> new AeropuertoInvalidoException(
                        "Aeropuerto origen no encontrado. ID: " + vueloDTO.getAeropuertoOrigenId()));

        Aeropuerto destino = aeropuertoRepository.findById(vueloDTO.getAeropuertoDestinoId())
                .orElseThrow(() -> new AeropuertoInvalidoException(
                        "Aeropuerto destino no encontrado. ID: " + vueloDTO.getAeropuertoDestinoId()));

        Avion avion = avionRepository.findById(vueloDTO.getAvionId())
                .orElseThrow(() -> new AvionInvalidoException(
                        "Avión no encontrado. ID: " + vueloDTO.getAvionId()));

        validarDisponibilidadAvion(
                vueloDTO.getAvionId(),
                vueloDTO.getFechaSalida(),
                vueloDTO.getFechaLlegada(), // agregás este
                vueloDTO.getHoraSalida(),
                vueloDTO.getHoraLlegada(),
                id
        );

        vuelo.setAeropuertoOrigen(origen);
        vuelo.setAeropuertoDestino(destino);
        vuelo.setAvion(avion);
        vuelo.setFechaSalida(vueloDTO.getFechaSalida());
        vuelo.setFechaLlegada(vueloDTO.getFechaLlegada());
        vuelo.setHoraSalida(vueloDTO.getHoraSalida());
        vuelo.setHoraLlegada(vueloDTO.getHoraLlegada());
        vuelo.setEstado(vueloDTO.getEstado());
        vuelo.setPrecioVuelo(vueloDTO.getPrecioVuelo());
        vuelo.setEscala(vueloDTO.getEscala());

        validarDatosVuelo(vuelo);

        return vueloRepository.save(vuelo);
    }

    private void validarDatosVuelo(Vuelo vuelo) {

        // Precio mayor a 0
        if (vuelo.getPrecioVuelo() == null || vuelo.getPrecioVuelo() <= 0) {
            throw new VueloInvalidoException(
                    "El precio del vuelo debe ser mayor a 0.");
        }

        // Aeropuertos distintos
        if (vuelo.getAeropuertoOrigen().getId()
                .equals(vuelo.getAeropuertoDestino().getId())) {

            throw new VueloInvalidoException(
                    "El aeropuerto de origen y destino deben ser distintos.");
        }

        // Fecha de llegada igual o posterior a la de salida
        if (vuelo.getFechaLlegada().isBefore(vuelo.getFechaSalida())) {
            throw new VueloInvalidoException(
                    "La fecha de llegada no puede ser anterior a la fecha de salida.");
        }

        // Si son el mismo día, la hora de llegada debe ser posterior
        if (vuelo.getFechaSalida().equals(vuelo.getFechaLlegada())
                && !vuelo.getHoraLlegada().isAfter(vuelo.getHoraSalida())) {

            throw new VueloInvalidoException(
                    "La hora de llegada debe ser posterior a la hora de salida.");
        }
    }

    private void validarDisponibilidadAvion(
            Integer avionId,
            LocalDate fechaSalida,
            LocalDate fechaLlegada,
            LocalTime horaSalida,
            LocalTime horaLlegada,
            Integer excludeId) {

        LocalDateTime salidaDateTime = LocalDateTime.of(fechaSalida, horaSalida);
        LocalDateTime llegadaDateTime = LocalDateTime.of(fechaLlegada, horaLlegada);

        if (!llegadaDateTime.isAfter(salidaDateTime)) {
            throw new VueloInvalidoException(
                    "La fecha y hora de llegada debe ser posterior a la fecha y hora de salida."
            );
        }

        boolean tieneConflicto = vueloRepository.existeConflictoHorario(
                avionId, fechaSalida, horaSalida, horaLlegada, excludeId
        );

        if (tieneConflicto) {
            throw new AvionInvalidoException(
                    "El avión ya tiene un vuelo asignado en ese horario para la fecha " + fechaSalida
            );
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "vuelos", key = "#id"),
            @CacheEvict(value = "vuelos", key = "'todos'")
    })
    public void eliminarVuelo(Integer id) {
        System.out.println("Eliminando vuelo " + id);
        vueloRepository.deleteById(id);
    }

    @Cacheable(value = "vuelos", key = "'todos'")
    public List<Vuelo> obtenerTodosLosVuelos() {
        return vueloRepository.findAll();
    }

    @CacheEvict(value = "vuelos", key = "'todos'")
    public void invalidarListaDeVuelos() {
        System.out.println("Limpiando la cache");
    }

    public List<Vuelo> buscarVuelosConFiltrosAvanzados(
            String ciudadOrigen,
            String ciudadDestino,
            LocalDateTime fechaSalida,
            LocalDateTime fechaLlegada,
            Double precioMaximo,
            Boolean escala,
            estadoVuelo estado,
            String paisDestino) {

        Specification<Vuelo> spec = Specification
                .where(VueloSpecification.porCiudadOrigen(ciudadOrigen))
                .and(VueloSpecification.porCiudadDestino(ciudadDestino))
                .and(VueloSpecification.porPaisDestino(paisDestino))
                .and(VueloSpecification.porFechaSalidaDesde(fechaSalida))
                .and(VueloSpecification.porFechaLlegadaHasta(fechaLlegada))
                .and(VueloSpecification.precioMenorOIgualA(precioMaximo))
                .and(VueloSpecification.porEscala(escala))
                .and(VueloSpecification.porEstado(estado));

        return vueloRepository.findAll(spec);
    }


    public List<Vuelo> buscarVuelosConFiltrosAvanzados(
            String origen,
            String destino,
            LocalDateTime fechaSalida,
            LocalDateTime fechaLlegada,
            Double precioMaximo,
            Boolean escala,
            estadoVuelo estado) {

        Specification<Vuelo> spec = Specification
                .where(VueloSpecification.porOrigenGeneral(origen))
                .and(VueloSpecification.porDestinoGeneral(destino))
                .and(VueloSpecification.porFechaSalidaDesde(fechaSalida))
                .and(VueloSpecification.porFechaLlegadaHasta(fechaLlegada))
                .and(VueloSpecification.precioMenorOIgualA(precioMaximo))
                .and(VueloSpecification.porEscala(escala))
                .and(VueloSpecification.porEstado(estado));

        return vueloRepository.findAll(spec);
    }
}