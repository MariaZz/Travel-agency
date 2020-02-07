package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.AuthorizedUserConverter;
import com.netcracker.TravelAgency.dto.AuthorizedUserDto;
import com.netcracker.TravelAgency.entity.AuthorizedUser;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.AuthorizedUserRepository;
import com.netcracker.TravelAgency.service.interfaces.AuthorizedUserService;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "userService")
public class AuthorizedUserServiceImpl implements UserDetailsService, AuthorizedUserService, CrudService<AuthorizedUserDto> {


    private AuthorizedUserRepository authorizedUserRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizedUserServiceImpl(AuthorizedUserRepository authorizedUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authorizedUserRepository = authorizedUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        Optional<AuthorizedUser> authorizedUser = authorizedUserRepository.findByLogin(login);
        if (!authorizedUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid AuthorizedUsername or password.");
        }

        return new org.springframework.security.core.userdetails.User(authorizedUser.get().getLogin(), authorizedUser.get().getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public AuthorizedUserDto findById(final int id) {
        AuthorizedUserDto userDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (authorizedUserRepository.findById(id).isPresent()) {
            AuthorizedUser user = authorizedUserRepository.findById(id).get();
            userDto = AuthorizedUserConverter.convertEntityToDto(user);
        } else {
            throw new NotFoundException("User not found");
        }
        return userDto;
    }

    @Override
    public int update(final AuthorizedUserDto authorizedUserDto) {
        authorizedUserDto.setPassword(passwordEncoder.encode(authorizedUserDto.getPassword()));
        AuthorizedUser authorizedUser = AuthorizedUserConverter.convertDtoToEntity(authorizedUserDto);
        if (authorizedUserDto.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (authorizedUserRepository.findById(authorizedUserDto.getId()).isPresent()) {
            authorizedUserRepository.delete(authorizedUserRepository.findById(authorizedUserDto.getId()).get());
            authorizedUserRepository.save(authorizedUser);
            return authorizedUser.getId();
        } else {
            throw new NotFoundException("Authorized user doesn't exist");
        }
    }

    @Override
    public int create(final AuthorizedUserDto authorizedUserDto) {
        try {
            authorizedUserDto.setPassword(passwordEncoder.encode(authorizedUserDto.getPassword()));
            AuthorizedUser authorizedUser = AuthorizedUserConverter.convertDtoToEntity(authorizedUserDto);
            authorizedUserRepository.save(authorizedUser);
            return authorizedUser.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public Optional<AuthorizedUserDto> findByLogin(final String login) {
        Optional<AuthorizedUser> user = authorizedUserRepository.findAll().stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst();
        AuthorizedUserDto userDto = null;
        if (user.isPresent()) userDto = AuthorizedUserConverter.convertEntityToDto(user.get());
        return Optional.ofNullable(userDto);
    }

    @Override
    public void deleteById(final int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (authorizedUserRepository.findById(id).isPresent()) {
            authorizedUserRepository.deleteById(id);
        } else {
            throw new NotFoundException("Authorized user doesn't exist");
        }
    }

    @Override
    public void delete(final AuthorizedUserDto authorizedUserDto) {
        if (authorizedUserDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (authorizedUserRepository.findById(authorizedUserDto.getId()).isPresent()) {
            authorizedUserRepository.deleteById(authorizedUserDto.getId());
        } else {
            throw new NotFoundException("Authorized user doesn't exist");
        }
    }

    @Override
    public List<AuthorizedUserDto> findAll() {
        List<AuthorizedUser> arrivalDate = authorizedUserRepository.findAll();
        return arrivalDate.stream().map((AuthorizedUserConverter::convertEntityToDto)).collect(Collectors.toList());
    }
}
