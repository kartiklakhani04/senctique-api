package org.uwl.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uwl.entity.Address;
import org.uwl.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @PostMapping("/{userId}")
  public ResponseEntity<Address> saveAddress(
      @PathVariable Long userId, @RequestBody Address address) {
    return ResponseEntity.ok(addressService.saveAddress(userId, address));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
  }

  @PutMapping("/{addressId}")
  public ResponseEntity<Address> updateAddress(
      @PathVariable Long addressId, @RequestBody Address address) {
    return ResponseEntity.ok(addressService.updateAddress(addressId, address));
  }
}
