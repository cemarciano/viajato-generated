package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Quarto.
 */
@Entity
@Table(name = "quarto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Quarto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

    @NotNull
    @Column(name = "diaria", nullable = false)
    private Integer diaria;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "quarto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reserva> reservas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("quartos")
    private Hotel hotel;

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

    public Quarto tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public Quarto capacidade(Integer capacidade) {
        this.capacidade = capacidade;
        return this;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getDiaria() {
        return diaria;
    }

    public Quarto diaria(Integer diaria) {
        this.diaria = diaria;
        return this;
    }

    public void setDiaria(Integer diaria) {
        this.diaria = diaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public Quarto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public Quarto reservas(Set<Reserva> reservas) {
        this.reservas = reservas;
        return this;
    }

    public Quarto addReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.setQuarto(this);
        return this;
    }

    public Quarto removeReserva(Reserva reserva) {
        this.reservas.remove(reserva);
        reserva.setQuarto(null);
        return this;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Quarto hotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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
        Quarto quarto = (Quarto) o;
        if (quarto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quarto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Quarto{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", capacidade=" + getCapacidade() +
            ", diaria=" + getDiaria() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
