package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.dto.AddressRegistrationDTO;
import com.example.ittalentsdominosproject.model.dto.AddressReturnDTO;
import com.example.ittalentsdominosproject.model.dto.AddressWithUserDTO;
import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InfoValidator infoValidator;

    public AddressReturnDTO addAddress(AddressRegistrationDTO addressRegistrationDTO, User user) {
        if(user == null) {
            throw new NotFoundException("No user found!");
        }
        infoValidator.addressPresentValidate(addressRegistrationDTO.getAddressName());
        infoValidator.addressUniquenessValidate(addressRegistrationDTO, user);

        Address address = modelMapper.map(addressRegistrationDTO, Address.class);
        address.setUser(user);
        addressRepository.save(address);
        return modelMapper.map(address, AddressReturnDTO.class);
    }

    public List<AddressWithUserDTO> getAllAddresses(Optional<User> user) {
        List<AddressWithUserDTO> addressDto = new ArrayList<>();
        List<Address> address =  addressRepository.getAddressesByUser_Id(user.get().getId());
        for (Address a:address){
            addressDto.add(modelMapper.map(a,AddressWithUserDTO.class));
        }
        return addressDto;
    }

    public AddressReturnDTO chooseAddress(User user, int aId) {
        Optional<Address> addressOptional = addressRepository.findById(aId);
        if(addressOptional.isEmpty() || addressOptional.get().getUser().getId() !=
                user.getId()) {
            throw new NotFoundException("No address found!");
        }
        return modelMapper.map(addressOptional.get(), AddressReturnDTO.class);
    }

    public AddressReturnDTO removeAddress(User user, int aId) {
        Optional<Address> addressOptional = addressRepository.findById(aId);
        if(addressOptional.isEmpty() || addressOptional.get().getUser().getId() !=
                user.getId()) {
            throw new NotFoundException("No address found!");
        }

        addressRepository.deleteById(aId);
        return modelMapper.map(addressOptional.get(), AddressReturnDTO.class);
    }

    public AddressReturnDTO editAddress(User user, int aId,
                                        AddressRegistrationDTO addressRegistrationDTO) {
        Optional<Address> addressOptional = addressRepository.findById(aId);
        if(addressOptional.isEmpty() || addressOptional.get().getUser().getId() !=
                user.getId()) {
            throw new NotFoundException("No address found!");
        }

        infoValidator.addressPresentValidate(addressRegistrationDTO.getAddressName());
        infoValidator.addressUniquenessValidate(addressRegistrationDTO, user);

        Address address = addressOptional.get();
        address.setAddressName(addressRegistrationDTO.getAddressName());

        return modelMapper.map(address, AddressReturnDTO.class);
    }
}
