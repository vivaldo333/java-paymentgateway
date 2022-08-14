package com.finaro.mapper;

import com.finaro.dto.transaction.ClientDto;
import com.finaro.persistence.entity.ClientEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T01:11:44+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl extends ClientMapper {

    @Override
    public ClientEntity convert(ClientDto dto) {
        if ( dto == null ) {
            return null;
        }

        ClientEntity.ClientEntityBuilder<?, ?> clientEntity = ClientEntity.builder();

        if ( dto.getEmail() != null ) {
            clientEntity.email( dto.getEmail() );
        }

        clientEntity.name( com.finaro.utils.Utils.encode(dto.getName()) );

        return clientEntity.build();
    }

    @Override
    public ClientDto convert(ClientEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        if ( entity.getEmail() != null ) {
            clientDto.setEmail( entity.getEmail() );
        }

        clientDto.setName( getMaskedDecodedText("name", entity.getName()) );

        return clientDto;
    }
}
