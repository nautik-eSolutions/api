package com.nautik.api.service.port;

import com.nautik.api.domain.City;
import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.port.PortDto;
import com.nautik.api.dto.port.create.CreatePortDto;
import com.nautik.api.repository.location.CityRepository;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortService {

    private final PortRepository portRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public List<PortDto> findAll() {
        return portRepository.findAll()
                .stream()
                .map(port -> modelMapper.map(port, PortDto.class))
                .toList();
    }

    public PortDto findById(Integer portId) {
        return modelMapper.map(portRepository.findById(portId), PortDto.class);
    }

    public PortDto findByName(String name) {
        return modelMapper.map(portRepository.findByName(name), PortDto.class);
    }

    private Company getCompanyByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return companyRepository.findByAdministrator(user)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    public PortDto create(CreatePortDto dto, Integer userId) {
        Company company = getCompanyByUserId(userId);
        City city = cityRepository.findCityByName(dto.getCityName())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        Port port = new Port();
        port.setName(dto.getName());
        port.setCompany(company);
        port.setCity(city);

        return modelMapper.map(portRepository.save(port), PortDto.class);
    }

    public PortDto update(Integer portId, CreatePortDto dto, Integer userId) {
        Company company = getCompanyByUserId(userId);

        Port port = portRepository.findById(portId)
                .orElseThrow(() -> new ResourceNotFoundException("Port not found"));

        if (!port.getCompany().getId().equals(company.getId())) {
            throw new AccessDeniedException("No permission to access this resource");
        }

        City city = cityRepository.findCityByName(dto.getCityName())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        port.setName(dto.getName());
        port.setCity(city);

        return modelMapper.map(portRepository.save(port), PortDto.class);
    }

    public void delete(Integer portId, Integer userId) {
        Company company = getCompanyByUserId(userId);

        Port port = portRepository.findById(portId)
                .orElseThrow(() -> new ResourceNotFoundException("Port not found"));

        if (!port.getCompany().getId().equals(company.getId())) {
            throw new AccessDeniedException("No permission to access this resource");
        }

        portRepository.delete(port);
    }
}