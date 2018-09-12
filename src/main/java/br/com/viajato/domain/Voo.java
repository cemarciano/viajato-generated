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
 * A Voo.
 */
@Entity
@Table(name = "voo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Column(name = "partida", nullable = false)
    private String partida;

    @NotNull
    @Column(name = "chegada", nullable = false)
    private String chegada;

    @OneToMany(mappedBy = "voo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Passagem> passagems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("voos")
    private LinhaAerea linhaAerea;

    @ManyToOne
    @JsonIgnoreProperties("vemDes")
    private Aeroporto origem;

    @ManyToOne
    @JsonIgnoreProperties("vaiParas")
    private Aeroporto destino;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Voo numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPartida() {
        return partida;
    }

    public Voo partida(String partida) {
        this.partida = partida;
        return this;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getChegada() {
        return chegada;
    }

    public Voo chegada(String chegada) {
        this.chegada = chegada;
        return this;
    }

    public void setChegada(String chegada) {
        this.chegada = chegada;
    }

    public Set<Passagem> getPassagems() {
        return passagems;
    }

    public Voo passagems(Set<Passagem> passagems) {
        this.passagems = passagems;
        return this;
    }

    public Voo addPassagem(Passagem passagem) {
        this.passagems.add(passagem);
        passagem.setVoo(this);
        return this;
    }

    public Voo removePassagem(Passagem passagem) {
        this.passagems.remove(passagem);
        passagem.setVoo(null);
        return this;
    }

    public void setPassagems(Set<Passagem> passagems) {
        this.passagems = passagems;
    }

    public LinhaAerea getLinhaAerea() {
        return linhaAerea;
    }

    public Voo linhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
        return this;
    }

    public void setLinhaAerea(LinhaAerea linhaAerea) {
        this.linhaAerea = linhaAerea;
    }

    public Aeroporto getOrigem() {
        return origem;
    }

    public Voo origem(Aeroporto aeroporto) {
        this.origem = aeroporto;
        return this;
    }

    public void setOrigem(Aeroporto aeroporto) {
        this.origem = aeroporto;
    }

    public Aeroporto getDestino() {
        return destino;
    }

    public Voo destino(Aeroporto aeroporto) {
        this.destino = aeroporto;
        return this;
    }

    public void setDestino(Aeroporto aeroporto) {
        this.destino = aeroporto;
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
        Voo voo = (Voo) o;
        if (voo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Voo{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", partida='" + getPartida() + "'" +
            ", chegada='" + getChegada() + "'" +
            "}";
    }
}
