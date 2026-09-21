package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.IngresoUpdateRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.PresupuestoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.PresupuestoResponse;
import com.vjtech.gastoshogar.adapter.rest.dto.presupuesto.ResumenResponse;
import java.util.List;
import java.util.UUID;

public interface PresupuestoServicePort {
    PresupuestoResponse create(PresupuestoRequest req, UUID hogarId);
    PresupuestoResponse getByHogarId(UUID hogarId);
    ResumenResponse getSummary(UUID hogarId, int anio, int mes);
    PresupuestoResponse updateIngresos(UUID presupuestoId, String quincena, List<IngresoUpdateRequest> ingresos);
}