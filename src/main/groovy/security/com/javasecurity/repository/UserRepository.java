package security.com.javasecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import security.com.javasecurity.entiry.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Optional<User> findByUserId(UUID userId);

    User findByProviderId(String providerId);
}