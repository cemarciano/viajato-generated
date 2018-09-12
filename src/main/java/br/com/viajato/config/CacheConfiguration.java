package br.com.viajato.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(br.com.viajato.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(br.com.viajato.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Estado.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Estado.class.getName() + ".cidades", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Cidade.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Cidade.class.getName() + ".aeroportos", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Cidade.class.getName() + ".enderecos", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Aeroporto.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Aeroporto.class.getName() + ".vemDes", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Aeroporto.class.getName() + ".vaiParas", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Endereco.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Telefone.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.LinhaAerea.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.LinhaAerea.class.getName() + ".telefones", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.LinhaAerea.class.getName() + ".voos", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Voo.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Voo.class.getName() + ".passagems", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Passagem.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Locadora.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Locadora.class.getName() + ".telefones", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Locadora.class.getName() + ".veiculos", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Locadora.class.getName() + ".contratoes", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Veiculo.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Veiculo.class.getName() + ".locacaos", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Locacao.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Hotel.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Hotel.class.getName() + ".telefones", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Hotel.class.getName() + ".quartos", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Quarto.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Quarto.class.getName() + ".reservas", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Reserva.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Cliente.class.getName() + ".passagems", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Cliente.class.getName() + ".locacaos", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Cliente.class.getName() + ".reservas", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Seguradora.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Seguradora.class.getName() + ".contratoes", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Seguradora.class.getName() + ".telefones", jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Contrato.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.viajato.domain.Contrato.class.getName() + ".clientes", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
