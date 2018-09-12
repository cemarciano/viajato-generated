package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Locacao.
 */
@Entity
@Table(name = "locacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Locacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private String inicio;

    @NotNull
    @Column(name = "duracao", nullable = false)
    private Integer duracao;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Integer valor;

    @ManyToOne
    @JsonIgnoreProperties("locacaos")
    private Veiculo veiculo;

    @ManyToOne
    @JsonIgnoreProperties("locacaos")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInicio() {
        return inicio;
    }

    public Locacao inicio(String inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public Locacao duracao(Integer duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Integer getValor() {
        return valor;
    }

    public Locacao valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Locacao veiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        return this;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Locacao cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        Locacao locacao = (Locacao) o;
        if (locacao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locacao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Locacao{" +
            "id=" + getId() +
            ", inicio='" + getInicio() + "'" +
            ", duracao=" + getDuracao() +
            ", valor=" + getValor() +
            "}";
    }
}
