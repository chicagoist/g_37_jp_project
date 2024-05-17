package de.aittr.g_37_jp_shop.repository;

import de.aittr.g_37_jp_shop.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByName(String name);
}
