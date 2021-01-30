package com.testing.mockito.business;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.testing.mockito.api.TodoService;
import com.testing.mockito.api.TodoServiceStub;
import static java.util.Arrays.asList;

public class TodoBusinessImplStubTest {

	@Test
	public void testRetrieveTodoRelatedToSpring_usingStub() {
		TodoService todoServiceStub = (TodoService) new TodoServiceStub();
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceStub);
		
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		
		@SuppressWarnings("unused")
		List<String> result = asList("learn Spring MVC", "learn Spring");
		
		//assertEquals(2, filteredTodos.size());
		assertTrue(filteredTodos.equals(result));
		
	}

}
