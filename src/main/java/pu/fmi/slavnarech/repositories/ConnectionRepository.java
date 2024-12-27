package pu.fmi.slavnarech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.slavnarech.entities.connection.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {}
