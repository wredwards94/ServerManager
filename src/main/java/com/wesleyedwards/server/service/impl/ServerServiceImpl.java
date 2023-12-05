package com.wesleyedwards.server.service.impl;

import com.wesleyedwards.server.dto.ServerRequestDto;
import com.wesleyedwards.server.dto.ServerResponseDto;
import com.wesleyedwards.server.enumeration.Status;
import com.wesleyedwards.server.mapper.ServerMapper;
import com.wesleyedwards.server.model.Server;
import com.wesleyedwards.server.repository.ServerRepository;
import com.wesleyedwards.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerMapper serverMapper;
    private final ServerRepository serverRepository;

    @Override
    public ServerResponseDto createServer(ServerRequestDto serverRequestDto) {
        log.info("Saving new server: {}", serverRequestDto.getName());
        Server server = serverMapper.dtoToEntity(serverRequestDto);
        server.setImageUrl(setServerImageUrl());

        return serverMapper.entityToDto(serverRepository.saveAndFlush(server));
    }

    @Override
    public ServerResponseDto pingServer(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Optional<Server> serverOptional = serverRepository.findByIpAddress(ipAddress);

        if(serverOptional.isEmpty()) throw new RuntimeException("No server exists with the entered address: " + ipAddress);

        Server server = serverOptional.get();

        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);

        return serverMapper.entityToDto(serverRepository.saveAndFlush(server));
    }

    @Override
    public Collection<ServerResponseDto> list(int limit) {
        log.info("Fetching all servers");
        return serverMapper.entitiesToDtos(serverRepository.findAll(PageRequest.of(0, limit)).toList());
    }

    @Override
    public ServerResponseDto getServer(Long id) {
        log.info("Fetching server by id: {}", id);

        Optional<Server> serverOptional = serverRepository.findById(id);
        if(serverOptional.isEmpty()) throw new RuntimeException("No server exists with the entered id: " + id);

        return serverMapper.entityToDto(serverOptional.get());
    }

    @Override
    public ServerResponseDto updateServer(ServerRequestDto serverRequestDto) {
        log.info("Updating server: {}", serverRequestDto.getName());
        Server server = serverMapper.dtoToEntity(serverRequestDto);

        return serverMapper.entityToDto(serverRepository.saveAndFlush(server));
    }

    @Override
    public Boolean deleteServer(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepository.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {};
        return null;
    }
}
