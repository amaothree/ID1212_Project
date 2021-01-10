package id1212.project.repository;

import id1212.project.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Long> {
    Optional<UserFile> findUserFileByNameAndRoom(String name,Long room);
    List<UserFile> findUserFilesByRoom(Long room);
}
