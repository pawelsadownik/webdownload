package pl.demoproject.webdownload.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.demoproject.webdownload.model.Website;

import java.util.List;

@Repository
public interface WebRepository extends JpaRepository<Website, Long> {

    List<Website> findAllByAddressContaining(String sequence);

}
