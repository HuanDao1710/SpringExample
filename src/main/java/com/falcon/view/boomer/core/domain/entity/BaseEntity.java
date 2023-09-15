	package com.falcon.view.boomer.core.domain.entity;

	import lombok.AllArgsConstructor;
	import lombok.Getter;
	import lombok.NoArgsConstructor;
	import lombok.Setter;
	import org.springframework.data.annotation.CreatedDate;
	import org.springframework.data.annotation.LastModifiedDate;
	import org.springframework.data.jpa.domain.support.AuditingEntityListener;

	import javax.persistence.Column;
	import javax.persistence.EntityListeners;
	import javax.persistence.MappedSuperclass;
	import java.util.Date;

	@MappedSuperclass
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@EntityListeners(AuditingEntityListener.class)
	public class BaseEntity {
		@Column(name = "created_date")
		@CreatedDate
		private Date createdDate;
		@Column(name = "modified_date")
		@LastModifiedDate
		private Date modifiedDate;
		@Column(name = "created_by")
		private String createdBy;
		@Column(name = "modified_by")
		private String modifiedBy;
	}
