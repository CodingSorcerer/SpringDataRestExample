package net.lonewolfcode.examples.datarest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager manager;

    SpringDataRestConfig(EntityManager manager){
        this.manager = manager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        for (EntityType entityType:manager.getMetamodel().getEntities()){
            config.exposeIdsFor(entityType.getBindableJavaType());
        }
    }
}
