package com.jerotoma.database.dao.users;

import com.jerotoma.common.models.addresses.StaffAddress;
import com.jerotoma.database.dao.BaseDao;

public interface StaffAddressDao extends BaseDao<StaffAddress> {

	StaffAddress getStaffAddressByStaffId(Integer staffId);

}
