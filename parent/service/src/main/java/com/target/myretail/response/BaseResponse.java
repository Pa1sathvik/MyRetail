package com.target.myretail.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.target.myretail.exception.Status;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class BaseResponse implements Serializable {

	private static final long serialVersionUID = -2680623080045798636L;

	private Status status;

	private HttpStatus httpStatus;

	@JsonIgnore
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	private String message;

	public BaseResponse() {
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BaseResponse [status=" + status + ", httpStatus=" + httpStatus + ", message=" + message + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
