package com.jerotoma.common.models.academic;

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

@Entity
@Table(name = DatabaseConstant.TABLES.CLASS_ROOM_RESOURCES)
public class ClassRoomResource {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.CLASS_ROOM_RESOURCES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.CLASS_ROOM_RESOURCES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.CLASS_ROOM_RESOURCES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String code;
	
	@Column
	private String name;
	
	@Column(name="number_of_item")
	private Integer numberOfItem;
	
	@Column
	private String description;
	
	@ManyToOne
	@JoinColumn(name="class_room_id")
	private ClassRoom classRoom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfItem() {
		return numberOfItem;
	}

	public void setNumberOfItem(Integer numberOfItem) {
		this.numberOfItem = numberOfItem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	} 
}
