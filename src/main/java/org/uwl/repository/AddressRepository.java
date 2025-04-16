package org.uwl.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uwl.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
  List<Address> findByUserId(Long userId);
}
