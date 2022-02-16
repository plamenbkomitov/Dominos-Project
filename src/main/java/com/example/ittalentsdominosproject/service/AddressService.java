package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.dto.AddressRegistrationDTO;
import com.example.ittalentsdominosproject.model.dto.AddressReturnDTO;
import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AddressReturnDTO addAddress(AddressRegistrationDTO addressRegistrationDTO, User user) {
        if(user == null) {
            throw new NotFoundException("No user found!");
        }
        //TODO check if address already added
        Address address = modelMapper.map(addressRegistrationDTO, Address.class);
        address.setUser(user);
        addressRepository.save(address);
        AddressReturnDTO addressReturnDTO = modelMapper.map(address, AddressReturnDTO.class);
        return addressReturnDTO;
    }
}
