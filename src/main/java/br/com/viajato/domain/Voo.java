package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import br.com.viajato.domain.enumeration.Aeroporto;

/**
 * A Voo.
 */
@Entity
@Table(name = "voo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Long numero;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "origem", nullable = false)
    private Aeroporto origem;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "destino", nullable = false)
    private Aeroporto destino;

    @NotNull
    @Column(name = "partida", nullable = false)
    private Instant partida;

    @NotNull
    @Column(name = "chegada", nullable = false)
    private Instant chegada;

    @OneToOne
    @JoinColumn(unique = true)
    private LinhaAerea linhaAerea;

    @ManyToOne
    @JsonIgnoreProperties("voos")
    private LinhaAerea linhaAerea;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public Voo numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Aeroporto getOrigem() {
        return origem;
    }

    public Voo origem(Aeroporto origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(Aeroporto origem) {
        this.origem = origem;
    }

    public Aeroporto getDestino() {
        return destino;
    }

    public Voo destino(Aeroporto destino) {
        this.destino = destino;
        return this;
    }

    public void setDestino(Aeroporto destino) {
        this.destino = destino;
    }

    public Instant getPartida() {
        return partida;
    }

    public Voo partida(Instant partida) {
        this.partida = partida;
        return this;
    }

    public void setPartida(Instant partida) {
        this.partida = partida;
    }

    public Instant getChegada() {
        return chegada;
    }

    public Voo chegada(Instant chegada) {
        this.chegada = chegada;
        return this;
    }

    public void setChegada(Instant chegada) {
        this.chegada = chegada;
    }

    public LinhaAerea getLinhaAerea() {
        return linhaAerea;
    }

    public Voo linhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
        return this;
    }

    public void setLinhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
    }

    public LinhaAerea getLinhaAerea() {
        return linhaAerea;
    }

    public Voo linhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
        return this;
    }

    public void setLinhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Voo voo = (Voo) o;
        if (voo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Voo{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", origem='" + getOrigem() + "'" +
            ", destino='" + getDestino() + "'" +
            ", partida='" + getPartida() + "'" +
            ", chegada='" + getChegada() + "'" +
            "}";
    }
}
