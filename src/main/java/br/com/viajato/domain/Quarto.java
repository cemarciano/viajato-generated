package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
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
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "capacidade", nullable = false)
    private Long capacidade;

    @NotNull
    @Column(name = "preco", nullable = false)
    private Long preco;

    @OneToOne
    @JoinColumn(unique = true)
    private Hotel hotel;

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

    public String getNome() {
        return nome;
    }

    public Quarto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCapacidade() {
        return capacidade;
    }

    public Quarto capacidade(Long capacidade) {
        this.capacidade = capacidade;
        return this;
    }

    public void setCapacidade(Long capacidade) {
        this.capacidade = capacidade;
    }

    public Long getPreco() {
        return preco;
    }

    public Quarto preco(Long preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(Long preco) {
        this.preco = preco;
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
            ", nome='" + getNome() + "'" +
            ", capacidade=" + getCapacidade() +
            ", preco=" + getPreco() +
            "}";
    }
}
