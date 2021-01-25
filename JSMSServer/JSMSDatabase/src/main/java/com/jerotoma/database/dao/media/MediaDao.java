package com.jerotoma.database.dao.media;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jerotoma.common.models.media.Media;

public interface MediaDao extends JpaRepository<Media, Integer> {

}
