package com.testing.mockito.api;

import java.util.Arrays;
import java.util.List;

public class TodoServiceStub implements TodoService {

	public List<String> retrieveTodos(String user) {
		// TODO Auto-generated method stub
		return Arrays.asList("learn Spring MVC", 
				"learn Spring", "learn to dance");
	}

}
