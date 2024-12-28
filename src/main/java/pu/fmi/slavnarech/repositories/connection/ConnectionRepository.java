package pu.fmi.slavnarech.repositories.connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pu.fmi.slavnarech.entities.connection.Connection;

public interface ConnectionRepository
    extends JpaRepository<Connection, Long>, JpaSpecificationExecutor<Connection> {}
