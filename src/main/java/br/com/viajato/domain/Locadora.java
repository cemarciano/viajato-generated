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

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToMany(mappedBy = "locadora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Telefone> telefones = new HashSet<>();

    @OneToMany(mappedBy = "locadora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Veiculo> veiculos = new HashSet<>();

    @OneToMany(mappedBy = "locadora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contrato> contratoes = new HashSet<>();

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

    public Endereco getEndereco() {
        return endereco;
    }

    public Locadora endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public Locadora telefones(Set<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public Locadora addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setLocadora(this);
        return this;
    }

    public Locadora removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setLocadora(null);
        return this;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public Set<Veiculo> getVeiculos() {
        return veiculos;
    }

    public Locadora veiculos(Set<Veiculo> veiculos) {
        this.veiculos = veiculos;
        return this;
    }

    public Locadora addVeiculo(Veiculo veiculo) {
        this.veiculos.add(veiculo);
        veiculo.setLocadora(this);
        return this;
    }

    public Locadora removeVeiculo(Veiculo veiculo) {
        this.veiculos.remove(veiculo);
        veiculo.setLocadora(null);
        return this;
    }

    public void setVeiculos(Set<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public Locadora contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public Locadora addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setLocadora(this);
        return this;
    }

    public Locadora removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setLocadora(null);
        return this;
    }

    public void setContratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
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
