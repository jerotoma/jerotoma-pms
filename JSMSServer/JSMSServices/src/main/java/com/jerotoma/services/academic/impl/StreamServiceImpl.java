package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.StreamConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.Stream;
import com.jerotoma.database.dao.academic.StreamDao;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.StreamService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class StreamServiceImpl implements StreamService {
	
	@Autowired
	private StreamDao streamDao;
	@Autowired
	private AcademicLevelService academicLevelService;

	@Override
	public Stream findObject(Integer primaryKey) throws SQLException {		
		return ServiceUtil.getEntity(streamDao.findById(primaryKey));
	}

	@Override
	public Stream findObjectUniqueKey(String uniqueKey) throws SQLException {
		return streamDao.findByUniqueKey(uniqueKey);
	}

	@Override
	public Stream createObject(Stream object) throws SQLException {
		Stream stream = streamDao.save(object);
		if (object.getAcademicLevelId() != null) {
			updateAcademicLevelStream(object.getAcademicLevelId(), object);
		}
		return stream;
	}

	@Override
	public Stream updateObject(Stream object) throws SQLException {		
		Stream stream = streamDao.save(object);	
		if (object.getAcademicLevelId() != null) {
			updateAcademicLevelStream(object.getAcademicLevelId(), object);
		}
		return stream;
	}

	private void updateAcademicLevelStream(Integer academicLevelId, Stream stream) throws SQLException {
		Set<Stream> streams = new HashSet<>();	
		AcademicLevel academicLevel = academicLevelService.findObject(academicLevelId);
		streams.add(stream);
		streams.addAll(academicLevel.getStreams());
		academicLevel.setStreams(streams);
		academicLevelService.updateObject(academicLevel);
	}

	@Override
	public Boolean deleteObject(Stream object) throws SQLException {
		streamDao.delete(object);
		return true;
	}

	@Override
	public List<Stream> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return streamDao.findAll();
		}		
		return streamDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {	
		Map<String, Object> map = new HashMap<>();
		Page<Stream> pageStream = streamDao.findAll(ServiceUtil.getPageable(queryParam));		
		map.put(StreamConstant.STREAMS, pageStream.toList());
		map.put(SystemConstant.COUNT, pageStream.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageStream.getTotalPages());			
		return map;
	}

}
