package com.nautik.api.service.admin;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.Token;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.exceptions.ForbiddenException;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.LoginRequest;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.admin.AdminCompanyRequest;
import com.nautik.api.dto.admin.AdminPortRequest;
import com.nautik.api.dto.admin.AdminResponse;
import com.nautik.api.dto.user.UserLoginResponse;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.repository.user.AdminRepository;
import com.nautik.api.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final PortRepository portRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;




    public AdminResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            Admin admin = adminRepository.findByUsername(loginRequest.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            Token token =  jwtService.generateAdminToken(admin);


            return mapToResponse(admin);

        }

        throw new RuntimeException("Credenciales inválidas");
    }



    public AdminResponse createCompanyAdmin(AdminCompanyRequest request) {
        Role role = roleRepository.findByName("ADMIN_COMPANY");
        if (role == null) {
            throw new EntityNotFoundException("Role ADMIN_COMPANY not found");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));

        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(role);
        admin.setCompany(company);
        admin.setPort(null);

        Admin savedAdmin = adminRepository.save(admin);


        return mapToResponse(savedAdmin);
    }

    public AdminResponse updateCompanyAdmin(Integer adminId, AdminCompanyRequest request) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));

        if (admin.getCompany() == null) {
            throw new ForbiddenException("This admin is not a company admin");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));

        admin.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        admin.setCompany(company);

        Admin updatedAdmin = adminRepository.save(admin);
        return mapToResponse(updatedAdmin);
    }

    public void deleteCompanyAdmin(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));

        if (admin.getCompany() == null) {
            throw new ForbiddenException("This admin is not a company admin");
        }

        adminRepository.deleteById(adminId);
    }


    public AdminResponse createPortAdmin(Integer adminCompanyId, Integer companyId, Integer portId, AdminPortRequest request) {
        verifyPortOwnership(companyId, portId);

        Role role = roleRepository.findByName("ADMIN_PORT");
        if (role == null) {
            throw new EntityNotFoundException("Role ADMIN_PORT not found");
        }

        Port port = portRepository.findById(portId)
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + portId));

        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(role);
        admin.setPort(port);
        admin.setCompany(null);

        Admin savedAdmin = adminRepository.save(admin);
        return mapToResponse(savedAdmin);
    }

    @Transactional(readOnly = true)
    public List<AdminResponse> getPortAdmins(Integer adminCompanyId, Integer companyId, Integer portId) {
        verifyPortOwnership(companyId, portId);

        Port port = portRepository.findById(portId)
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + portId));

        return port.getAdmins().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AdminResponse updatePortAdmin(Integer adminCompanyId, Integer companyId, Integer portId, Integer adminPortId, AdminPortRequest request) {
        verifyPortOwnership(companyId, portId);

        Admin admin = adminRepository.findById(adminPortId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminPortId));

        if (admin.getPort() == null || !admin.getPort().getId().equals(portId)) {
            throw new ForbiddenException("Admin does not belong to the specified port");
        }

        admin.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return mapToResponse(updatedAdmin);
    }

    public void deletePortAdmin(Integer adminCompanyId, Integer companyId, Integer portId, Integer adminPortId) {
        verifyPortOwnership(companyId, portId);

        Admin admin = adminRepository.findById(adminPortId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminPortId));

        if (admin.getPort() == null || !admin.getPort().getId().equals(portId)) {
            throw new ForbiddenException("Admin does not belong to the specified port");
        }

        adminRepository.deleteById(adminPortId);
    }



    @Transactional(readOnly = true)
    public AdminResponse getAdmin(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));
        return mapToResponse(admin);
    }

    @Transactional(readOnly = true)
    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void verifyPortOwnership(Integer companyId, Integer portId) {
        Port port = portRepository.findById(portId)
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + portId));

        if (!port.getCompany().getId().equals(companyId)) {
            throw new ForbiddenException("Port does not belong to your company");
        }
    }

    private AdminResponse mapToResponse(Admin admin) {
        AdminResponse response = new AdminResponse();
        Token token =  jwtService.generateAdminToken(admin);
        response.setToken(token);
        response.setId(admin.getId());
        response.setUsername(admin.getUsername());

        if (admin.getCompany() != null) {
            response.setCompanyId(admin.getCompany().getId());
            response.setAdminType("COMPANY");
        } else if (admin.getPort() != null) {
            response.setPortId(admin.getPort().getId());
            response.setAdminType("PORT");
        }

        return response;
    }
}