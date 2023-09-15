package com.falcon.view.boomer.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ExceptionResponseDTO {
	private HttpStatus status;
	private String message;
	private Date timestamp;
}
