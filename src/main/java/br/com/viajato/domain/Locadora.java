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
 * A Locadora.
 */
@Entity
@Table(name = "locadora")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Locadora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "locadora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Carro> carros = new HashSet<>();

    @OneToOne(mappedBy = "locadora")
    @JsonIgnore
    private Carro carro;

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

    public Locadora nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Carro> getCarros() {
        return carros;
    }

    public Locadora carros(Set<Carro> carros) {
        this.carros = carros;
        return this;
    }

    public Locadora addCarro(Carro carro) {
        this.carros.add(carro);
        carro.setLocadora(this);
        return this;
    }

    public Locadora removeCarro(Carro carro) {
        this.carros.remove(carro);
        carro.setLocadora(null);
        return this;
    }

    public void setCarros(Set<Carro> carros) {
        this.carros = carros;
    }

    public Carro getCarro() {
        return carro;
    }

    public Locadora carro(Carro carro) {
        this.carro = carro;
        return this;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
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
        Locadora locadora = (Locadora) o;
        if (locadora.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locadora.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Locadora{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
