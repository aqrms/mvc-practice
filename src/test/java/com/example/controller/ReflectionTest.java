package com.example.controller;

import static org.assertj.core.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.annotation.Controller;
import com.example.model.User;

class ReflectionTest {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
	@Test
	void controllerScan() {
		Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));

		logger.debug("beans: [{}]", beans);

	}

	private static Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations ) {
		Reflections reflections = new Reflections("com.example");

		Set<Class<?>> beans = new HashSet<>();
		annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

		return beans;
	}

	@Test
	void showClass() {
		Class<User> clazz = User.class;
		logger.debug(clazz.getName());

		logger.debug("User all declared fields: [{}]", Arrays.stream(clazz.getDeclaredFields()).toList());
		logger.debug("User all declared constructors: [{}]", Arrays.stream(clazz.getDeclaredConstructors()).toList());
		logger.debug("User all declared methods: [{}]", Arrays.stream(clazz.getDeclaredMethods()).toList());

	}

	@Test
	void load() throws ClassNotFoundException {
		//1
		Class<User> clazz = User.class;

		//2
		User user = new User("milo", "woo");
		Class<? extends User> clazz2 = user.getClass();

		//3
		Class<?> clazz3 = Class.forName("com.example.model.User");

		logger.debug("clazz: [{}]", clazz);
		logger.debug("clazz: [{}]", clazz2);
		logger.debug("clazz: [{}]", clazz3);

		assertThat(clazz).isEqualTo(clazz);
		assertThat(clazz2).isEqualTo(clazz3);
		assertThat(clazz3).isEqualTo(clazz);

	}


}