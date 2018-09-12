package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Hotel.
 */
@Entity
@Table(name = "hotel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "nota", nullable = false)
    private Integer nota;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToMany(mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Telefone> telefones = new HashSet<>();

    @OneToMany(mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Quarto> quartos = new HashSet<>();

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

    public Hotel nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNota() {
        return nota;
    }

    public Hotel nota(Integer nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Hotel endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public Hotel telefones(Set<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public Hotel addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setHotel(this);
        return this;
    }

    public Hotel removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setHotel(null);
        return this;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public Set<Quarto> getQuartos() {
        return quartos;
    }

    public Hotel quartos(Set<Quarto> quartos) {
        this.quartos = quartos;
        return this;
    }

    public Hotel addQuarto(Quarto quarto) {
        this.quartos.add(quarto);
        quarto.setHotel(this);
        return this;
    }

    public Hotel removeQuarto(Quarto quarto) {
        this.quartos.remove(quarto);
        quarto.setHotel(null);
        return this;
    }

    public void setQuartos(Set<Quarto> quartos) {
        this.quartos = quartos;
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
        Hotel hotel = (Hotel) o;
        if (hotel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hotel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hotel{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nota=" + getNota() +
            "}";
    }
}
