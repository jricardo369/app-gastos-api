package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.deuda.DeudaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.DeudaResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.PagoDeudaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.deuda.PagoDeudaResponse;
import java.util.List;
import java.util.UUID;

public interface DeudaServicePort {
    List<DeudaResponse> getAll(UUID hogarId);
    DeudaResponse create(DeudaRequest req, UUID hogarId);
    DeudaResponse update(UUID id, DeudaRequest req);
    void delete(UUID id);
    PagoDeudaResponse addPago(UUID deudaId, PagoDeudaRequest req);
    DeudaResponse getSummary(UUID hogarId);
}