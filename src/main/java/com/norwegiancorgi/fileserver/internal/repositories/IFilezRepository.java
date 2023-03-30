package com.norwegiancorgi.fileserver.internal.repositories;

import com.norwegiancorgi.fileserver.internal.entities.Filez;
import com.norwegiancorgi.fileserver.internal.entities.Userz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IFilezRepository extends JpaRepository<Filez, UUID> {

    List<Filez> findAllByUserz(Userz userz);

}
