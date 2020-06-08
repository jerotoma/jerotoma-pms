package com.jerotoma.common.models.attendances;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;;

@Entity
@Table(name = DatabaseConstant.TABLES.CLASS_ATTENDANCES)
public class ClassAttendance {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.CLASS_ATTENDANCES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.CLASS_ATTENDANCES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.CLASS_ATTENDANCES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;

	@ManyToOne
	@JoinColumn(name="class_id")
	private Class mClass;
	
	@ManyToOne
	@JoinColumn(name="academic_year_id")
	private AcademicYear academicYear;
	
	@Column(name="attendance_date")
	private Date attendanceDate;
	
	@Column(name="added_by")
	private Integer addedBy;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public ClassAttendance(Integer id, Class mClass, AcademicYear academicYear, Date attendanceDate, Integer addedBy,
			Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.mClass = mClass;
		this.academicYear = academicYear;
		this.attendanceDate = attendanceDate;
		this.addedBy = addedBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	public ClassAttendance(Class mClass, AcademicYear academicYear, Date attendanceDate, Integer addedBy,
			Date createdOn, Date updatedOn) {		
		this.mClass = mClass;
		this.academicYear = academicYear;
		this.attendanceDate = attendanceDate;
		this.addedBy = addedBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Class getmClass() {
		return mClass;
	}

	public void setmClass(Class mClass) {
		this.mClass = mClass;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	public Integer getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	public static class ClassAttendanceParam {
		Integer id;
		Integer classId;
		Integer academicYearId;
		Date attendanceDate;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getClassId() {
			return classId;
		}
		public void setClassId(Integer classId) {
			this.classId = classId;
		}
		public Integer getAcademicYearId() {
			return academicYearId;
		}
		public void setAcademicYearId(Integer academicYearId) {
			this.academicYearId = academicYearId;
		}
		public Date getAttendanceDate() {
			return attendanceDate;
		}
		public void setAttendanceDate(Date attendanceDate) {
			this.attendanceDate = attendanceDate;
		}		
	}
}
