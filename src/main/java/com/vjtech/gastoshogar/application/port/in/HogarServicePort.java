package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.hogar.HogarRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.hogar.HogarResponse;
import java.util.List;
import java.util.UUID;

public interface HogarServicePort {
    HogarResponse create(HogarRequest req, UUID userId);
    List<HogarResponse> getAll(UUID userId);
    HogarResponse getById(UUID id, UUID userId);
    HogarResponse update(UUID id, HogarRequest req, UUID userId);
    void delete(UUID id, UUID userId);
}