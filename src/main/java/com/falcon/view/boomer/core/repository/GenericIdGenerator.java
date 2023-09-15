package com.falcon.view.boomer.core.repository;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Properties;

public class GenericIdGenerator implements IdentifierGenerator {
	private String prefixId;
	private String sequenceName;
	private DecimalFormat decimalFormat;


	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws
			MappingException {
		IdentifierGenerator.super.configure(type, params, serviceRegistry);
		this.prefixId = params.getProperty("prefix");
		this.sequenceName = params.getProperty("sequence");

		Long numberCharacter = Long.valueOf(params.getProperty("number_character", "10"));
		String numberFormat = getNumberFormat(numberCharacter);
		this.decimalFormat = new DecimalFormat(numberFormat);
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws
			HibernateException {
		var result = session.createNativeQuery(String.format("SELECT  nextval('%s')", this.sequenceName)).getSingleResult();
		var sequence = ((BigInteger) result).longValue();
		return this.prefixId + "_" + getLongId(sequence);
	}

	private String getLongId(Long sequence) {
		return decimalFormat.format(sequence);
	}

	private String getNumberFormat(Long length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append("0");
		}
		return builder.toString();
	}
}
