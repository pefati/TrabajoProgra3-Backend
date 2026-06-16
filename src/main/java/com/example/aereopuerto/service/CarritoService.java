package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.CarritoInvalidoException;
import com.example.aereopuerto.Exceptions.PersonaInvalidaException;
import com.example.aereopuerto.Exceptions.VueloInvalidoException;
import com.example.aereopuerto.dto.CarritoDTO;
import com.example.aereopuerto.dto.CarritoItemDTO;
import com.example.aereopuerto.model.Carrito;
import com.example.aereopuerto.model.CarritoItem;
import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.Vuelo;
import com.example.aereopuerto.model.enums.ClasesVuelo;
import com.example.aereopuerto.repository.CarritoItemRepository;
import com.example.aereopuerto.repository.CarritoRepository;
import com.example.aereopuerto.repository.PersonaRepository;
import com.example.aereopuerto.repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final PersonaRepository personaRepository;
    private final VueloRepository vueloRepository;

    @Cacheable(value = "carrito", key = "#personaId")
    public CarritoDTO getCarritoByPersonaId(Integer personaId) {
        Carrito carrito = createOrFindCarrito(personaId);
        return mapToDTO(carrito);
    }

    @CacheEvict(value = "carrito", key = "#personaId")
    public CarritoItemDTO addItem(Integer personaId, Integer vueloId, int cantidad, ClasesVuelo clase) {
        Carrito carrito = createOrFindCarrito(personaId);

        Vuelo vuelo = vueloRepository.findById(vueloId)
                .orElseThrow(() -> new VueloInvalidoException("Vuelo no encontrado con id: " + vueloId));

        if (cantidad <= 0) {
            throw new CarritoInvalidoException("La cantidad debe ser mayor a 0.");
        }

        CarritoItem item = CarritoItem.builder()
                .carrito(carrito)
                .vuelo(vuelo)
                .cantidad(cantidad)
                .claseVuelo(clase)
                .build();

        return mapItemToDTO(carritoItemRepository.save(item));
    }

    @CacheEvict(value = "carrito", key = "#personaId")
    public void removeItem(Integer personaId, Integer itemId) {
        Carrito carrito = createOrFindCarrito(personaId);

        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new CarritoInvalidoException("Item del carrito no encontrado con id: " + itemId));

        if(carrito.getId().equals(item.getCarrito().getId())) {
            carritoItemRepository.delete(item);
        }
        else{
            throw new CarritoInvalidoException("El item " + item.getId() + " no se encuentra en el carrito.");
        }
    }

    @CacheEvict(value = "carrito", key = "#personaId")
    public void clearCarrito(Integer personaId, Integer carritoId) {

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new CarritoInvalidoException("Carrito no encontrado con id: " + carritoId));
        if (personaId == carrito.getPersona().getId()) {
            carrito.getItems().clear();
            carritoRepository.save(carrito);
        }
        else{
            throw new CarritoInvalidoException("El carrito " + carrito.getId() + " no coincide con el de la persona " +personaId);
        }
    }

    private Carrito createOrFindCarrito (Integer personaId){
        return carritoRepository.findByPersonaId(personaId).orElseGet(() -> {
            Persona persona = personaRepository.findById(personaId)
                    .orElseThrow(() -> new PersonaInvalidaException("Persona no encontrado con id: " + personaId));
            Carrito newCarrito = Carrito.builder().persona(persona).build();
            return carritoRepository.save(newCarrito);
        });
    }

    private CarritoDTO mapToDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        dto.setPersonaId(carrito.getPersona().getId());
        dto.setItems(carrito.getItems().stream().map(this::mapItemToDTO).toList());
        return dto;
    }

    private CarritoItemDTO mapItemToDTO(CarritoItem item) {
        CarritoItemDTO dto = new CarritoItemDTO();
        dto.setId(item.getId());
        dto.setCarritoId(item.getCarrito().getId());
        dto.setVueloId(item.getVuelo().getId());
        dto.setCantidad(item.getCantidad());
        dto.setClaseVuelo(item.getClaseVuelo());
        return dto;
    }
}
