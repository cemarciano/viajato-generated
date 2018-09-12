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
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private Integer cpf;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Passagem> passagems = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Locacao> locacaos = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reserva> reservas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clientes")
    private Contrato contrato;

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

    public Cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCpf() {
        return cpf;
    }

    public Cliente cpf(Integer cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public Cliente senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Passagem> getPassagems() {
        return passagems;
    }

    public Cliente passagems(Set<Passagem> passagems) {
        this.passagems = passagems;
        return this;
    }

    public Cliente addPassagem(Passagem passagem) {
        this.passagems.add(passagem);
        passagem.setCliente(this);
        return this;
    }

    public Cliente removePassagem(Passagem passagem) {
        this.passagems.remove(passagem);
        passagem.setCliente(null);
        return this;
    }

    public void setPassagems(Set<Passagem> passagems) {
        this.passagems = passagems;
    }

    public Set<Locacao> getLocacaos() {
        return locacaos;
    }

    public Cliente locacaos(Set<Locacao> locacaos) {
        this.locacaos = locacaos;
        return this;
    }

    public Cliente addLocacao(Locacao locacao) {
        this.locacaos.add(locacao);
        locacao.setCliente(this);
        return this;
    }

    public Cliente removeLocacao(Locacao locacao) {
        this.locacaos.remove(locacao);
        locacao.setCliente(null);
        return this;
    }

    public void setLocacaos(Set<Locacao> locacaos) {
        this.locacaos = locacaos;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public Cliente reservas(Set<Reserva> reservas) {
        this.reservas = reservas;
        return this;
    }

    public Cliente addReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.setCliente(this);
        return this;
    }

    public Cliente removeReserva(Reserva reserva) {
        this.reservas.remove(reserva);
        reserva.setCliente(null);
        return this;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public Cliente contrato(Contrato contrato) {
        this.contrato = contrato;
        return this;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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
        Cliente cliente = (Cliente) o;
        if (cliente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf=" + getCpf() +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            "}";
    }
}
