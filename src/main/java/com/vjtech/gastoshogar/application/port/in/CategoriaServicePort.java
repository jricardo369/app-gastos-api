package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.categoria.CategoriaRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.categoria.CategoriaResponse;
import java.util.List;
import java.util.UUID;

public interface CategoriaServicePort {
    List<CategoriaResponse> getAll(UUID hogarId);
    CategoriaResponse create(CategoriaRequest req, UUID hogarId);
    CategoriaResponse update(UUID id, CategoriaRequest req, UUID hogarId);
    void delete(UUID id, UUID hogarId);
}