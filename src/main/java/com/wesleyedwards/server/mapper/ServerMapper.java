package com.wesleyedwards.server.mapper;

import com.wesleyedwards.server.dto.ServerRequestDto;
import com.wesleyedwards.server.dto.ServerResponseDto;
import com.wesleyedwards.server.model.Server;
import org.mapstruct.*;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = SPRING)
public interface ServerMapper {
    Server dtoToEntity(ServerRequestDto serverRequestDto);

    ServerResponseDto entityToDto(Server server);

    List<ServerResponseDto> entitiesToDtos(List<Server> servers);

}