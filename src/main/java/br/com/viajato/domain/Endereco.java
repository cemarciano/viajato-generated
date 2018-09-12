package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Endereco.
 */
@Entity
@Table(name = "endereco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Column(name = "complemento", nullable = false)
    private String complemento;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Locadora locadora;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Hotel hotel;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Seguradora seguradora;

    @ManyToOne
    @JsonIgnoreProperties("enderecos")
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public Endereco tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Endereco logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public Endereco numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public Endereco complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Locadora getLocadora() {
        return locadora;
    }

    public Endereco locadora(Locadora locadora) {
        this.locadora = locadora;
        return this;
    }

    public void setLocadora(Locadora locadora) {
        this.locadora = locadora;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Endereco hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Seguradora getSeguradora() {
        return seguradora;
    }

    public Endereco seguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
        return this;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Endereco cidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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
        Endereco endereco = (Endereco) o;
        if (endereco.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endereco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Endereco{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero=" + getNumero() +
            ", complemento='" + getComplemento() + "'" +
            "}";
    }
}
