package com.testing.mockito.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.testing.mockito.api.TodoService;
import com.testing.mockito.api.TodoServiceStub;
import static java.util.Arrays.asList;

public class TodoBusinessImplMockTest {

	@Test
	public void testRetrieveTodoRelatedToSpring_usingMock() {
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("learn Spring MVC", 
				"learn Spring", "learn to dance");
		
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		
		@SuppressWarnings("unused")
		List<String> result = asList("learn Spring MVC", "learn Spring");
		
		//assertEquals(2, filteredTodos.size());
		assertTrue(filteredTodos.equals(result));
		
	}
	@Test
	public void testRetrieveTodoRelatedToSpring_withEmptyist() {
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList();
		
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		List<String> result = asList();
	
		
		//assertEquals(0, filteredTodos.size());
		assertTrue(filteredTodos.equals(result));
		
	}

}
