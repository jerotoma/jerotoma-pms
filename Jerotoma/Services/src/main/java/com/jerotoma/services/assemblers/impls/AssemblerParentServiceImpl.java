package com.jerotoma.services.assemblers.impls;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ParentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.dao.AssemblerParentDao;
import com.jerotoma.services.assemblers.AssemblerParentService;


@Service
public class AssemblerParentServiceImpl implements AssemblerParentService {
	
	@Autowired AssemblerParentDao assemblerParentDao;
	
	@Override
	public ParentVO findObject(Integer primaryKey) throws SQLException {
		
		ParentVO parentVO = assemblerParentDao.findObject(primaryKey);
		if (parentVO != null) {
			List<StudentVO> students =  loadStudentsByParentId(parentVO.getId());
			parentVO.setStudents(students);
		}
		return parentVO;
	}

	@Override
	public ParentVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerParentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<ParentVO> loadList(QueryParam queryParam) throws SQLException {
		List<ParentVO> parentVOs = new ArrayList<>();
		List<ParentVO> parents = assemblerParentDao.loadList(queryParam);
		if (parents != null) {
			for(ParentVO parent : parents ) {
				List<StudentVO> students = loadStudentsByParentId(parent.getId());
				parent.setStudents(students);
				parentVOs.add(parent);			
			}		
		}		
		return parentVOs;
	}

	

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> mapParents = new HashMap<>();		
		Map<String, Object> mParents = assemblerParentDao.loadMapList(queryParam);
		if (mParents != null) {
			List<ParentVO> parentVOs = (List<ParentVO>) mParents.get(ParentConstant.PARENTS);
			List<ParentVO> mParentVOs = new ArrayList<>();				
			for(ParentVO parent : parentVOs) {
				List<StudentVO> students = loadStudentsByParentId(parent.getId());
				parent.setStudents(students);
				mParentVOs.add(parent);			
			}
			
			mapParents.put(ParentConstant.PARENTS, mParentVOs);
			mapParents.put(ParentConstant.PARENT_COUNT, mParents.get(SystemConstant.PAGE_COUNT));
			mapParents.put(SystemConstant.PAGE_COUNT, mParents.get(SystemConstant.PAGE_COUNT));
		}		
		return mapParents;
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerParentDao.countObject();
	}

	@Override
	public List<ParentVO> search(QueryParam queryParam) throws SQLException {		
		List<ParentVO> parentVOs = new ArrayList<>();
		List<ParentVO> parents = assemblerParentDao.search(queryParam);
		if (parents != null) {
			for(ParentVO parent : parents ) {
				List<StudentVO> students = loadStudentsByParentId(parent.getId());
				parent.setStudents(students);
				parentVOs.add(parent);			
			}		
		}		
		return parentVOs;
	}

	@Override
	public List<StudentVO> loadStudentsByParentId(Integer parentId) throws SQLException {
		return assemblerParentDao.loadStudentsByParentId(parentId);
	}

}
