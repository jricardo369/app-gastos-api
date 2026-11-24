package com.vjtech.gastoshogar.application.port.in;

import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoRequest;
import com.vjtech.gastoshogar.adapter.rest.dto.respaldo.RespaldoResponse;

public interface RespaldoServicePort {
    RespaldoResponse guardar(String username, RespaldoRequest req);
    RespaldoResponse obtener(String username);
}
