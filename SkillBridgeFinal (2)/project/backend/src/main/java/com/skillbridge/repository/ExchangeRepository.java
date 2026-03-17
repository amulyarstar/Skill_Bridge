package com.skillbridge.repository;

import com.skillbridge.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, UUID> {
    @Query("SELECT e FROM Exchange e WHERE e.requesterId = :userId OR e.partnerId = :userId")
    List<Exchange> findByUserId(@Param("userId") UUID userId);

    List<Exchange> findByRequesterId(UUID requesterId);
    List<Exchange> findByPartnerId(UUID partnerId);
    List<Exchange> findByStatus(String status);

    @Query("SELECT e FROM Exchange e WHERE (e.requesterId = :userId OR e.partnerId = :userId) AND e.status = :status")
    List<Exchange> findByUserIdAndStatus(@Param("userId") UUID userId, @Param("status") String status);
}
