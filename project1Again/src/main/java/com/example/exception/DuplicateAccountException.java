package com.example.exception;

public class DuplicateAccountException extends Exception {
	public DuplicateAccountException(String msg) {
		super(msg);
	}
}
