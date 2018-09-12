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
 * A Veiculo.
 */
@Entity
@Table(name = "veiculo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "fabricante", nullable = false)
    private String fabricante;

    @NotNull
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @NotNull
    @Column(name = "ano_modelo", nullable = false)
    private Integer anoModelo;

    @NotNull
    @Column(name = "ano_fabricacao", nullable = false)
    private Integer anoFabricacao;

    @NotNull
    @Column(name = "cor", nullable = false)
    private String cor;

    @NotNull
    @Column(name = "num_passageiros", nullable = false)
    private Integer numPassageiros;

    @OneToMany(mappedBy = "veiculo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Locacao> locacaos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("veiculos")
    private Locadora locadora;

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

    public Veiculo tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public Veiculo fabricante(String fabricante) {
        this.fabricante = fabricante;
        return this;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public Veiculo modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public Veiculo anoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
        return this;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public Veiculo anoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
        return this;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getCor() {
        return cor;
    }

    public Veiculo cor(String cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Integer getNumPassageiros() {
        return numPassageiros;
    }

    public Veiculo numPassageiros(Integer numPassageiros) {
        this.numPassageiros = numPassageiros;
        return this;
    }

    public void setNumPassageiros(Integer numPassageiros) {
        this.numPassageiros = numPassageiros;
    }

    public Set<Locacao> getLocacaos() {
        return locacaos;
    }

    public Veiculo locacaos(Set<Locacao> locacaos) {
        this.locacaos = locacaos;
        return this;
    }

    public Veiculo addLocacao(Locacao locacao) {
        this.locacaos.add(locacao);
        locacao.setVeiculo(this);
        return this;
    }

    public Veiculo removeLocacao(Locacao locacao) {
        this.locacaos.remove(locacao);
        locacao.setVeiculo(null);
        return this;
    }

    public void setLocacaos(Set<Locacao> locacaos) {
        this.locacaos = locacaos;
    }

    public Locadora getLocadora() {
        return locadora;
    }

    public Veiculo locadora(Locadora locadora) {
        this.locadora = locadora;
        return this;
    }

    public void setLocadora(Locadora locadora) {
        this.locadora = locadora;
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
        Veiculo veiculo = (Veiculo) o;
        if (veiculo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), veiculo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Veiculo{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", fabricante='" + getFabricante() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", anoModelo=" + getAnoModelo() +
            ", anoFabricacao=" + getAnoFabricacao() +
            ", cor='" + getCor() + "'" +
            ", numPassageiros=" + getNumPassageiros() +
            "}";
    }
}
