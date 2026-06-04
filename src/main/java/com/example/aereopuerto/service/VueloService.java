package com.example.aereopuerto.service;

import com.example.aereopuerto.dto.VueloDTO;
import com.example.aereopuerto.model.Aeropuerto;
import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.model.Vuelo;
import com.example.aereopuerto.repository.AeropuertoRepository;
import com.example.aereopuerto.repository.AvionRepository;
import com.example.aereopuerto.repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VueloService {

    @Autowired
    private final VueloRepository vueloRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final AvionRepository avionRepository;

    /**
     * Cacheable: Si el vuelo existe en Redis (key = id), lo devuelve inmediatamente sin tocar MySQL.
     * Si no existe, lo busca en MySQL, lo devuelve, y automaticamente lo guarda en Redis.
     */
    @Cacheable(value = "vuelos", key = "#id")
    public Vuelo obtenerVueloPorId(Integer id) {
        System.out.println("Buscando vuelo " + id);
        return vueloRepository.findById(id).orElseThrow(() -> new RuntimeException("Vuelo no encontrado. ID: " + id));
    }

    /**
     * CachePut: Actualiza la base de datos MySQL e INMEDIATAMENTE actualiza/inserta el valor en Redis.
     * De esta forma, garantizamos que los datos nunca estén "viejos" (desincronizados).
     */

    @CachePut(value = "vuelos", key = "#result.id")
    @CacheEvict(value = "vuelos", key = "'todos'")
    public Vuelo crearVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    @CachePut(value = "vuelos", key = "#result.id")
    @CacheEvict(value = "vuelos", key = "'todos'")
    public Vuelo actualizarVuelo(Integer id, VueloDTO vueloDTO) {

        Vuelo vuelo = vueloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Vuelo no encontrado. ID: " + id));

        Aeropuerto origen = aeropuertoRepository.findById(vueloDTO.getAeropuertoOrigenId())
                .orElseThrow(() -> new RuntimeException(
                        "Aeropuerto origen no encontrado. ID: " + vueloDTO.getAeropuertoOrigenId()));

        Aeropuerto destino = aeropuertoRepository.findById(vueloDTO.getAeropuertoDestinoId())
                .orElseThrow(() -> new RuntimeException(
                        "Aeropuerto destino no encontrado. ID: " + vueloDTO.getAeropuertoDestinoId()));

        Avion avion = avionRepository.findById(vueloDTO.getAvionId())
                .orElseThrow(() -> new RuntimeException(
                        "Avión no encontrado. ID: " + vueloDTO.getAvionId()));

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

        return vueloRepository.save(vuelo);
    }
    /**
     * CacheEvict: Al eliminar un registro de MySQL, lo borramos tambien de la cache de Redis.
     */
    @Caching(evict = {
            @CacheEvict(value = "vuelos", key = "#id"),
            @CacheEvict(value = "vuelos", key = "'todos'")
    })
    public void eliminarVuelo(Integer id) {
        System.out.println("Eliminando vuelo " + id);
        vueloRepository.deleteById(id);
    }

    /**
     * Cacheable para una lista.
     * Nota: Cachear listas enteras puede ser complejo de invalidar si cambian mucho.
     * En este caso cacheamos bajo la key "todos" dentro del namespace "vuelos".
     */
    @Cacheable(value = "vuelos", key = "'todos'")
    public List<Vuelo> obtenerTodosLosVuelos() {
        return vueloRepository.findAll();
    }

    /**
     * Si modificamos cualquier vuelo, debemos vaciar la cache de la "lista entera"
     * para no retornar listas oxidadas, aunque el vuelo individual sí se actualice.
     */
    @CacheEvict(value = "vuelos", key = "'todos'")
    public void invalidarListaDeVuelos() {
        System.out.println("Limpiando la cachee");
    }
}