package com.nautik.api.service.port;

import com.nautik.api.domain.City;
import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.dto.port.PortDto;
import com.nautik.api.dto.port.create.CreatePortDto;
import com.nautik.api.repository.location.CityRepository;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortService {


    private final PortRepository portRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;


    public List<PortDto> findAll(){
        return portRepository.findAll()
                .stream()
                .map(port -> modelMapper.map(port, PortDto.class))
                .toList();

    }

    public PortDto findById(Long portId){
        return modelMapper.map(portRepository.findById(Math.toIntExact(portId)), PortDto.class);
    }
    public PortDto findByName(String name){
        return modelMapper.map(portRepository.findByName(name), PortDto.class);
    }

    public PortDto create(CreatePortDto port){
        System.out.println("---------"+port.getCompanyName());
        Company company = companyRepository.findCompanyByName(port.getCompanyName()).orElseThrow();
        City city = cityRepository.findCityByName(port.getCityName()).orElseThrow();


        Port addPort = new Port();
        addPort.setName(port.getName());
        addPort.setCompany(company);
        addPort.setCity(city);

        return modelMapper.map(portRepository.save(addPort), PortDto.class);
    }

    public PortDto update(Long portId, PortDto port ){
        Port updatePort = portRepository.findById(Math.toIntExact(portId)).orElseThrow();
        Port providePort = modelMapper.map(port, Port.class);
        providePort.setId(updatePort.getId());
        return modelMapper.map(portRepository.save(providePort), PortDto.class);
    }

    public void delete(Long portId){
        Port deletePort = portRepository.findById(Math.toIntExact(portId)).orElseThrow();
        portRepository.delete(deletePort);
    }

}
