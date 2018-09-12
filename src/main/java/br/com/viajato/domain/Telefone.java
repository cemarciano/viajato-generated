package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Telefone.
 */
@Entity
@Table(name = "telefone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ddd", nullable = false)
    private Integer ddd;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @ManyToOne
    @JsonIgnoreProperties("telefones")
    private LinhaAerea linhaAerea;

    @ManyToOne
    @JsonIgnoreProperties("telefones")
    private Locadora locadora;

    @ManyToOne
    @JsonIgnoreProperties("telefones")
    private Hotel hotel;

    @ManyToOne
    @JsonIgnoreProperties("telefones")
    private Seguradora seguradora;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDdd() {
        return ddd;
    }

    public Telefone ddd(Integer ddd) {
        this.ddd = ddd;
        return this;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    public Integer getNumero() {
        return numero;
    }

    public Telefone numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LinhaAerea getLinhaAerea() {
        return linhaAerea;
    }

    public Telefone linhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
        return this;
    }

    public void setLinhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
    }

    public Locadora getLocadora() {
        return locadora;
    }

    public Telefone locadora(Locadora locadora) {
        this.locadora = locadora;
        return this;
    }

    public void setLocadora(Locadora locadora) {
        this.locadora = locadora;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Telefone hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Seguradora getSeguradora() {
        return seguradora;
    }

    public Telefone seguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
        return this;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
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
        Telefone telefone = (Telefone) o;
        if (telefone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telefone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Telefone{" +
            "id=" + getId() +
            ", ddd=" + getDdd() +
            ", numero=" + getNumero() +
            "}";
    }
}
