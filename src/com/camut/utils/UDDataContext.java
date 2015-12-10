package com.camut.utils;

public class UDDataContext {

	public static enum Table {
		Discount("Discount");
		
		private final String name;

		private Table(String name) {
			        this.name = name;
			    }

		public String tabeName() {
			return name;
		}
	}
	
public static enum DDiscount {
		
		Discount("Discount"), 

		private final String value;

		private DB.Discount(String value) {
			        this.value = value;
			    }

		public String getValue() {
			return value;
		}
	}
}
