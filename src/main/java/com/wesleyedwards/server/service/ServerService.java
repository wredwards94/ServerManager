package com.wesleyedwards.server.service;

import com.wesleyedwards.server.dto.ServerRequestDto;
import com.wesleyedwards.server.dto.ServerResponseDto;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    ServerResponseDto createServer(ServerRequestDto serverRequestDto);

    ServerResponseDto pingServer(String ipAddress) throws IOException;

    Collection<ServerResponseDto> list(int limit);

    ServerResponseDto getServer(Long id);

    ServerResponseDto updateServer(ServerRequestDto serverRequestDto);

    Boolean deleteServer(Long id);
}
