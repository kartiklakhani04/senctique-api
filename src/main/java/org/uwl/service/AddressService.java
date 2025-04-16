package org.uwl.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.uwl.entity.Address;
import org.uwl.entity.User;
import org.uwl.repository.AddressRepository;
import org.uwl.repository.UserRepository;

@Service
public class AddressService {

  private final AddressRepository addressRepository;
  private final UserRepository userRepository;

  public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
    this.addressRepository = addressRepository;
    this.userRepository = userRepository;
  }

  public Address saveAddress(Long userId, Address address) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      address.setUser(user.get());
      return addressRepository.save(address);
    }
    throw new RuntimeException("User not found");
  }

  public List<Address> getAddressesByUserId(Long userId) {
    return addressRepository.findByUserId(userId);
  }

  public Address updateAddress(Long addressId, Address updatedAddress) {
    return addressRepository
        .findById(addressId)
        .map(
            address -> {
              address.setAddressLine1(updatedAddress.getAddressLine1());
              address.setAddressLine2(updatedAddress.getAddressLine2());
              address.setCity(updatedAddress.getCity());
              address.setPostcode(updatedAddress.getPostcode());
              address.setCountry(updatedAddress.getCountry());

              return addressRepository.save(address);
            })
        .orElseThrow(() -> new RuntimeException("Address not found"));
  }
}
