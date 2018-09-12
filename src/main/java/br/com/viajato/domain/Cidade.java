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
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aeroporto> aeroportos = new HashSet<>();

    @OneToMany(mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Endereco> enderecos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("cidades")
    private Estado estado;

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

    public Cidade nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Aeroporto> getAeroportos() {
        return aeroportos;
    }

    public Cidade aeroportos(Set<Aeroporto> aeroportos) {
        this.aeroportos = aeroportos;
        return this;
    }

    public Cidade addAeroporto(Aeroporto aeroporto) {
        this.aeroportos.add(aeroporto);
        aeroporto.setCidade(this);
        return this;
    }

    public Cidade removeAeroporto(Aeroporto aeroporto) {
        this.aeroportos.remove(aeroporto);
        aeroporto.setCidade(null);
        return this;
    }

    public void setAeroportos(Set<Aeroporto> aeroportos) {
        this.aeroportos = aeroportos;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public Cidade enderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }

    public Cidade addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setCidade(this);
        return this;
    }

    public Cidade removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.setCidade(null);
        return this;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Estado getEstado() {
        return estado;
    }

    public Cidade estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
        Cidade cidade = (Cidade) o;
        if (cidade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cidade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
