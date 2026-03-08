package com.daniwl.encurl.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MetricaLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Chave Estrangeira (Lazy para não carregar o Link inteiro atoa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id", nullable = false)
    private Link link;

    private LocalDateTime dataAcesso;

    // Construtor padrão exigido pelo JPA
    protected MetricaLink() {}

    public MetricaLink(Link link, LocalDateTime dataAcesso) {
        this.link = link;
        this.dataAcesso = dataAcesso;
    }

    public MetricaLink(Link link) {
        this.link = link;
        this.dataAcesso = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public LocalDateTime getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(LocalDateTime dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

}
