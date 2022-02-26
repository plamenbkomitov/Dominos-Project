package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.dto.AddressRegistrationDTO;
import com.example.ittalentsdominosproject.model.dto.AddressReturnDTO;
import com.example.ittalentsdominosproject.model.dto.AddressWithUserDTO;
import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        InfoValidator.class,
        AddressRepository.class,
        ModelMapper.class,
        AddressService.class})
public class AddressServiceTest {
    @Autowired
    private AddressService serviceToTest;

    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private ModelMapper modelMapper;


    @Test
    void addAddress_whenUserIsNull_shouldThrowException() {
        User user = null;
        AddressRegistrationDTO address = null;

        Assertions.assertThrows(NotFoundException.class, () -> serviceToTest.addAddress(address, user));
    }

    @Test
    void addAddress_whenUserIsNotNullAndAddressNameIsBlank_shouldThrowException() {
        User user = new User();
        AddressRegistrationDTO address = new AddressRegistrationDTO();
        address.setAddressName("");

        Assertions.assertThrows(BadRequestException.class, () -> serviceToTest.addAddress(address, user));
    }

    @Test
    void addAddress_whenUserIsNotNullAndAddressNameIsNull_shouldThrowException() {
        User user = new User();
        AddressRegistrationDTO address = new AddressRegistrationDTO();

        Assertions.assertThrows(BadRequestException.class, () -> serviceToTest.addAddress(address, user));
    }

    @Test
    void addAddress_whenUserAlreadyHasSameAddress_shouldThrowException() {
        User user = new User();
        AddressRegistrationDTO address = new AddressRegistrationDTO();
        address.setAddressName("Test name 1");
        Address userAddress = new Address();
        userAddress.setAddressName(address.getAddressName());
        user.setAddresses(List.of(userAddress));

        Assertions.assertThrows(BadRequestException.class, () -> serviceToTest.addAddress(address, user));
    }

    @Test
    void addAddress_whenUserAndAddressPassValidation_shouldReturnAddress() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        AddressRegistrationDTO address = new AddressRegistrationDTO();
        address.setAddressName("Test name 1");
        Address addressToAdd = new Address();
        addressToAdd.setAddressName(address.getAddressName());
        AddressReturnDTO addressToReturn = new AddressReturnDTO();
        addressToReturn.setAddressName(addressToAdd.getAddressName());
        addressToReturn.setId(1);

        given(modelMapper.map(address, Address.class)).willReturn(addressToAdd);
        given(addressRepository.save(addressToAdd)).willReturn(addressToAdd);
        given(modelMapper.map(addressToAdd, AddressReturnDTO.class)).willReturn(addressToReturn);

        AddressReturnDTO returnedAddress = serviceToTest.addAddress(address, user);

        Assertions.assertEquals(address.getAddressName(), returnedAddress.getAddressName());
        verify(modelMapper, times(1)).map(address, Address.class);
        verify(addressRepository, times(1)).save(addressToAdd);
        verify(modelMapper, times(1)).map(addressToAdd, AddressReturnDTO.class);
    }

    @Test
    void getAllAddresses_whenUserIsEmpty_shouldThrowException() {

        Assertions.assertThrows(BadRequestException.class, () -> serviceToTest.getAllAddresses(Optional.empty()));
    }

    @Test
    void getAllAddresses_whenUserIsNotEmpty_shouldReturnAddresses() {
        User user = new User();
        user.setId(1);
        List<Address> addresses = new ArrayList<>();
        Address firstAddress = new Address();
        firstAddress.setAddressName("new address");
        Address secondAddress = new Address();
        secondAddress.setAddressName("new address 2");
        addresses.add(firstAddress);
        addresses.add(secondAddress);
        AddressWithUserDTO firstAddressWithUser = new AddressWithUserDTO();
        firstAddressWithUser.setAddressName(firstAddress.getAddressName());
        AddressWithUserDTO secondAddressWithUser = new AddressWithUserDTO();
        secondAddressWithUser.setAddressName(secondAddress.getAddressName());

        given(addressRepository.getAddressesByUser_Id(user.getId())).willReturn(addresses);
        given(modelMapper.map(firstAddress, AddressWithUserDTO.class)).willReturn(firstAddressWithUser);
        given(modelMapper.map(secondAddress, AddressWithUserDTO.class)).willReturn(secondAddressWithUser);

        List<AddressWithUserDTO> addressWithUserDTOS = serviceToTest.getAllAddresses(Optional.of(user));

        Assertions.assertEquals(firstAddress.getAddressName(), addressWithUserDTOS.get(0).getAddressName());
        Assertions.assertEquals(secondAddress.getAddressName(), addressWithUserDTOS.get(1).getAddressName());
        verify(addressRepository, times(1)).getAddressesByUser_Id(user.getId());
        verify(modelMapper, times(1)).map(firstAddress, AddressWithUserDTO.class);
        verify(modelMapper, times(1)).map(secondAddress, AddressWithUserDTO.class);
    }

    @Test
    void chooseAddress_whenAddressIsEmpty_shouldThrowException() {
        User user = new User();

        given(addressRepository.findById(1)).willReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> serviceToTest.chooseAddress(user, 1));
    }


    @Test
    void chooseAddress_whenUserDoesNotHaveAddress_shouldThrowException() {
        User user = new User();
        user.setId(1);
        Address address = new Address();
        User addressOwner = new User();
        addressOwner.setId(2);
        address.setUser(addressOwner);

        given(addressRepository.findById(1)).willReturn(Optional.of(address));

        Assertions.assertThrows(NotFoundException.class, () -> serviceToTest.chooseAddress(user, 1));
    }

    @Test
    void chooseAddress_whenUserDoesHaveAddress_shouldReturnAddress() {
        User user = new User();
        user.setId(1);
        Address address = new Address();
        address.setAddressName("test 1");
        AddressReturnDTO addressReturn = new AddressReturnDTO();
        addressReturn.setAddressName(address.getAddressName());
        address.setUser(user);

        given(addressRepository.findById(1)).willReturn(Optional.of(address));
        given(modelMapper.map(address, AddressReturnDTO.class)).willReturn(addressReturn);

        AddressReturnDTO returnedAddress = serviceToTest.chooseAddress(user, 1);

        Assertions.assertEquals(address.getAddressName(), returnedAddress.getAddressName());
        verify(addressRepository, times(1)).findById(1);
        verify(modelMapper, times(1)).map(address, AddressReturnDTO.class);
    }

    @Test
    void removeAddress_whenAddressIsEmpty_shouldThrowException() {
        User user = new User();

        given(addressRepository.findById(1)).willReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> serviceToTest.removeAddress(user, 1));
    }

    @Test
    void removeAddress_whenUserDoesNotHaveAddress_shouldThrowException() {
        User user = new User();
        user.setId(1);
        Address address = new Address();
        User addressOwner = new User();
        addressOwner.setId(2);
        address.setUser(addressOwner);

        given(addressRepository.findById(1)).willReturn(Optional.of(address));

        Assertions.assertThrows(NotFoundException.class, () -> serviceToTest.removeAddress(user, 1));
    }

}
