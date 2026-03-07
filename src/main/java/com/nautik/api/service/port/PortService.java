package com.nautik.api.service.port;

import com.nautik.api.domain.City;
import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.dto.port.PortDto;
import com.nautik.api.dto.port.create.CreatePortDto;
import com.nautik.api.repository.location.CityRepository;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.user.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PortService {

    private final PortRepository portRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final AdminRepository adminRepository;

    public List<PortDto> findAll() {
        return portRepository.findAll()
                .stream()
                .map(port -> modelMapper.map(port, PortDto.class))
                .toList();
    }
    public List<PortDto> findAllByCompanyAdmin(Integer companyAdmin) {
        List<Port> ports = portRepository.findAllByCompanyAdminId(companyAdmin);
        if (ports.isEmpty()){
            throw new EntityNotFoundException("No ports were found");
        }

        return ports.stream().map(p-> modelMapper.map(p, PortDto.class)).toList();
    }
    public PortDto findAllByPortAdmin(Integer portAdminId) {
        Admin admin = adminRepository.findById(portAdminId).orElseThrow(()->new EntityNotFoundException("User no found"));

        Port port = admin.getPort();

        return modelMapper.map(port, PortDto.class);
    }

    public PortDto findById(Integer portId, Integer adminId) {
        Port port = getPortAndValidateOwnership(portId, adminId);
        return modelMapper.map(port, PortDto.class);
    }

    public PortDto findByName(String name) {
        return modelMapper.map(portRepository.findByName(name), PortDto.class);
    }

    private Company getCompanyByAdminId(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
        return companyRepository.findByAdmin(admin)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
    }

    public PortDto create(CreatePortDto dto, Integer adminId) {
        Company company = getCompanyByAdminId(adminId);
        City city = cityRepository.findCityByName(dto.getCityName())
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        Port port = modelMapper.map(dto,Port.class);
        port.setCompany(company);
        port.setCity(city);

        return modelMapper.map(portRepository.save(port), PortDto.class);
    }

    public PortDto update(Integer portId, CreatePortDto dto, Integer adminId) {
        Port port = getPortAndValidateOwnership(portId,adminId);
        Port providedPort = modelMapper.map(dto,Port.class);

        City city = cityRepository.findCityByName(dto.getCityName())
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        providedPort.setCity(city);
        providedPort.setId(port.getId());

        return modelMapper.map(portRepository.save(providedPort), PortDto.class);
    }

    public void delete(Integer portId, Integer adminId) {
        Port port = getPortAndValidateOwnership(portId,adminId);

        portRepository.delete(port);
    }


    public Port getPortAndValidateOwnership(Integer portId, Integer adminId){
        Admin admin = adminRepository.findById(adminId).orElseThrow(()->new EntityNotFoundException("admin not found"));
        Port port = portRepository.findById(portId)
                .orElseThrow(() -> new EntityNotFoundException("Port not found"));

        Port portOfAdmin = admin.getPort();
        if (portOfAdmin == null){
            Company company = getCompanyByAdminId(adminId);
            if (!port.getCompany().getId().equals(company.getId())) {
                throw new AccessDeniedException("No permission to access this resource");
            }
        }else {
            if (!Objects.equals(portOfAdmin.getId(), port.getId())){
                throw new AccessDeniedException("No permission to access this resource");
            }
        }
        return port;
    }
}