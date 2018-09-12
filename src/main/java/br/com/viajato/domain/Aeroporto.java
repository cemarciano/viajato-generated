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
 * A Aeroporto.
 */
@Entity
@Table(name = "aeroporto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aeroporto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @OneToMany(mappedBy = "aeroporto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Voo> origems = new HashSet<>();

    @OneToMany(mappedBy = "aeroporto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Voo> destinos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("aeroportos")
    private Cidade cidade;

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

    public Aeroporto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public Aeroporto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Set<Voo> getOrigems() {
        return origems;
    }

    public Aeroporto origems(Set<Voo> voos) {
        this.origems = voos;
        return this;
    }

    public Aeroporto addOrigem(Voo voo) {
        this.origems.add(voo);
        voo.setAeroporto(this);
        return this;
    }

    public Aeroporto removeOrigem(Voo voo) {
        this.origems.remove(voo);
        voo.setAeroporto(null);
        return this;
    }

    public void setOrigems(Set<Voo> voos) {
        this.origems = voos;
    }

    public Set<Voo> getDestinos() {
        return destinos;
    }

    public Aeroporto destinos(Set<Voo> voos) {
        this.destinos = voos;
        return this;
    }

    public Aeroporto addDestino(Voo voo) {
        this.destinos.add(voo);
        voo.setAeroporto(this);
        return this;
    }

    public Aeroporto removeDestino(Voo voo) {
        this.destinos.remove(voo);
        voo.setAeroporto(null);
        return this;
    }

    public void setDestinos(Set<Voo> voos) {
        this.destinos = voos;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Aeroporto cidade(Cidade cidade) {
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
        Aeroporto aeroporto = (Aeroporto) o;
        if (aeroporto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aeroporto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aeroporto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
