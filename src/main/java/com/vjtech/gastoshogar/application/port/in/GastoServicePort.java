package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoFilter;
import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.gasto.GastoResponse;
import java.util.List;
import java.util.UUID;

public interface GastoServicePort {
    List<GastoResponse> getAll(UUID presupuestoId, GastoFilter filter);
    GastoResponse create(UUID presupuestoId, GastoRequest req);
    GastoResponse update(UUID id, GastoRequest req);
    void delete(UUID id);
    GastoResponse togglePaid(UUID id);
    GastoResponse markAsPaid(UUID id);
}