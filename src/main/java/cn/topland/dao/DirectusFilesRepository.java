package cn.topland.dao;

import cn.topland.entity.DirectusFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectusFilesRepository extends JpaRepository<DirectusFiles, String> {
}