package com.swpu.sharebook.controller;

import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rootControl")
public class RootControl {
	
	@GetMapping("/root")
	public ResponseResult getAllroot() {
		return null;
		 
	}
}
