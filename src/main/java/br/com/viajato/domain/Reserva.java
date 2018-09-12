package br.com.viajato.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "num_pessoas", nullable = false)
    private Integer numPessoas;

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
    @JsonIgnoreProperties("reservas")
    private Quarto quarto;

    @ManyToOne
    @JsonIgnoreProperties("reservas")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumPessoas() {
        return numPessoas;
    }

    public Reserva numPessoas(Integer numPessoas) {
        this.numPessoas = numPessoas;
        return this;
    }

    public void setNumPessoas(Integer numPessoas) {
        this.numPessoas = numPessoas;
    }

    public String getInicio() {
        return inicio;
    }

    public Reserva inicio(String inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public Reserva duracao(Integer duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Integer getValor() {
        return valor;
    }

    public Reserva valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public Reserva quarto(Quarto quarto) {
        this.quarto = quarto;
        return this;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Reserva cliente(Cliente cliente) {
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
        Reserva reserva = (Reserva) o;
        if (reserva.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reserva.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + getId() +
            ", numPessoas=" + getNumPessoas() +
            ", inicio='" + getInicio() + "'" +
            ", duracao=" + getDuracao() +
            ", valor=" + getValor() +
            "}";
    }
}
