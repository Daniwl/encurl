package com.daniwl.encurl.dto.event;

import java.time.LocalDateTime;

public record MetricaLinkEvent(String urlId, LocalDateTime dataHora) {
}
