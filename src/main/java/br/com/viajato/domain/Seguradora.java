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
 * A Seguradora.
 */
@Entity
@Table(name = "seguradora")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Seguradora implements Serializable {

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

    @OneToMany(mappedBy = "seguradora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contrato> contratoes = new HashSet<>();

    @OneToMany(mappedBy = "seguradora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Telefone> telefones = new HashSet<>();

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

    public Seguradora nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Seguradora endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public Seguradora contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public Seguradora addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setSeguradora(this);
        return this;
    }

    public Seguradora removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setSeguradora(null);
        return this;
    }

    public void setContratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public Seguradora telefones(Set<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public Seguradora addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setSeguradora(this);
        return this;
    }

    public Seguradora removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setSeguradora(null);
        return this;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
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
        Seguradora seguradora = (Seguradora) o;
        if (seguradora.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seguradora.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seguradora{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
