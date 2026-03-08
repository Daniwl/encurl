package com.daniwl.encurl.worker;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.daniwl.encurl.core.Encoder;
import com.daniwl.encurl.dto.event.MetricaLinkEvent;
import com.daniwl.encurl.model.Link;
import com.daniwl.encurl.model.MetricaLink;
import com.daniwl.encurl.repository.LinkRepository;
import com.daniwl.encurl.repository.MetricaLinkRepository;

@Component
public class MetricasWorker {

    private final MetricaLinkRepository metricaRepository;
    private final LinkRepository linkRepository;
    private final Encoder encoder;

    public MetricasWorker(MetricaLinkRepository metricaRepository, LinkRepository linkRepository, Encoder encoder) {
        this.metricaRepository = metricaRepository;
        this.linkRepository = linkRepository;
        this.encoder = encoder;
    }

    @RabbitListener(queuesToDeclare = @Queue("fila-metricas-clique"))
    public void processarClique(MetricaLinkEvent evento) {
        // 1. Decodifica a String (ex: "b") para o ID do banco (ex: 2)
        Long idBanco = encoder.decodificar(evento.urlId());
        
        // 2. Pega a referência do Link sem fazer SELECT no banco (otimização do JPA)
        Link linkRef = linkRepository.getReferenceById(idBanco);
        
        // 3. Salva a métrica
        MetricaLink metrica = new MetricaLink(linkRef, evento.dataHora());
        metricaRepository.save(metrica);
        
        System.out.println("SUCESSO - Métrica salva no banco para o link ID: " + idBanco);
    }
}