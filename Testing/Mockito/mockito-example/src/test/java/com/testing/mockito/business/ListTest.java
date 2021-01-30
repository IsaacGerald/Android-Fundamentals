package com.testing.mockito.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

public class ListTest {
    
	@Test
	public void mockListSizeMethod() {
		
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(2);
		assertEquals(2, listMock.size());
				
		
	}
	
	@Test
	public void mockListSizeMethod_ReturnMultipleValues() {
		
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(2).thenReturn(3);
		assertEquals(2, listMock.size());
		assertEquals(3, listMock.size());
		
			
	}
	
	@Test
	public void mockListGetMethod_ReturnMultipleValues() {
		
		@SuppressWarnings("rawtypes")
		List listMock = mock(List.class);
		//Argument Marcher
		when(listMock.get(anyInt())).thenReturn("Ghana");
		assertEquals("Ghana", listMock.get(0));
		assertEquals("Ghana", listMock.get(1));
		
		
		
	}
	
	@Test(expected=RuntimeException.class)
	public void mockList_throwAnException() {
		
		@SuppressWarnings("rawtypes")
		List listMock = mock(List.class);
		//Argument Marcher
		when(listMock.get(anyInt())).thenThrow(new RuntimeException("something"));
		listMock.get(0);
		
		
		
	}

	
	
	

}
