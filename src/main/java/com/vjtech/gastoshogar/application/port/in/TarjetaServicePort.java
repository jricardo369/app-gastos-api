package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.MovimientoTarjetaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.TarjetaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.tarjeta.TarjetaResponse;
import java.util.List;
import java.util.UUID;

public interface TarjetaServicePort {
    List<TarjetaResponse> getAll(UUID hogarId);
    TarjetaResponse create(TarjetaRequest req, UUID hogarId);
    TarjetaResponse getById(UUID id, UUID hogarId);
    TarjetaResponse update(UUID id, TarjetaRequest req);
    void delete(UUID id);
    List<MovimientoTarjetaResponse> getMovimientos(UUID tarjetaId, String estado, String tipo);
}