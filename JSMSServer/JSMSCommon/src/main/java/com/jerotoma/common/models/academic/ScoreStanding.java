package com.jerotoma.common.models.academic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.SCORE_STANDINGS)
public class ScoreStanding {
	
		@Id
		@GeneratedValue(
				strategy = GenerationType.SEQUENCE, 
				generator = DatabaseConstant.TABLES.SCORE_STANDINGS + "_generator")
		@SequenceGenerator(
				name = DatabaseConstant.TABLES.SCORE_STANDINGS + "_generator", 
				sequenceName = DatabaseConstant.TABLES.SCORE_STANDINGS + "_id_seq", 
				allocationSize=1)
		@Column
		private Integer id;
		
		
		@Column
		private String standing;
		
		@Column(name = "min_score")
		private Double minScore;
		
		@Column(name = "max_score")
		private Double maxScore;
		
		@Column(name = "standing_color")
		private String standingColor;
		
		@Column(name="updated_by")
		private Integer updatedBy;
			
		@Column(name="created_on")
		private Date createdOn;
		
		@Column(name="updated_on")
		private Date updatedOn;
		
		

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getStanding() {
			return standing;
		}

		public void setStanding(String standing) {
			this.standing = standing;
		}

		public Double getMinScore() {
			return minScore;
		}

		public void setMinScore(Double minScore) {
			this.minScore = minScore;
		}

		public Double getMaxScore() {
			return maxScore;
		}

		public void setMaxScore(Double maxScore) {
			this.maxScore = maxScore;
		}

		public String getStandingColor() {
			return standingColor;
		}

		public void setStandingColor(String standingColor) {
			this.standingColor = standingColor;
		}

		public Integer getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(Integer updatedBy) {
			this.updatedBy = updatedBy;
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
}
